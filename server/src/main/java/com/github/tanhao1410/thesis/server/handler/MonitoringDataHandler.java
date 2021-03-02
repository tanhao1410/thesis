package com.github.tanhao1410.thesis.server.handler;

import com.alibaba.fastjson.JSON;
import com.github.tanhao1410.thesis.common.domain.AlarmDO;
import com.github.tanhao1410.thesis.common.domain.DeviceDO;
import com.github.tanhao1410.thesis.common.domain.HistoryAlarmDO;
import com.github.tanhao1410.thesis.common.domain.MonitoringDataDO;
import com.github.tanhao1410.thesis.common.mapper.AlarmDOMapper;
import com.github.tanhao1410.thesis.common.mapper.DeviceDOMapper;
import com.github.tanhao1410.thesis.common.mapper.HistoryAlarmDOMapper;
import com.github.tanhao1410.thesis.common.mapper.MonitoringDataDOMapper;
import com.github.tanhao1410.thesis.mq.MQConstant;
import com.github.tanhao1410.thesis.mq.RedisService;
import com.github.tanhao1410.thesis.protocol.MessageProtocolInfo;
import com.github.tanhao1410.thesis.protocol.MessageTypeEnum;
import com.github.tanhao1410.thesis.protocol.bean.ClientInfo;
import com.github.tanhao1410.thesis.protocol.bean.MonitoringAlarm;
import com.github.tanhao1410.thesis.protocol.bean.MonitoringData;
import com.github.tanhao1410.thesis.server.spring.SpringBeanManagement;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.data.domain.PageRequest;

import java.net.InetSocketAddress;
import java.util.Date;
import java.util.List;

/**
 * 处理客户端告警信息上报
 */
public class MonitoringDataHandler extends SimpleChannelInboundHandler<MessageProtocolInfo.MessageProtocol> {

    //处理异常, 一般是需要关闭通道

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        SpringBeanManagement.clientChannelManagment.removeClient(ctx);
        super.handlerRemoved(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        SpringBeanManagement.clientChannelManagment.removeClient(ctx);
        ctx.close();
    }

