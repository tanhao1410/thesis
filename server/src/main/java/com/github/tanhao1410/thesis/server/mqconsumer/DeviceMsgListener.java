package com.github.tanhao1410.thesis.server.mqconsumer;

import com.github.tanhao1410.thesis.mq.MQConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author tanhao
 * @date 2021/2/19 14:34
 */
@Component
@Slf4j
@Async
public class DeviceMsgListener extends MessageListenerAdapter {
    
    @Autowired
    public DeviceMsgListener(RedisMessageListenerContainer messageListenerContainer) {
        messageListenerContainer.addMessageListener(this, new PatternTopic(MQConstant.DEVICE_CHANGE_MESSAGE_NAME));
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        System.out.println("接收到Redis的消息:" +  new String(message.getBody()));
        String msgBody = new String(message.getBody());
        //新增浏览记录
        try {
            String msg = msgBody.replaceAll("\\\\","");
            msg = msg.substring(1,msg.length()-1);

            //设备信息变更后，重新查询该设备的配置信息，从client连接库中找到对应的设备，向它下发新的配置信息



        } catch (Exception e) {
            log.error("DownloadsConsumerService deal error: " + e);
        }
    }


}
