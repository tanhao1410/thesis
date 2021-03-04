package com.github.tanhao1410.thesis.management.service;



import com.github.tanhao1410.thesis.common.bean.UserBean;
import com.github.tanhao1410.thesis.common.bean.response.AlarmListResponse;
import com.github.tanhao1410.thesis.common.bean.response.GroupAlarmInfoResponse;
import com.github.tanhao1410.thesis.common.domain.AlarmDO;

import java.util.List;

public interface AlarmService {
    /**
     *
     * 获取网络下的所有设备的相关告警情况。
     * @param networkId
     * @return
     * @throws Exception
     */
    AlarmListResponse getAllAlarm(Long networkId) throws Exception;

    /**
     * 取所有的设备组告警信息
     * @param userBean
     * @return
     */
    List<GroupAlarmInfoResponse> groupAlarmInfo(UserBean userBean) throws Exception;
}
