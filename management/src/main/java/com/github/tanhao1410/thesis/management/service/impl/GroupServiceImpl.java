package com.github.tanhao1410.thesis.management.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.tanhao1410.thesis.common.domain.GroupDO;
import com.github.tanhao1410.thesis.common.mapper.GroupDOMapper;
import com.github.tanhao1410.thesis.management.service.GroupService;
import org.apache.ibatis.annotations.ResultMap;
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
    public void updateNetwork(GroupDO network) throws Exception {

    }

    @Override
    public List<GroupDO> getAllNetwork() throws Exception {
        GroupDO queryDo = new GroupDO();
        //queryDo.setId(1L);
        queryDo.setX(30);
        ArrayList<GroupDO> res = new ArrayList();
        res.add(groupDOMapper.selectByPrimaryKey(1L));
        return res;
    }

    @Override
    public void saveLocation(String jsonParam) throws Exception {
        JSONArray jsonArray = JSONObject.parseArray(jsonParam);
        for(int i = 0;i < jsonArray.size();i ++){
           JSONObject obj =  jsonArray.getJSONObject(i);
           if("node".equals(obj.getString("type"))){
               //说明要保存的节点是设备节点

           }else{

           }
        }
    }

    @Override
    public void deleteNetworkById(String id) throws Exception {

    }
}
