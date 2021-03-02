package com.github.tanhao1410.thesis.server.mqconsumer;

import com.alibaba.fastjson.JSON;
import com.github.tanhao1410.thesis.common.domain.AlarmDO;
import com.github.tanhao1410.thesis.common.domain.DeviceDO;
import com.github.tanhao1410.thesis.common.mapper.AlarmDOMapper;
import com.github.tanhao1410.thesis.common.mapper.DeviceDOMapper;
import com.github.tanhao1410.thesis.common.mapper.HistoryAlarmDOMapper;
import com.github.tanhao1410.thesis.common.mapper.MonitoringItemDOMapper;
import com.github.tanhao1410.thesis.mq.MQConstant;
import com.github.tanhao1410.thesis.mq.RedisService;
import com.github.tanhao1410.thesis.mq.bean.AlarmChangeMsg;
import com.github.tanhao1410.thesis.mq.bean.DeviceChangeMsg;
import com.github.tanhao1410.thesis.protocol.MessageProtocolInfo;
import com.github.tanhao1410.thesis.protocol.MessageTypeEnum;
import com.github.tanhao1410.thesis.protocol.TCPProtocolConstant;
import com.github.tanhao1410.thesis.protocol.bean.MonitoringConfig;
import com.github.tanhao1410.thesis.server.comm.ClientChannelManagment;
import com.github.tanhao1410.thesis.server.service.ClientCommService;
import com.github.tanhao1410.thesis.server.service.MonitoringConfigService;
import com.github.tanhao1410.thesis.server.spring.SpringBeanManagement;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author tanhao
 * @date 2021/2/19 14:34
 */
@Component
@Slf4j
@Async
public class DeviceMsgListener extends MessageListenerAdapter {

    @Resource
    private AlarmDOMapper alarmDOMapper;
    @Resource
    private HistoryAlarmDOMapper historyAlarmDOMapper;
    @Resource
    private DeviceDOMapper deviceDOMapper;
    @Resource
    private MonitoringItemDOMapper monitoringItemDOMapper;
    @Resource
    private MonitoringConfigService monitoringConfigService;
    @Resource
    private ClientCommService clientCommService;
    @Resource
    private RedisService redisService;
    
    @Autowired
    public DeviceMsgListener(RedisMessageListenerContainer messageListenerContainer) {
        messageListenerContainer.addMessageListener(this, new PatternTopic(MQConstant.DEVICE_CHANGE_MESSAGE_NAME));
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        System.out.println("server接收到管理系统的消息:" +  new String(message.getBody()));
        String msgBody = new String(message.getBody());
        //新增浏览记录
        try {
            String msg = msgBody.replaceAll("\\\\","");
            msg = msg.substring(1,msg.length()-1);

            final DeviceChangeMsg msgObj = JSON.parseObject(msg, DeviceChangeMsg.class);
            String channelName = msgObj.getIp() + ":" + msgObj.getPort();

            final ChannelHandlerContext ctx = SpringBeanManagement.clientChannelManagment.getClientChannelByName(channelName);

            //判断设备是新增，删除，还是修改
            if(msgObj.getType() == DeviceChangeMsg.Type.CREATE.getId()){

                AlarmChangeMsg alarmMsg = new AlarmChangeMsg();
                //新增设备时判断设备是否在线
                if (ctx == null){
                    //说明客户端未在线，生成一个不在线的告警返回。
                    AlarmDO record = new AlarmDO();
                    record.setDeviceId(msgObj.getId());
                    record.setRuleId(1L);
                    record.setName("在线状态");
                    record.setStartTime(new Date(System.currentTimeMillis()));
                    record.setValue("断线");
                    record.setIsNormal(false);
                    //alarmDOMapper.insert(record);
                    final Long alarmId = alarmDOMapper.insertSelectiveReturnPrimaryKey(record);

                    alarmMsg.setDeviceId(msgObj.getId());
                    alarmMsg.setAlarmId(alarmId);

                }else{
                    //说明客户端已经连接上了，第一次下发配置，直接生成一个正常状态的告警，表明设备在线。
                    //说明客户端未在线，生成一个不在线的告警返回。
                    AlarmDO record = new AlarmDO();
                    record.setDeviceId(msgObj.getId());
                    record.setRuleId(1L);
                    record.setStartTime(new Date(System.currentTimeMillis()));
                    record.setValue("在线");
                    record.setName("在线状态");
                    record.setIsNormal(true);
                    //alarmDOMapper.insert(record);
                    final Long alarmId = alarmDOMapper.insertSelectiveReturnPrimaryKey(record);

                    alarmMsg.setDeviceId(msgObj.getId());
                    alarmMsg.setAlarmId(alarmId);
                }

                redisService.pubMessage(MQConstant.ALARM_CHANGE_MESSAGE_NAME, JSON.toJSONString(alarmMsg));


            }else if(msgObj.getType() ==  DeviceChangeMsg.Type.DELETE.getId()){
                //设备删除了，向客户端下发一个空的配置。

                String content = JSON.toJSONString(new ArrayList<MonitoringConfig>());
                final MessageProtocolInfo.MessageProtocol configMsg = MessageProtocolInfo.MessageProtocol.newBuilder()
                        .setHead(TCPProtocolConstant.HEAD)
                        .setContent(content)
                        .setLen(content.length())
                        .setType(MessageTypeEnum.MONITORING_CONFIG.getId())
                        .build();
                ctx.writeAndFlush(configMsg);

            }else if (msgObj.getType() == DeviceChangeMsg.Type.UPDATE.getId()){
                //向客户端下发新的配置信息
                clientCommService.sendMonitoringConfig(msgObj.getId());
            }

        } catch (Exception e) {
            log.error("DownloadsConsumerService deal error: " + e);
        }
    }


}
