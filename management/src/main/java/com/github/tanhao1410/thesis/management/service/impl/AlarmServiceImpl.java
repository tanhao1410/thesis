package com.github.tanhao1410.thesis.management.service.impl;


import com.alibaba.fastjson.JSON;
import com.github.tanhao1410.thesis.common.bean.response.AlarmListResponse;
import com.github.tanhao1410.thesis.common.domain.AlarmDO;
import com.github.tanhao1410.thesis.common.domain.DeviceDO;
import com.github.tanhao1410.thesis.common.mapper.AlarmDOMapper;
import com.github.tanhao1410.thesis.common.mapper.DeviceDOMapper;
import com.github.tanhao1410.thesis.management.service.AlarmService;
import com.github.tanhao1410.thesis.management.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlarmServiceImpl implements AlarmService {

    @Resource
    private DeviceDOMapper deviceDOMapper;

    @Resource
    private AlarmDOMapper alarmDOMapper;


    @Override
    public AlarmListResponse getAllAlarm(Long groupId) throws Exception {

        //根据networkID获知所有的设备
        DeviceDO queryDo = new DeviceDO();
        queryDo.setGroupId(groupId);
        final List<DeviceDO> deviceDOS = deviceDOMapper.selectPageSelective(queryDo, new PageRequest(0, Integer.MAX_VALUE, null));

        AlarmListResponse response = new AlarmListResponse();
        //查询出所有的告警信息，封装返回。
        deviceDOS.stream().forEach(e -> {

            //查询出该设备对应的所有的告警监控
            AlarmDO alarmQuery = new AlarmDO();
            alarmQuery.setDeviceId(e.getId());
            final List<AlarmDO> alarmDOS = alarmDOMapper.selectPageSelective(alarmQuery, new PageRequest(0, Integer.MAX_VALUE, null));

            final List<AlarmListResponse.AlarmItem> alarmItems = alarmDOS.stream()
                    .map(AlarmListResponse.AlarmItem::new)
                    .sorted(new Comparator<AlarmListResponse.AlarmItem>() {
                        @Override
                        public int compare(AlarmListResponse.AlarmItem o1, AlarmListResponse.AlarmItem o2) {
                            return o2.getLevel().compareTo(o1.getLevel());
                        }
                    })
                    .collect(Collectors.toList());

            response.put(String.valueOf(e.getId()), alarmItems);

        });

        return response;
    }
}
