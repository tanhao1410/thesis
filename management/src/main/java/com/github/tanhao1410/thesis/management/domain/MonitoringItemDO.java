package com.github.tanhao1410.thesis.management.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 框架自动生成表模型和CRUD操作，勿修改；
 * 如特殊需要，请以Ext***Mapper自行扩展；
 * 生成日期 : 2021-02-20 15:53:32
 * @author ##your name##
 */
@Setter
@Getter
@ToString
public class MonitoringItemDO {
    private Long id;

    private Long deviceId;

    private Long monitoringId;

    private String threshold;

    private String param;

    private String alarmCondition;
}