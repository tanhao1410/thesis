package com.github.tanhao1410.thesis.client.task;

import com.alibaba.fastjson.JSON;
import com.github.tanhao1410.thesis.client.collect.AbstractCollectMethod;
import com.github.tanhao1410.thesis.client.comm.ClientChannelManagement;
import com.github.tanhao1410.thesis.protocol.MessageProtocolInfo;
import com.github.tanhao1410.thesis.protocol.MessageTypeEnum;
import com.github.tanhao1410.thesis.protocol.TCPProtocolConstant;
import com.github.tanhao1410.thesis.protocol.bean.AlarmConditionEnum;
import com.github.tanhao1410.thesis.protocol.bean.MonitoringAlarm;
import com.github.tanhao1410.thesis.protocol.bean.MonitoringConfig;
import com.github.tanhao1410.thesis.protocol.bean.MonitoringData;
import io.netty.channel.ChannelHandlerContext;

import java.util.Date;
import java.util.List;

/**
 * 信息采集线程
 *
 * @author tanhao
 * @date 2021/02/24 11:21
 */
public class MonitoringThread extends Thread {

    private volatile boolean stop = false;

    //监控方法
    private AbstractCollectMethod method;

    private MonitoringConfig config;

    //客户端与服务端的连接
    private ChannelHandlerContext channelHandlerContext;

    public MonitoringThread(MonitoringConfig config) {
        this.method = AbstractCollectMethod.getCollectMethodByName(config.getMonitoringMethod());
        this.config = config;
        this.channelHandlerContext = ClientChannelManagement.getCTX();
    }

    /**
     * 停止采集
     */
    void stopThread(){
        stop = true;
    }

    @Override
    public void run() {

        System.out.println("信息采集客户端启动采集线程："+config.getMonitoringMethod());

        Boolean preStatus = null;

        while (!stop) {

            //本次监测开始时间
            long startTime = System.currentTimeMillis();

            //如果是告警监控
            if (config.getType() == MessageTypeEnum.MONITORING_ALARM.getId()) {

                //拿到监控值
                final String value = method.getValue(config.getParam());
                //config.getAlarmCondition()

                final boolean status = !AlarmConditionEnum.isAlarm(value, config.getThreshold(), config.getAlarmCondition());


                if(preStatus != null && preStatus.equals(status)){
                    continue;
                }

                MonitoringAlarm alarmInfo = new MonitoringAlarm();

                alarmInfo.setTime(System.currentTimeMillis());
                alarmInfo.setValue(value);
                alarmInfo.setDeviceId(config.getDeviceId());
                alarmInfo.setName(config.getItemName());
                alarmInfo.setItemId(config.getItemId());
                alarmInfo.setLevel(status?0:config.getLevel());
                alarmInfo.setIsNormal(status);

                //记录本次状态
                preStatus = status;

                final String alarmInfoStr = JSON.toJSONString(alarmInfo);
                //组件告警
                final MessageProtocolInfo.MessageProtocol msg = MessageProtocolInfo.MessageProtocol.newBuilder()
                        .setHead(TCPProtocolConstant.HEAD)
                        .setContent(alarmInfoStr)
                        .setLen(alarmInfoStr.length())
                        .setType(MessageTypeEnum.MONITORING_ALARM.getId())
                        .build();

                System.out.println("上传了告警数据：" + alarmInfoStr);
                channelHandlerContext.writeAndFlush(msg);

            } else if (config.getType() == MessageTypeEnum.MONITORING_DATA.getId()) {
                //数据监控

                //直接传递给服务端结果即可。
                MonitoringData monitoringData = new MonitoringData();

                monitoringData.setName(config.getItemName());
                monitoringData.setItemId(config.getItemId());
                monitoringData.setTime(System.currentTimeMillis());
                monitoringData.setValue(method.getValue(config.getParam()));
                monitoringData.setDeviceId(config.getDeviceId());

                final String monitoringDataStr = JSON.toJSONString(monitoringData);
                final MessageProtocolInfo.MessageProtocol msg = MessageProtocolInfo.MessageProtocol.newBuilder()
                        .setHead(TCPProtocolConstant.HEAD)
                        .setContent(monitoringDataStr)
                        .setLen(monitoringDataStr.length())
                        .setType(MessageTypeEnum.MONITORING_DATA.getId())
                        .build();

                System.out.println("上传了性能监控数据：" + monitoringDataStr);

                channelHandlerContext.writeAndFlush(msg);
            }


            //本次监测结束时间
            long endTime = System.currentTimeMillis();

            //线程需要等待的时间
            long needSleepTime = config.getInterval() * 1000 - (endTime - startTime);

            if (needSleepTime > 0) {
                try {
                    Thread.sleep(needSleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }

    }
}
