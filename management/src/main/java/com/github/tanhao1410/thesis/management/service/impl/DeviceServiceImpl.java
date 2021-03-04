package com.github.tanhao1410.thesis.management.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.tanhao1410.thesis.common.domain.DeviceDO;
import com.github.tanhao1410.thesis.common.mapper.DeviceDOMapper;
import com.github.tanhao1410.thesis.management.service.DeviceService;
import com.github.tanhao1410.thesis.mq.MQConstant;
import com.github.tanhao1410.thesis.mq.RedisService;
import com.github.tanhao1410.thesis.mq.bean.DeviceChangeMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class DeviceServiceImpl implements DeviceService {

    @Autowired
    private DeviceDOMapper deviceDOMapper;

    @Autowired
    private RedisService redisService;

    @Override
    public void deleteDeviceById(Long id) throws Exception {

        final DeviceDO deviceDO = deviceDOMapper.selectByPrimaryKey(id);
        deviceDOMapper.deleteByPrimaryKey(id);

        DeviceChangeMsg msg = new DeviceChangeMsg();
        msg.setId(deviceDO.getId());
        msg.setIp(deviceDO.getIp());
        msg.setPort(deviceDO.getPort());
        msg.setType(DeviceChangeMsg.Type.DELETE.getId());

        //发布消息通知采集服务端，新增了设备
        redisService.pubMessage(MQConstant.DEVICE_CHANGE_MESSAGE_NAME, JSON.toJSONString(msg));

    }
    @Override
    public List<DeviceDO> getAllDevice(Long groupId) throws Exception {
        DeviceDO queryDo = new DeviceDO();
        queryDo.setGroupId(groupId);
        return deviceDOMapper.selectPageSelective(queryDo,new PageRequest(0,Integer.MAX_VALUE,null));
    }

    @Override
    public DeviceDO createDevice(DeviceDO deviceDO) throws Exception {
        final Long id = deviceDOMapper.insertSelectiveReturnPrimaryKey(deviceDO);
        deviceDO.setId(id);

        DeviceChangeMsg msg = new DeviceChangeMsg();
        msg.setId(deviceDO.getId());
        msg.setIp(deviceDO.getIp());
        msg.setPort(deviceDO.getPort());
        msg.setType(DeviceChangeMsg.Type.CREATE.getId());

        //发布消息通知采集服务端，新增了设备
        redisService.pubMessage(MQConstant.DEVICE_CHANGE_MESSAGE_NAME, JSON.toJSONString(msg));

        return deviceDO;
    }

}
