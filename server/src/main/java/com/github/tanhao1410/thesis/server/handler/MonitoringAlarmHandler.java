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
public class MonitoringAlarmHandler extends SimpleChannelInboundHandler<MessageProtocolInfo.MessageProtocol> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocolInfo.MessageProtocol msg) throws Exception {
        //根据dataType 来显示不同的信息
        //客户端信息上报
        if (msg.getType() == MessageTypeEnum.MONITORING_ALARM.getId()) {

            //
            final AlarmDOMapper alarmDOMapper = SpringBeanManagement.alarmDOMapper;
            final HistoryAlarmDOMapper historyAlarmDOMapper = SpringBeanManagement.historyAlarmDOMapper;
            //将客户端传过来的告警消息转换成数据库中的形式

            MonitoringAlarm monitoringAlarm = JSON.parseObject(msg.getContent(), MonitoringAlarm.class);

            AlarmDO queryDo = new AlarmDO();
            queryDo.setDeviceId(monitoringAlarm.getDeviceId());
            queryDo.setItemId(monitoringAlarm.getItemId());
            final List<AlarmDO> alarmDOS = alarmDOMapper.selectPageSelective(queryDo, new PageRequest(0, 1, null));
            if (alarmDOS != null && alarmDOS.size() > 0) {
                //查询是否和当前告警是否发生，如果已经发生，则跳过
                final AlarmDO alarmDO = alarmDOS.get(0);
                if (!alarmDO.getIsNormal().equals(monitoringAlarm.getIsNormal())) {

                    if (monitoringAlarm.getIsNormal()) {
                        //当前是正常的，说明之前的告警消失了，加入历史告警
                        HistoryAlarmDO historyAlarmDO = new HistoryAlarmDO();
                        historyAlarmDO.setDeviceId(alarmDO.getDeviceId());
                        historyAlarmDO.setEndTime(new Date(System.currentTimeMillis()));
                        historyAlarmDO.setName(alarmDO.getName());
                        //historyAlarmDO.setRuleId(alarmDO.getRuleId());
                        historyAlarmDO.setItemId(alarmDO.getItemId());
                        historyAlarmDO.setStartTime(alarmDO.getStartTime());
                        historyAlarmDO.setValue(alarmDO.getValue());

                        //保存历史告警
                        historyAlarmDOMapper.insert(historyAlarmDO);

                        //更新当前告警
                        alarmDO.setStartTime(new Date(System.currentTimeMillis()));
                        alarmDO.setValue(monitoringAlarm.getValue());
                        alarmDO.setIsNormal(monitoringAlarm.getIsNormal());
                        alarmDO.setLevel(0);
                    } else {
                        //正常---->不正常
                        alarmDO.setIsNormal(false);
                        alarmDO.setStartTime(new Date(System.currentTimeMillis()));
                        alarmDO.setValue(monitoringAlarm.getValue());

                        //设置告警的级别
                        alarmDO.setLevel(monitoringAlarm.getLevel());

                        //更新当前告警表
                        alarmDOMapper.updateByPrimaryKey(alarmDO);
                    }
                }
            }else{
                //当前告警不存在，应该新建一个当前告警消息
                queryDo.setName(monitoringAlarm.getName());
                queryDo.setStartTime(new Date(System.currentTimeMillis()));
                queryDo.setIsNormal(monitoringAlarm.getIsNormal());
                queryDo.setValue(monitoringAlarm.getValue());
                queryDo.setItemId(monitoringAlarm.getItemId());
                queryDo.setLevel(monitoringAlarm.getLevel());

                alarmDOMapper.insert(queryDo);
            }
        }
        ctx.fireChannelRead(msg);
    }
}
