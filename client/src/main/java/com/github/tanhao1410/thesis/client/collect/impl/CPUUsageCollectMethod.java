package com.github.tanhao1410.thesis.client.collect.impl;

import com.github.tanhao1410.thesis.client.collect.AbstractCollectMethod;
import com.github.tanhao1410.thesis.client.collect.Collection;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;

import java.text.DecimalFormat;
import java.util.concurrent.TimeUnit;

/**
 * cpu 数据监控
 * @author tanhao
 * @date 2021/02/24 12:47
 */
@Collection("cpu使用率监控方法")
public class CPUUsageCollectMethod extends AbstractCollectMethod {



    @Override
    public String getValue(String param) {
        try{
            return new DecimalFormat("#.##").format(getCPUInfo());
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 返回cpu使用率， *100
     * @throws InterruptedException
     */
    private double getCPUInfo() throws InterruptedException {

        SystemInfo systemInfo = new SystemInfo();
        CentralProcessor processor = systemInfo.getHardware().getProcessor();
        // 睡眠10ms
        TimeUnit.MILLISECONDS.sleep(10);
        return processor.getSystemCpuLoadBetweenTicks() * 100;
    }

}
