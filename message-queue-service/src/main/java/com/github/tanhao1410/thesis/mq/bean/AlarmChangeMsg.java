package com.github.tanhao1410.thesis.mq.bean;

import lombok.Getter;
import lombok.Setter;

/**
 * @author tanhao
 * @date 2021/02/24 10:16
 */
@Getter
@Setter
public class AlarmChangeMsg {

    private Long deviceId;
    private Long alarmId;
}
