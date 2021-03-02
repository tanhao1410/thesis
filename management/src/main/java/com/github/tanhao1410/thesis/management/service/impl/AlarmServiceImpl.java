package com.github.tanhao1410.thesis.management.service.impl;


import com.github.tanhao1410.thesis.common.domain.AlarmDO;
import com.github.tanhao1410.thesis.common.domain.DeviceDO;
import com.github.tanhao1410.thesis.management.service.AlarmService;
import com.github.tanhao1410.thesis.management.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlarmServiceImpl implements AlarmService {

    @Autowired
    DeviceService deviceService;

    @Override
    public List<AlarmDO> getAllAlarm(Long networkId) throws Exception {

        //根据networkID获知所有的设备

        //根据设备从Redis中查询出所有的告警
        return null;
    }
}
