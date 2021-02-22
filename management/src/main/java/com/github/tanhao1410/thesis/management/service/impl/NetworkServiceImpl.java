package com.github.tanhao1410.thesis.management.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.tanhao1410.thesis.management.domain.DeviceDO;
import com.github.tanhao1410.thesis.management.domain.GroupDO;
import com.github.tanhao1410.thesis.management.service.NetworkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class NetworkServiceImpl implements NetworkService {

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
        return null;
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