    //读取数据实际(这里我们可以读取客户端发送的消息)
    /*
    1. ChannelHandlerContext ctx:上下文对象, 含有 管道pipeline , 通道channel, 地址
    2. Object msg: 就是客户端发送的数据 默认Object
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocolInfo.MessageProtocol msg) throws Exception {
        //根据dataType 来显示不同的信息
        //客户端信息上报
        if (msg.getType() == MessageTypeEnum.CLIENT_INFO.getId()) {

            final ClientInfo clientInfo = JSON.parseObject(msg.getContent(), ClientInfo.class);
            //得到client的ip 和端口号
            final InetSocketAddress socketAddress = (InetSocketAddress) ctx.channel().remoteAddress();
            String ip = socketAddress.getAddress().getHostAddress();
            int port = socketAddress.getPort();
            //客户端信息上传
            System.out.println("客户端信息上传ip :" + ip + " port : " + port);

            //将客户端放入管理类中管理
            SpringBeanManagement.clientChannelManagment.clientChannelJoin(ctx, clientInfo);

            final DeviceDOMapper deviceDOMapper = SpringBeanManagement.deviceDOMapper;
            DeviceDO queryDo = new DeviceDO();
            queryDo.setIp(ip);
            queryDo.setPort(port);
            final List<DeviceDO> deviceDOS = deviceDOMapper.selectPageSelective(queryDo, new PageRequest(0, 1, null));
            if (deviceDOS != null && deviceDOS.size() > 0) {
                //下发配置信息
                SpringBeanManagement.clientCommService.sendMonitoringConfig(deviceDOS.get(0).getId());
            }
        } else if (msg.getType() == MessageTypeEnum.MONITORING_ALARM.getId()) {

            //
            final AlarmDOMapper alarmDOMapper = SpringBeanManagement.alarmDOMapper;
            final HistoryAlarmDOMapper historyAlarmDOMapper = SpringBeanManagement.historyAlarmDOMapper;
            //将客户端传过来的告警消息转换成数据库中的形式

            MonitoringAlarm monitoringAlarm = JSON.parseObject(msg.getContent(), MonitoringAlarm.class);

            AlarmDO queryDo = new AlarmDO();
            queryDo.setDeviceId(monitoringAlarm.getDeviceId());
            final List<AlarmDO> alarmDOS = alarmDOMapper.selectPageSelective(queryDo, new PageRequest(0, 1, null));
            if(alarmDOS != null && alarmDOS.size() > 0){
                //查询是否和当前告警是否发生，如果已经发生，则跳过
                final AlarmDO alarmDO = alarmDOS.get(0);
                if(!alarmDO.getIsNormal().equals(monitoringAlarm.getIsNormal())){

                    if(monitoringAlarm.getIsNormal()){
                        //当前是正常的，说明之前的告警消失了，加入历史告警
                        HistoryAlarmDO historyAlarmDO = new HistoryAlarmDO();
                        historyAlarmDO.setDeviceId(alarmDO.getDeviceId());
                        historyAlarmDO.setEndTime(new Date(System.currentTimeMillis()));
                        historyAlarmDO.setName(alarmDO.getName());
                        historyAlarmDO.setRuleId(alarmDO.getRuleId());
                        historyAlarmDO.setStartTime(alarmDO.getStartTime());
                        historyAlarmDO.setValue(alarmDO.getValue());

                        //保存历史告警
                        historyAlarmDOMapper.insert(historyAlarmDO);

                        //更新当前告警
                        alarmDO.setStartTime(new Date(System.currentTimeMillis()));
                        alarmDO.setValue(monitoringAlarm.getValue());
                        alarmDO.setIsNormal(monitoringAlarm.getIsNormal());
                    }else{
                        //正常---->不正常
                        alarmDO.setIsNormal(false);
                        alarmDO.setStartTime(new Date(System.currentTimeMillis()));
                        alarmDO.setValue(monitoringAlarm.getValue());

                        //更新当前告警表
                        alarmDOMapper.updateByPrimaryKey(alarmDO);
                    }
                }
            }else{
               //还没有当前告警，需要新建一个当前告警
                AlarmDO alarmDO = new AlarmDO();
                alarmDO.setValue(monitoringAlarm.getValue());
                alarmDO.setStartTime(new Date(System.currentTimeMillis()));
                alarmDO.setIsNormal(monitoringAlarm.getIsNormal());
                alarmDO.setDeviceId(monitoringAlarm.getDeviceId());
                alarmDO.setRuleId(monitoringAlarm.getRuleId());
                alarmDO.setName(monitoringAlarm.getName());
                alarmDOMapper.insert(alarmDO);
            }

            //通知management系统，告警发生变更
            final RedisService redisService = SpringBeanManagement.redisService;
            String alarmMsg = "";
            redisService.pubMessage(MQConstant.ALARM_CHANGE_MESSAGE_NAME, alarmMsg);


        } else if (msg.getType() == MessageTypeEnum.MONITORING_DATA.getId()) {

            final MonitoringDataDOMapper monitoringDataDOMapper = SpringBeanManagement.monitoringDataDOMapper;
            MonitoringData monitoringData = JSON.parseObject(msg.getContent(), MonitoringData.class);
            //转换成数据库格式，直接保存即可
            MonitoringDataDO dataDO = new MonitoringDataDO();
            dataDO.setDeviceId(monitoringData.getDeviceId());
            dataDO.setName(monitoringData.getName());
            dataDO.setRuleId(monitoringData.getRuleId());
            dataDO.setStartTime(new Date(monitoringData.getTime()));
            dataDO.setValue(monitoringData.getValue());
            //存入数据库
            monitoringDataDOMapper.insert(dataDO);
        }
    }
}
