package com.github.tanhao1410.thesis.server.service.impl;

import com.github.tanhao1410.thesis.common.domain.MonitoringItemDO;
import com.github.tanhao1410.thesis.common.mapper.MonitoringItemDOMapper;
import com.github.tanhao1410.thesis.common.mapper.MonitoringRuleDOMapper;
import com.github.tanhao1410.thesis.protocol.bean.MonitoringConfig;
import com.github.tanhao1410.thesis.server.service.MonitoringConfigService;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author tanhao
 * @date 2021/03/01 13:38
 */
@Service
public class MonitoringConfigServiceImpl implements MonitoringConfigService {

    @Resource
    private MonitoringItemDOMapper monitoringItemDOMapper;

    @Resource
    private MonitoringRuleDOMapper monitoringRuleDOMapper;

    @Override
    public List<MonitoringConfig> getMonitoringConfigsByDeviceId(Long deviceId) {

        MonitoringItemDO queryDo = new MonitoringItemDO();
        queryDo.setDeviceId(deviceId);
        final List<MonitoringItemDO> monitoringItemDOS = monitoringItemDOMapper.selectPageSelective(queryDo, new PageRequest(0, 100,null));

        //根据配置项生成 返回的配置结果
        final List<MonitoringConfig> configs = monitoringItemDOS.stream().map(e -> {
            MonitoringConfig config = new MonitoringConfig();
            config.setAlarmCondition(e.getAlarmCondition());
            config.setInterval(e.getIntervalTime());
            //config.setMonitoringMethod(e.get);
            //config.setName(e);
            config.setParam(e.getParam());
            //config.setRuleId(e.get);
            config.setThreshold(e.getThreshold());
            config.setType(e.getIsAlarm()?0:3);
            config.setRuleId(e.getRuleId());
            config.setMonitoringMethod(e.getMonitoringMethod());
            config.setDeviceId(e.getDeviceId());
            return config;
        }).collect(Collectors.toList());
        return configs;
    }
}
