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

    /**
     * 创建一个设备
     * @param node
     * @return
     * @throws Exception
     */
    DeviceDO createDevice(DeviceDO node) throws Exception;


    /**
     * 删除一个设备
     * @param id
     * @throws Exception
     */
    void deleteDeviceById(Long id)throws Exception;
}
