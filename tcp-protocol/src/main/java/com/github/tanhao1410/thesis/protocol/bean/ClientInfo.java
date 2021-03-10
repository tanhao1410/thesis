package com.github.tanhao1410.thesis.protocol.bean;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * 信息采集客户端的基本信息，通过IP和端口号来确定唯一的一个设备
 * @author tanhao
 * @date 2021/02/24 10:31
 */
@Getter
@Setter
public class ClientInfo implements Serializable {
    /**
     * 采集客户端IP
     */
    private String ip;
    /**
     * 采集客户端端口号
     */
    private int port;

    /**
     * 设备的基本信息
     */
    private String operationSystem;
    private String manufacturer;
    //新增客户端上传信息
    private String deviceName;
    private String groupName;
    private String departmentName;
    private List<MonitoringConfig> monitoringConfigs;
}
