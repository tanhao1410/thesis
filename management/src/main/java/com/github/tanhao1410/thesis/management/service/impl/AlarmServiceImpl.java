package com.github.tanhao1410.thesis.management.service.impl;


import com.github.tanhao1410.thesis.common.domain.AlarmDO;
import com.github.tanhao1410.thesis.common.domain.DeviceDO;
import com.github.tanhao1410.thesis.management.service.AlarmService;
import com.github.tanhao1410.thesis.management.service.NodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlarmServiceImpl implements AlarmService {

    @Autowired
    NodeService nodeService;

    @Override
    public List<AlarmDO> getAllAlarm(String networkId) throws Exception {

        //根据networkID获知所有的设备
        List<DeviceDO> nodes = nodeService.getAllNetwork(networkId);
        //根据设备从Redis中查询出所有的告警
        return null;
    }
}
