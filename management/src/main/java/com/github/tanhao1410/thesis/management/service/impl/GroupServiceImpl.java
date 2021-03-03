package com.github.tanhao1410.thesis.management.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.tanhao1410.thesis.common.bean.UserBean;
import com.github.tanhao1410.thesis.common.domain.DeviceDO;
import com.github.tanhao1410.thesis.common.domain.GroupDO;
import com.github.tanhao1410.thesis.common.mapper.DeviceDOMapper;
import com.github.tanhao1410.thesis.common.mapper.ExtGroupDOMapper;
import com.github.tanhao1410.thesis.common.mapper.GroupDOMapper;
import com.github.tanhao1410.thesis.management.service.GroupService;
import com.github.tanhao1410.thesis.mq.MQConstant;
import com.github.tanhao1410.thesis.mq.RedisService;
import org.apache.ibatis.annotations.ResultMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class GroupServiceImpl implements GroupService {

    @Resource
    private GroupDOMapper groupDOMapper;

    @Resource
    private DeviceDOMapper deviceDOMapper;

    @Resource
    private ExtGroupDOMapper extGroupDOMapper;

    @Override
    public GroupDO getNetworkById(String networkId) throws Exception {
        return null;
    }

    @Override
    public GroupDO createNetwork(GroupDO network) throws Exception {
        //生成主键
        String id = UUID.randomUUID().toString();

        return network;
    }


    @Override
    public List<GroupDO> getAllNetwork(UserBean userBean) throws Exception {
        return extGroupDOMapper.selectAll();
    }

    @Override
    public void saveLocation(String jsonParam) throws Exception {
        JSONArray jsonArray = JSONObject.parseArray(jsonParam);
        for(int i = 0;i < jsonArray.size();i ++){
           JSONObject obj =  jsonArray.getJSONObject(i);
           if("node".equals(obj.getString("type"))){
               //说明要保存的节点是设备节点
               DeviceDO deviceDO = deviceDOMapper.selectByPrimaryKey(obj.getLong("id"));
               deviceDO.setX(obj.getInteger("x"));
               deviceDO.setY(obj.getInteger("y"));
               deviceDOMapper.updateByPrimaryKey(deviceDO);
           }else{
               GroupDO deviceDO = groupDOMapper.selectByPrimaryKey(obj.getLong("id"));
               deviceDO.setX(obj.getInteger("x"));
               deviceDO.setY(obj.getInteger("y"));
               groupDOMapper.updateByPrimaryKey(deviceDO);
           }
        }
    }

    @Override
    public void deleteNetworkById(Long id) throws Exception {

    }
}
