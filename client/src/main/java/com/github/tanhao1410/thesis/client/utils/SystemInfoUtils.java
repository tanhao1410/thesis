package com.github.tanhao1410.thesis.client.utils;

import oshi.SystemInfo;
import oshi.hardware.ComputerSystem;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.software.os.OperatingSystem;

/**
 * @author tanhao
 * @date 2021/02/25 09:21
 */
public class SystemInfoUtils {

    public static void main(String[] args) {
        System.out.println(getOperationSystem());
    }

    /**
     * 返回操作系统
     * @return
     */
    public static String getOperationSystem(){
        SystemInfo si = new SystemInfo();
        OperatingSystem os = si.getOperatingSystem();
        return os.toString();
    }

    /**
     * 返回设备生产厂家系统
     * @return
     */
    public static String get(){

        SystemInfo si = new SystemInfo();
        HardwareAbstractionLayer hal = si.getHardware();
        final ComputerSystem computerSystem = hal.getComputerSystem();
        return computerSystem.toString();
    }
}
