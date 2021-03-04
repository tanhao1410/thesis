package com.github.tanhao1410.thesis.management.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.tanhao1410.thesis.common.bean.UserBean;
import com.github.tanhao1410.thesis.common.domain.DeviceDO;
import com.github.tanhao1410.thesis.common.domain.GroupDO;
import com.github.tanhao1410.thesis.common.domain.UserGroupDO;
import com.github.tanhao1410.thesis.common.mapper.DeviceDOMapper;
import com.github.tanhao1410.thesis.common.mapper.ExtGroupDOMapper;
import com.github.tanhao1410.thesis.common.mapper.GroupDOMapper;
import com.github.tanhao1410.thesis.common.mapper.UserGroupDOMapper;
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
import java.util.stream.Collectors;

@Service
public class GroupServiceImpl implements GroupService {

    @Resource
    private GroupDOMapper groupDOMapper;

    @Resource
    private DeviceDOMapper deviceDOMapper;

    @Resource
    private ExtGroupDOMapper extGroupDOMapper;

    @Resource
    private UserGroupDOMapper userGroupDOMapper;

    @Override
    public GroupDO getNetworkById(Long groupId) throws Exception {
        return groupDOMapper.selectByPrimaryKey(groupId);
    }

    @Override
    public GroupDO createNetwork(GroupDO groupDO) throws Exception {
        final Long id = groupDOMapper.insertSelectiveReturnPrimaryKey(groupDO);
        groupDO.setId(id);
        return groupDO;
    }


    @Override
    public List<GroupDO> getAllNetwork(UserBean userBean) throws Exception {

        //管理员用户具有所有设备的权限
        if(userBean.isAdmin()){
            return extGroupDOMapper.selectAll();
        }

        //过滤掉不在权限范围内的设备组
        final UserGroupDO queryDO = new UserGroupDO();
        queryDO.setUserId(userBean.getId());
        final List<UserGroupDO> userGroupDOS = userGroupDOMapper.selectPageSelective(queryDO, new PageRequest(0, Integer.MAX_VALUE, null));
        List<Long> groupIds = userGroupDOS.stream().map(e->{
            return e.getGroupId();
        }).collect(Collectors.toList());

        if(groupIds == null || groupIds.size() == 0){
            return new ArrayList<>();
        }
        return groupDOMapper.batchSelectByPrimaryKey(groupIds);
    }

    @Override
    public void saveLocation(String jsonParam) throws Exception {
        JSONArray jsonArray = JSONObject.parseArray(jsonParam);
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            if ("node".equals(obj.getString("type"))) {
                //说明要保存的节点是设备节点
                DeviceDO deviceDO = deviceDOMapper.selectByPrimaryKey(obj.getLong("id"));
                deviceDO.setX(obj.getInteger("x"));
                deviceDO.setY(obj.getInteger("y"));
                deviceDOMapper.updateByPrimaryKey(deviceDO);
            } else {
                GroupDO deviceDO = groupDOMapper.selectByPrimaryKey(obj.getLong("id"));
                deviceDO.setX(obj.getInteger("x"));
                deviceDO.setY(obj.getInteger("y"));
                groupDOMapper.updateByPrimaryKey(deviceDO);
            }
        }
    }

    @Override
    public void deleteNetworkById(Long id) throws Exception {
        groupDOMapper.deleteByPrimaryKey(id);
    }
}
