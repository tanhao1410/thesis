package com.github.tanhao1410.thesis.management.service.impl;

import com.github.tanhao1410.thesis.management.domain.DeviceDO;
import com.github.tanhao1410.thesis.management.mapper.DeviceDOMapper;
import com.github.tanhao1410.thesis.management.service.NodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class NodeServiceImpl implements NodeService {

    @Autowired
    private DeviceDOMapper nodeMapper;



    @Override
    public void deleteNodeById(String id) throws Exception {


    }

    @Override
    public List<DeviceDO> getAllNetwork(String networkId) throws Exception {
        return null;
    }

    @Override
    public DeviceDO createNode(DeviceDO node) throws Exception {

        return node;
    }

    @Override
    public Set<DeviceDO> getAllNetwork() throws Exception {
        return null;
    }

    @Override
    public String getWebserverName() {
        return null;
    }
}
