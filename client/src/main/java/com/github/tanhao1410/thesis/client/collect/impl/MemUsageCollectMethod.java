package com.github.tanhao1410.thesis.client.collect.impl;

import com.github.tanhao1410.thesis.client.collect.AbstractCollectMethod;
import com.github.tanhao1410.thesis.client.collect.Collection;
import com.sun.management.OperatingSystemMXBean;

import java.lang.management.ManagementFactory;

/**
 * @author tanhao
 * @date 2021/02/24 13:19
 */
@Collection("内存使用率监控方法")
public class MemUsageCollectMethod extends AbstractCollectMethod {


    @Override
    public String getValue(String param) {

        OperatingSystemMXBean osmxb = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

        final long usage = (osmxb.getTotalPhysicalMemorySize() - osmxb.getFreePhysicalMemorySize()) * 100
                / osmxb.getTotalPhysicalMemorySize();

        return String.valueOf(usage);
    }
}
