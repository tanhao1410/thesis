package com.github.tanhao1410.thesis.client.task;

import com.alibaba.fastjson.JSON;
import com.github.tanhao1410.thesis.client.collect.AbstractCollectMethod;
import com.github.tanhao1410.thesis.client.comm.ClientChannelManagement;
import com.github.tanhao1410.thesis.protocol.MessageProtocolInfo;
import com.github.tanhao1410.thesis.protocol.MessageTypeEnum;
import com.github.tanhao1410.thesis.protocol.TCPProtocolConstant;
import com.github.tanhao1410.thesis.protocol.bean.MonitoringAlarm;
import com.github.tanhao1410.thesis.protocol.bean.MonitoringConfig;
import io.netty.channel.ChannelHandlerContext;

import java.util.List;

/**
 * 信息采集线程
 *
 * @author tanhao
 * @date 2021/02/24 11:21
 */
public class MonitoringThread extends Thread {

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


    @Override
    public void run() {

        while (true) {

            //本次监测开始时间
            long startTime = System.currentTimeMillis();

            //如果是告警监控
            if (config.getType() == MessageTypeEnum.MONITORING_ALARM.getId()) {

                //拿到监控值
                final String value = method.getValue(config.getParam());
                //config.getAlarmCondition()

                MonitoringAlarm alarmInfo = new MonitoringAlarm();

                final String alarmInfoStr = JSON.toJSONString(alarmInfo);
                //组件告警
                final MessageProtocolInfo.MessageProtocol msg = MessageProtocolInfo.MessageProtocol.newBuilder()
                        .setHead(TCPProtocolConstant.HEAD)
                        .setContent(alarmInfoStr)
                        .setLen(alarmInfoStr.length())
                        .setType(MessageTypeEnum.MONITORING_ALARM.getId())
                        .build();
                channelHandlerContext.writeAndFlush(msg);

            } else if (config.getType() == MessageTypeEnum.MONITORING_DATA.getId()) {
                //数据监控

                //直接传递给服务端结果即可。

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
