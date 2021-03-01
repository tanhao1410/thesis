package com.github.tanhao1410.thesis.protocol.bean;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author tanhao
 * @date 2021/02/24 10:35
 */
@Getter
@Setter
public class MonitoringConfig implements Serializable {

    //监控策略相关
    /**
     * 监控方法
     */
    private String monitoringMethod;

    /**
     * 间隔时间
     */
    private Integer interval;

    private Long ruleId;

    private String name;



    //监控项相关

    /**
     * 阈值
     */
    private String threshold;

    /**
     * 参数
     */
    private String param;

    /**
     * 告警条件
     */
    private String alarmCondition;

    //其他
    /**
     * 类型，是性能监控还是数据监控
     */
    private Integer type;
}
