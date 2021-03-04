package com.github.tanhao1410.thesis.management.service.impl;


import com.github.tanhao1410.thesis.common.bean.UserBean;
import com.github.tanhao1410.thesis.common.bean.response.AlarmListResponse;
import com.github.tanhao1410.thesis.common.bean.response.GroupAlarmInfoResponse;
import com.github.tanhao1410.thesis.common.domain.AlarmDO;
import com.github.tanhao1410.thesis.common.domain.DeviceDO;
import com.github.tanhao1410.thesis.common.domain.GroupDO;
import com.github.tanhao1410.thesis.common.mapper.AlarmDOMapper;
import com.github.tanhao1410.thesis.common.mapper.DeviceDOMapper;
import com.github.tanhao1410.thesis.management.service.AlarmService;
import com.github.tanhao1410.thesis.management.service.GroupService;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class AlarmServiceImpl implements AlarmService {

    @Resource
    private DeviceDOMapper deviceDOMapper;

    @Resource
    private AlarmDOMapper alarmDOMapper;

    @Resource
    private GroupService groupService;


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

    @Override
    public List<GroupAlarmInfoResponse> groupAlarmInfo(UserBean userBean) throws Exception {
        //获取拥有权限的所有组
        final List<GroupDO> allGroup = groupService.getAllNetwork(userBean);

        List<GroupAlarmInfoResponse> res = new ArrayList<>();

        allGroup.forEach(e -> {
            GroupAlarmInfoResponse item = new GroupAlarmInfoResponse();
            item.setMaxLevel(0);
            //设置基本信息
            item.setDeviceNumber(getDeviceNumber(e.getId()));

            item.setGroupId(e.getId());
            item.setGroupName(e.getName());
            item.setOneLevelNumber(getAlarmNum(e.getId(), 1));
            if (item.getOneLevelNumber() > 0) {
                item.setMaxLevel(1);
            }

            item.setTwoLevelNumber(getAlarmNum(e.getId(), 2));
            if (item.getTwoLevelNumber() > 0) {
                item.setMaxLevel(2);
            }

            item.setThreeLevelNumber(getAlarmNum(e.getId(), 3));
            if (item.getThreeLevelNumber() > 0) {
                item.setMaxLevel(3);
            }

            item.setFourLevelNumber(getAlarmNum(e.getId(), 4));
            if (item.getFourLevelNumber() > 0) {
                item.setMaxLevel(4);
            }

            res.add(item);
        });

        return res;
    }

    /**
     * @param groupId
     * @param level
     * @return
     */
    public Integer getAlarmNum(Long groupId, Integer level) {
        AtomicReference<Integer> res = new AtomicReference<>(0);
        //查询所有的设备，查询设备下所有的告警，求和。
        final DeviceDO deviceDO = new DeviceDO();
        deviceDO.setGroupId(groupId);
        final List<DeviceDO> deviceDOS = deviceDOMapper.selectPageSelective(deviceDO, new PageRequest(0, Integer.MAX_VALUE, null));
        deviceDOS.forEach(device -> {
            AlarmDO alarmDO = new AlarmDO();
            alarmDO.setLevel(level);
            alarmDO.setDeviceId(device.getId());
            final Long num = alarmDOMapper.selectCountSelective(alarmDO);
            res.updateAndGet(v -> v + num.intValue());
        });
        return res.get();
    }

    /**
     * 得到网络下的设备数量
     *
     * @param groupId
     * @return
     */
    public Integer getDeviceNumber(Long groupId) {
        DeviceDO queryDo = new DeviceDO();
        queryDo.setGroupId(groupId);
        return deviceDOMapper.selectCountSelective(queryDo).intValue();
    }
}
