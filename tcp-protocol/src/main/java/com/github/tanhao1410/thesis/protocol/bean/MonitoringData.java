package com.github.tanhao1410.thesis.protocol.bean;

import lombok.Getter;
import lombok.Setter;

/**
 * 监控数据
 * @author tanhao
 * @date 2021/02/24 13:39
 */
@Getter
@Setter
public class MonitoringData {

    /**
     * 发生时间
     */
    private Long time;
    /**
     * 监控值
     */
    private String value;

    /**
     * 监控项名称
     */
    private String name;

    private Long deviceId;

    /**
     * 监控项id
     */
    private Long itemId;


}
