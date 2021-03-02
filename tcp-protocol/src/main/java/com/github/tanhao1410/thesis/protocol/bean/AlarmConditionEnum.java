package com.github.tanhao1410.thesis.protocol.bean;

import lombok.Getter;

import java.math.BigDecimal;

/**
 * 告警判断条件枚举
 *
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
     *
     * @param value
     * @param thresholdVlaue
     * @param condition
     * @return
     */
    public static boolean isAlarm(String value, String thresholdVlaue, String condition) {
        switch (condition) {
            case ">":
                //如果包含点的话，说明是小数，否则按long处理
                if (value.contains(".") || thresholdVlaue.contains(".")) {
                    return Double.parseDouble(value) > Double.parseDouble(thresholdVlaue);
                } else {
                    return Long.parseLong(value) > Long.parseLong(thresholdVlaue);
                }
            case "<":
                if (value.contains(".") || thresholdVlaue.contains(".")) {
                    return Double.parseDouble(value) < Double.parseDouble(thresholdVlaue);
                } else {
                    return Long.parseLong(value) < Long.parseLong(thresholdVlaue);
                }
            case ">=":
                //如果包含点的话，说明是小数，否则按long处理
                if (value.contains(".") || thresholdVlaue.contains(".")) {
                    return Double.parseDouble(value) >= Double.parseDouble(thresholdVlaue);
                } else {
                    return Long.parseLong(value) >= Long.parseLong(thresholdVlaue);
                }
            case "<=":
                if (value.contains(".") || thresholdVlaue.contains(".")) {
                    return Double.parseDouble(value) <= Double.parseDouble(thresholdVlaue);
                } else {
                    return Long.parseLong(value) <= Long.parseLong(thresholdVlaue);
                }
            case "==":
                //如果包含点的话，说明是小数，否则按long处理
                return value.equals(thresholdVlaue);
            case "!=":
                return !value.equals(thresholdVlaue);
            case "包含":
                //如果包含点的话，说明是小数，否则按long处理
                return value.contains(thresholdVlaue);
            case "不包含":
                return !value.contains(thresholdVlaue);
        }


        return false;
    }
}
