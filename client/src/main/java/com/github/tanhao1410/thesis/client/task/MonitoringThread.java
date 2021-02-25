package com.github.tanhao1410.thesis.client.task;

import com.github.tanhao1410.thesis.client.collect.AbstractCollectMethod;
import com.github.tanhao1410.thesis.protocol.MessageTypeEnum;
import com.github.tanhao1410.thesis.protocol.bean.MonitoringConfig;

import java.util.List;

/**
 * 信息采集线程
 * @author tanhao
 * @date 2021/02/24 11:21
 */
public class MonitoringThread extends Thread{



    //监控方法
    private AbstractCollectMethod method;

    private MonitoringConfig config;

    public MonitoringThread(MonitoringConfig config){
        this.method = AbstractCollectMethod.getCollectMethodByName(config.getMonitoringMethod());
        this.config = config;
    }


    @Override
    public void run() {

        while(true){

            //本次监测开始时间
            long startTime = System.currentTimeMillis();

            //如果是告警监控
            if (config.getType() == MessageTypeEnum.MONITORING_ALARM.getId()){



            }else if(config.getType() == MessageTypeEnum.MONITORING_DATA.getId()){
                //数据监控

                //直接传递给服务端结果即可。

            }


            //本次监测结束时间
            long endTime = System.currentTimeMillis();

            //线程需要等待的时间
            long needSleepTime = config.getInterval() * 1000 - (endTime - startTime);
            if(needSleepTime>0){
                try {
                    Thread.sleep(needSleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }

    }
}
