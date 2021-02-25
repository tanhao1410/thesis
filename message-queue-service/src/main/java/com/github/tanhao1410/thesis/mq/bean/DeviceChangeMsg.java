package com.github.tanhao1410.thesis.mq.bean;

import lombok.Getter;
import lombok.Setter;

/**
 * @author tanhao
 * @date 2021/02/24 10:16
 */
@Getter
@Setter
public class DeviceChangeMsg {

    private Long id;
    private Integer type;

    @Getter
    public enum Type {
        CREATE(1, "新增设备"),
        DELETE(2, "删除设备"),
        UPDATE(3, "更新设备");
        Integer id;
        String desc;

        Type(Integer id, String desc) {
            this.desc = desc;
            this.id = id;
        }
    }
}
