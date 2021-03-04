package com.github.tanhao1410.thesis.management.mqconsumer;

import com.alibaba.fastjson.JSON;
import com.github.tanhao1410.thesis.common.bean.response.AlarmListResponse;
import com.github.tanhao1410.thesis.common.bean.response.GroupAlarmInfoResponse;
import com.github.tanhao1410.thesis.common.domain.AlarmDO;
import com.github.tanhao1410.thesis.common.domain.DeviceDO;
import com.github.tanhao1410.thesis.common.domain.GroupDO;
import com.github.tanhao1410.thesis.common.domain.UserGroupDO;
import com.github.tanhao1410.thesis.common.mapper.AlarmDOMapper;
import com.github.tanhao1410.thesis.common.mapper.DeviceDOMapper;
import com.github.tanhao1410.thesis.common.mapper.MonitoringItemDOMapper;
import com.github.tanhao1410.thesis.common.mapper.UserGroupDOMapper;
import com.github.tanhao1410.thesis.email.EMailService;
import com.github.tanhao1410.thesis.management.constant.RedisConfConstant;
import com.github.tanhao1410.thesis.management.service.AlarmService;
import com.github.tanhao1410.thesis.management.service.GroupService;
import com.github.tanhao1410.thesis.management.websocket.WebSocketHandler;
import com.github.tanhao1410.thesis.mq.MQConstant;
import com.github.tanhao1410.thesis.mq.bean.AlarmChangeMsg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author hushawen
 * @date 2021/2/19 14:34
 */
@Component
@Slf4j
@Async
public class AlarmMsgListener extends MessageListenerAdapter {

    @Resource
    private AlarmDOMapper alarmDOMapper;

    @Resource
    private EMailService emailService;

    @Resource
    private WebSocketHandler webSocketHandler;

    @Resource
    private MonitoringItemDOMapper monitoringItemDOMapper;

    @Resource
    private DeviceDOMapper deviceDOMapper;

    @Resource
    private UserGroupDOMapper userGroupDOMapper;

    @Resource
    private AlarmService alarmService;

    @Autowired
    public AlarmMsgListener(RedisMessageListenerContainer messageListenerContainer) {
        messageListenerContainer.addMessageListener(this, new PatternTopic(MQConstant.ALARM_CHANGE_MESSAGE_NAME));
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        System.out.println("接收到Redis的消息:" + new String(message.getBody()));
        String msg = new String(message.getBody());
        //新增浏览记录
        try {
            msg = msg.replaceAll("\\\\", "");
            msg = msg.substring(1, msg.length() - 1);

            AlarmChangeMsg msgObj = JSON.parseObject(msg, AlarmChangeMsg.class);

            //后端服务端传来了告警，根据告警能查到这是那台设备，以及是否需要邮件通知
            final DeviceDO deviceDO = deviceDOMapper.selectByPrimaryKey(msgObj.getDeviceId());
            final AlarmDO alarmDO = alarmDOMapper.selectByPrimaryKey(msgObj.getAlarmId());

            //找到这是属于哪个设备组的，以及哪些用户有这个设备组的权限
            UserGroupDO userGroupQuery = new UserGroupDO();
            userGroupQuery.setGroupId(deviceDO.getGroupId());
            final List<UserGroupDO> userGroupDOS = userGroupDOMapper.selectPageSelective(userGroupQuery, new PageRequest(0, Integer.MAX_VALUE, null));
            Set<Long> userIds = new HashSet();
            userGroupDOS.forEach(e -> {
                userIds.add(e.getUserId());
            });


            AlarmListResponse response = new AlarmListResponse();
            //查询该设备的所有告警，重新发送给前台，由前台更新
            AlarmDO alarmQuery = new AlarmDO();
            alarmQuery.setDeviceId(msgObj.getDeviceId());
            final List<AlarmDO> alarmDOS = alarmDOMapper.selectPageSelective(alarmQuery, new PageRequest(0, Integer.MAX_VALUE, null));

            final List<AlarmListResponse.AlarmItem> alarmItems = alarmDOS.stream()
                    .map(AlarmListResponse.AlarmItem::new)
                    .sorted(new Comparator<AlarmListResponse.AlarmItem>() {
                        @Override
                        public int compare(AlarmListResponse.AlarmItem o1, AlarmListResponse.AlarmItem o2) {
                            return o2.getLevel().compareTo(o1.getLevel());
                        }
                    })
                    .collect(Collectors.toList());

            response.put(String.valueOf(msgObj.getDeviceId()), alarmItems);

            //设备组的告警更新


            List<GroupAlarmInfoResponse> res = new ArrayList<>();


            GroupAlarmInfoResponse item = new GroupAlarmInfoResponse();
            item.setMaxLevel(0);
            //设置基本信息
            item.setDeviceNumber(alarmService.getDeviceNumber(deviceDO.getGroupId()));

            item.setGroupId(deviceDO.getGroupId());
            //item.setGroupName(e.getName());
            item.setOneLevelNumber(alarmService.getAlarmNum(deviceDO.getGroupId(), 1));
            if (item.getOneLevelNumber() > 0) {
                item.setMaxLevel(1);
            }

            item.setTwoLevelNumber(alarmService.getAlarmNum(deviceDO.getGroupId(), 2));
            if (item.getTwoLevelNumber() > 0) {
                item.setMaxLevel(2);
            }

            item.setThreeLevelNumber(alarmService.getAlarmNum(deviceDO.getGroupId(), 3));
            if (item.getThreeLevelNumber() > 0) {
                item.setMaxLevel(3);
            }

            item.setFourLevelNumber(alarmService.getAlarmNum(deviceDO.getGroupId(), 4));
            if (item.getFourLevelNumber() > 0) {
                item.setMaxLevel(4);
            }

            res.add(item);

            webSocketHandler.sendMessageToUsers(new TextMessage(JSON.toJSONString(res)), userIds);
            //向前台通知该

            //只给有权限的用户发送消息
            webSocketHandler.sendMessageToUsers(new TextMessage(JSON.toJSONString(response)), userIds);


        } catch (Exception e) {
            log.error("AlarmMsgListener deal error: " + e);
        }
    }


}
