package com.github.tanhao1410.thesis.server.handler;

import com.alibaba.fastjson.JSON;
import com.github.tanhao1410.thesis.common.domain.*;
import com.github.tanhao1410.thesis.common.mapper.*;
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
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;

import java.net.InetSocketAddress;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * 处理客户端信息上报信息
 */
public class ClientInfoHandler extends SimpleChannelInboundHandler<MessageProtocolInfo.MessageProtocol> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocolInfo.MessageProtocol msg) throws Exception {
        //客户端信息上报
        if (msg.getType() == MessageTypeEnum.CLIENT_INFO.getId()) {

            final ClientInfo clientInfo = JSON.parseObject(msg.getContent(), ClientInfo.class);
            //得到client的ip 和端口号
            final InetSocketAddress socketAddress = (InetSocketAddress) ctx.channel().remoteAddress();
            String ip = socketAddress.getAddress().getHostAddress();
            int port = socketAddress.getPort();
            //客户端信息上传
            //System.out.println("客户端信息上传ip :" + ip + " port : " + port);

            System.out.println("客户端信息上传:" + JSON.toJSONString(clientInfo));


            final DeviceDOMapper deviceDOMapper = SpringBeanManagement.deviceDOMapper;
            final MonitoringItemDOMapper monitoringItemDOMapper = SpringBeanManagement.monitoringItemDOMapper;
            final GroupDOMapper groupDOMapper = SpringBeanManagement.groupDOMapper;
            DeviceDO deviceQueryDo = new DeviceDO();
            deviceQueryDo.setIp(ip);
            deviceQueryDo.setPort(port);
            deviceQueryDo.setName(clientInfo.getDeviceName());
            final List<DeviceDO> deviceDOS = deviceDOMapper.selectPageSelective(deviceQueryDo, new PageRequest(0, 1, null));
            if (deviceDOS != null && deviceDOS.size() > 0) {

                //第一次连接到服务端的时候才需要更新，后面不需要了

//                final DeviceDO deviceDO = deviceDOS.get(0);
//
//                //说明之前该设备已经上线过了，那么更新它的配置
//
//                //更新组
//                final GroupDO groupQueryDo = new GroupDO();
//                groupQueryDo.setName(clientInfo.getGroupName());
//                final List<GroupDO> groupDOS = groupDOMapper.selectPageSelective(groupQueryDo, new PageRequest(0, 1, null));
//                if (groupDOS != null && groupDOS.size() > 0) {
//                    //说明组存在
//                    deviceDO.setGroupId(groupDOS.get(0).getId());
//                } else {
//                    //生成一个新组
//                    final GroupDO groupDO = new GroupDO();
//                    groupDO.setName(clientInfo.getGroupName());
//                    //生成随机位置信息
//                    groupDO.setX(new Random().nextInt(1000) + 100);
//                    groupDO.setY(new Random().nextInt(300) + 10);
//
//                    final Long groupId = groupDOMapper.insertSelectiveReturnPrimaryKey(groupDO);
//                    deviceDO.setGroupId(groupId);
//                }
//
//                //删除原来的监控配置信息
//
//                //增加新的配置信息
//                clientInfo.getMonitoringConfigs().forEach(config -> {
//                    final MonitoringItemDO monitoringItemDO = new MonitoringItemDO();
//                    //monitoringItemDO.setAlarmCondition(config.getAlarmCondition());
//                    //monitoringItemDO.se
//                    BeanUtils.copyProperties(config, monitoringItemDO);
//                    monitoringItemDO.setDeviceId(deviceDO.getId());
//
//                    monitoringItemDOMapper.insert(monitoringItemDO);
//                });


                //将客户端放入管理类中管理
                SpringBeanManagement.clientChannelManagment.clientChannelJoin(ctx, clientInfo);
                //下发配置信息
                SpringBeanManagement.clientCommService.sendMonitoringConfig(deviceDOS.get(0).getId());
            } else {

                //新加一个设备
                deviceQueryDo.setName(clientInfo.getDeviceName());
                deviceQueryDo.setX(new Random().nextInt(1000) + 100);
                deviceQueryDo.setY(new Random().nextInt(300) + 10);

                //更新组
                final GroupDO groupQueryDo = new GroupDO();
                groupQueryDo.setName(clientInfo.getGroupName());
                final List<GroupDO> groupDOS = groupDOMapper.selectPageSelective(groupQueryDo, new PageRequest(0, 1, null));
                if (groupDOS != null && groupDOS.size() > 0) {
                    //说明组存在
                    deviceQueryDo.setGroupId(groupDOS.get(0).getId());
                } else {
                    //生成一个新组
                    final GroupDO groupDO = new GroupDO();
                    groupDO.setName(clientInfo.getGroupName());
                    //生成随机位置信息
                    groupDO.setX(new Random().nextInt(1000) + 100);
                    groupDO.setY(new Random().nextInt(300) + 10);

                    final Long groupId = groupDOMapper.insertSelectiveReturnPrimaryKey(groupDO);
                    deviceQueryDo.setGroupId(groupId);
                }

                //返回的id不对！！！
                deviceDOMapper.insertSelectiveReturnPrimaryKey(deviceQueryDo);
                final Long deviceId = deviceDOMapper.selectPageSelective(deviceQueryDo, new PageRequest(0, 1, null)).get(0).getId();


                //增加新的配置信息
                clientInfo.getMonitoringConfigs().forEach(config -> {
                    final MonitoringItemDO monitoringItemDO = new MonitoringItemDO();
                    //monitoringItemDO.setAlarmCondition(config.getAlarmCondition());
                    //monitoringItemDO.se
                    BeanUtils.copyProperties(config, monitoringItemDO);

                    monitoringItemDO.setAlarmCondition(config.getAlarmCondition());
                    monitoringItemDO.setIntervalTime(config.getInterval());
                    monitoringItemDO.setIsAlarm(config.getType() == 0);//告警是0
                    //monitoringItemDO.setNeedMail();
                    monitoringItemDO.setDeviceId(deviceId);

                    monitoringItemDOMapper.insert(monitoringItemDO);
                });


                //将客户端放入管理类中管理
                SpringBeanManagement.clientChannelManagment.clientChannelJoin(ctx, clientInfo);
                //下发配置信息
                SpringBeanManagement.clientCommService.sendMonitoringConfig(deviceId);

            }



        }
        ctx.fireChannelRead(msg);
    }
}
