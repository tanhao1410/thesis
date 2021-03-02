package com.github.tanhao1410.thesis.management.service.impl;

import com.github.tanhao1410.thesis.common.domain.DeviceDO;
import com.github.tanhao1410.thesis.common.mapper.DeviceDOMapper;
import com.github.tanhao1410.thesis.management.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class DeviceServiceImpl implements DeviceService {

    @Autowired
    private DeviceDOMapper deviceDOMapper;

    @Override
    public void deleteNodeById(String id) throws Exception {


    }
    @Override
    public List<DeviceDO> getAllDevice(Long groupId) throws Exception {
        DeviceDO queryDo = new DeviceDO();
        queryDo.setGroupId(groupId);
        return deviceDOMapper.selectPageSelective(queryDo,new PageRequest(0,Integer.MAX_VALUE,null));
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
