package com.github.tanhao1410.thesis.management.redisQueue;

import com.alibaba.fastjson.JSONObject;
import com.github.tanhao1410.thesis.management.constant.RedisConfConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author hushawen
 * @date 2021/2/19 14:34
 */
@Component
@Slf4j
@Async
public class RedisCommonMsgListener extends MessageListenerAdapter {



    @Autowired
    public RedisCommonMsgListener(RedisMessageListenerContainer messageListenerContainer) {
        messageListenerContainer.addMessageListener(this, new PatternTopic(RedisConfConstant.AIDECISION_FINE_RK_LOGIN_TOKEN_PRE));
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        System.out.println("接收到Redis的消息:" +  new String(message.getBody()));
        String browseHis = new String(message.getBody());
        //新增浏览记录
        try {
            browseHis = browseHis.replaceAll("\\\\","");
            browseHis = browseHis.substring(1,browseHis.length()-1);

        } catch (Exception e) {
            log.error("DownloadsConsumerService deal error: " + e);
        }
    }


}
