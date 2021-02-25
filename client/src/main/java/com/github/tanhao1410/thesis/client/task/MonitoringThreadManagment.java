package com.github.tanhao1410.thesis.client.task;

import com.github.tanhao1410.thesis.protocol.bean.MonitoringConfig;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 管理采集线程
 * @author tanhao
 * @date 2021/02/24 17:07
 */

public class MonitoringThreadManagment {

    private static List<MonitoringThread> monitoringThreadList = new ArrayList<>();


    public  static void startMonitoringThread(List<MonitoringConfig> configList) {

        //停止原来的轮询，开始新的轮询计划
        monitoringThreadList.forEach(e -> {
            e.stop();
        });
        monitoringThreadList.removeAll(monitoringThreadList);

        //根据下发的配置启动相应的线程，线程应该接收配置参数，然后启动，轮询即可
        final List<MonitoringThread> newThreads = configList.stream().map(e -> {
            final MonitoringThread monitoringThread = new MonitoringThread(e);
            //启动线程
            monitoringThread.start();
            return monitoringThread;
        }).collect(Collectors.toList());

        monitoringThreadList.addAll(newThreads);
    }

}
