package com.github.tanhao1410.thesis.common.bean.response;

import com.alibaba.fastjson.JSONObject;
import com.github.tanhao1410.thesis.common.domain.AlarmDO;
import com.github.tanhao1410.thesis.common.utils.DateUtils;
import lombok.Getter;
import lombok.Setter;

/**
 * 设备组告警信息
 * @author tanhao
 * @date 2021/03/03 09:14
 */
@Getter
@Setter
public class GroupAlarmInfoResponse {

    private Long groupId;

    private Integer maxLevel;

    private String groupName;

    private Integer deviceNumber;

    private Integer haveAlarmDeviceNumber;

    private Integer oneLevelNumber;
    private Integer twoLevelNumber;
    private Integer threeLevelNumber;
    private Integer fourLevelNumber;



}
