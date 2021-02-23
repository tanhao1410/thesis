package com.github.tanhao1410.thesis.management.service;


import com.github.tanhao1410.thesis.common.domain.DeviceDO;

import java.util.List;
import java.util.Set;

public interface NodeService {

    List<DeviceDO> getAllNetwork(String networkId) throws Exception;

    DeviceDO createNode(DeviceDO node) throws Exception;

    Set<DeviceDO> getAllNetwork() throws Exception;

    String getWebserverName();

    void deleteNodeById(String id)throws Exception;
}
