package com.github.tanhao1410.thesis.management.service;


import com.github.tanhao1410.thesis.common.domain.DeviceDO;

import java.util.List;
import java.util.Set;

public interface DeviceService {

    /**
     * 获取设备组下面的所有设备
     * @param networkId
     * @return
     * @throws Exception
     */
    List<DeviceDO> getAllDevice(Long networkId) throws Exception;

    DeviceDO createNode(DeviceDO node) throws Exception;

    Set<DeviceDO> getAllNetwork() throws Exception;

    String getWebserverName();

    void deleteNodeById(String id)throws Exception;
}
