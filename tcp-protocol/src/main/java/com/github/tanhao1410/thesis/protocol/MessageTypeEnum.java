package com.github.tanhao1410.thesis.protocol;

import lombok.Getter;

@Getter
public enum MessageTypeEnum {
    MONITORING_ALARM(0,"告警数据"),
    MONITORING_CONFIG(1,"监控配置下发"),
    CLIENT_INFO(2,"客户端信息上报"),
    MONITORING_DATA(3,"监控数据"),

    //心跳包机制
    PING(4,"ping"),
    PONG(5,"pong")
    ;
    private Integer id;
    private String name;

     MessageTypeEnum(Integer id ,String name){
        this.id = id;
        this.name = name;
    }
}
