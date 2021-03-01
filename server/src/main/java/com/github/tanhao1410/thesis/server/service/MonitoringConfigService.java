package com.github.tanhao1410.thesis.server.service;

import com.github.tanhao1410.thesis.protocol.bean.MonitoringConfig;

import java.util.List;

/**
 * @author tanhao
 * @date 2021/03/01 13:37
 */
public interface MonitoringConfigService {

    /**
     * 根据设备的id返回所有的设备配置信息
     * @param deviceId
     * @return
     */
    List<MonitoringConfig> getMonitoringConfigsByDeviceId(Long deviceId);
}
