package com.github.tanhao1410.thesis.client.collect.impl;

import com.github.tanhao1410.thesis.client.collect.AbstractCollectMethod;
import com.github.tanhao1410.thesis.client.collect.Collection;
import oshi.SystemInfo;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.software.os.OSProcess;
import oshi.software.os.OperatingSystem;
import oshi.util.FormatUtil;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * @author tanhao
 * @date 2021/02/24 13:19
 */
@Collection("服务存活状态监控方法")
public class ServiceStatusMonitoreMethod extends AbstractCollectMethod {

    @Override
    public String getValue(String param) {


        // 磁盘使用情况
        File file = new File(param);
        final long totalSpace = file.getTotalSpace();
        final long usableSpace = file.getUsableSpace();

        return String.valueOf(usableSpace * 100 / totalSpace);
    }

    private static void printProcesses() {
        SystemInfo si = new SystemInfo();
        HardwareAbstractionLayer hal = si.getHardware();
        OperatingSystem os = si.getOperatingSystem();
        GlobalMemory memory = hal.getMemory();
        System.out.println("Processes: " + os.getProcessCount() + ", Threads: " + os.getThreadCount());
        // Sort by highest CPU
        List<OSProcess> procs = Arrays.asList(os.getProcesses(5, OperatingSystem.ProcessSort.CPU));

        System.out.println("   PID  %CPU %MEM       VSZ       RSS Name");
        for (int i = 0; i < procs.size() && i < 5; i++) {
            OSProcess p = procs.get(i);
            System.out.format(" %5d %5.1f %4.1f %9s %9s %s%n", p.getProcessID(),
                    100d * (p.getKernelTime() + p.getUserTime()) / p.getUpTime(),
                    100d * p.getResidentSetSize() / memory.getTotal(), FormatUtil.formatBytes(p.getVirtualSize()),
                    FormatUtil.formatBytes(p.getResidentSetSize()), p.getName());
        }
    }

    public static void main(String[] args) {
        File file = new File("c:");
        final long totalSpace = file.getTotalSpace();
        final long usableSpace = file.getUsableSpace();
        System.out.println(String.valueOf(usableSpace * 100 / totalSpace));
    }
}
