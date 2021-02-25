package com.github.tanhao1410.thesis.protocol.bean;

import lombok.Getter;

/**
 * 告警判断条件枚举
 * @author tanhao
 * @date 2021/02/25 13:37
 */
@Getter
public enum AlarmConditionEnum {
    BIGGER(1, ">"),
    BIGGER_EQUEL(2, ">="),
    SMALLER(3, "<"),
    SMALLER_EQUEL(4, "<="),
    EQUEL(5, "=="),
    NOT_EQUEL(6, "!="),

    CONTAIN(7, "包含"),
    NOT_CONTAIN(8, "不包含"),
    ;
    private Integer id;
    private String name;

    AlarmConditionEnum(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * 判断是否产生告警
     * @param value
     * @param thresholdVlaue
     * @param condition
     * @return
     */
    public static boolean isAlarm(String value,String thresholdVlaue ,Integer condition){

        return false;
    }
}
