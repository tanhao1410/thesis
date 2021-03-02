package com.github.tanhao1410.thesis.management.service;



import com.github.tanhao1410.thesis.common.domain.AlarmDO;

import java.util.List;

public interface AlarmService {
    List<AlarmDO> getAllAlarm(Long networkId) throws Exception;
}
