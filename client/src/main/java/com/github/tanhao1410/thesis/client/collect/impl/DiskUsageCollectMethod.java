package com.github.tanhao1410.thesis.client.collect.impl;

import com.github.tanhao1410.thesis.client.collect.AbstractCollectMethod;
import com.github.tanhao1410.thesis.client.collect.Collection;
import com.sun.management.OperatingSystemMXBean;
import oshi.hardware.ComputerSystem;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.text.DecimalFormat;

/**
 * @author tanhao
 * @date 2021/02/24 13:19
 */
@Collection("磁盘使用率监控方法")
public class DiskUsageCollectMethod extends AbstractCollectMethod {


    @Override
    public String getValue(String param) {

        // 磁盘使用情况
        File file = new File(param);
        final long totalSpace = file.getTotalSpace();
        final long usableSpace = file.getUsableSpace();

        return String.valueOf(usableSpace * 100 / totalSpace);
    }

    public static void main(String[] args) {
        File file = new File("c:");
        final long totalSpace = file.getTotalSpace();
        final long usableSpace = file.getUsableSpace();
        System.out.println(String.valueOf(usableSpace * 100 / totalSpace));

    }
}
