package com.github.tanhao1410.thesis.management.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.tanhao1410.thesis.common.domain.DeviceDO;
import com.github.tanhao1410.thesis.common.domain.MonitoringItemDO;
import com.github.tanhao1410.thesis.common.domain.MonitoringRuleDO;
import com.github.tanhao1410.thesis.common.mapper.DeviceDOMapper;
import com.github.tanhao1410.thesis.common.mapper.MonitoringItemDOMapper;
import com.github.tanhao1410.thesis.common.mapper.MonitoringRuleDOMapper;
import com.github.tanhao1410.thesis.management.service.MonitoringItemService;
import com.github.tanhao1410.thesis.mq.MQConstant;
import com.github.tanhao1410.thesis.mq.RedisService;
import com.github.tanhao1410.thesis.mq.bean.DeviceChangeMsg;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author tanhao
 * @date 2021/03/03 13:17
 */
@Service
public class MonitoringItemServiceImpl implements MonitoringItemService {

    @Resource
    private MonitoringRuleDOMapper ruleDOMapper;

    @Resource
    private DeviceDOMapper deviceDOMapper;

    @Resource
    private MonitoringItemDOMapper monitoringItemDOMapper;

    @Resource
    private RedisService redisService;

    @Override
    public List<MonitoringRuleDO> getMonitoringRules() {
        final List<MonitoringRuleDO> monitoringRuleDOS = ruleDOMapper.selectAll();
        return monitoringRuleDOS;
    }

    @Override
    public MonitoringItemDO createMonitoringItem(MonitoringItemDO itemDO) {

        monitoringItemDOMapper.insert(itemDO);

        final DeviceDO deviceDO = deviceDOMapper.selectByPrimaryKey(itemDO.getDeviceId());

        DeviceChangeMsg msg = new DeviceChangeMsg();
        msg.setId(deviceDO.getId());
        msg.setIp(deviceDO.getIp());
        msg.setPort(deviceDO.getPort());
        msg.setType(DeviceChangeMsg.Type.UPDATE.getId());

        //发布消息通知采集服务端，新增了设备
        redisService.pubMessage(MQConstant.DEVICE_CHANGE_MESSAGE_NAME, JSON.toJSONString(msg));

        return itemDO;
    }

    @Override
    public void deleteMonitoringItemById(Long id) {
        final MonitoringItemDO itemDO = monitoringItemDOMapper.selectByPrimaryKey(id);
        monitoringItemDOMapper.deleteByPrimaryKey(id);

        //相关的当前告警也需要删除，转为历史告警

        final DeviceDO deviceDO = deviceDOMapper.selectByPrimaryKey(itemDO.getDeviceId());

        DeviceChangeMsg msg = new DeviceChangeMsg();
        msg.setId(deviceDO.getId());
        msg.setIp(deviceDO.getIp());
        msg.setPort(deviceDO.getPort());
        msg.setType(DeviceChangeMsg.Type.UPDATE.getId());

        //发布消息通知采集服务端，新增了设备
        redisService.pubMessage(MQConstant.DEVICE_CHANGE_MESSAGE_NAME, JSON.toJSONString(msg));
    }
}
