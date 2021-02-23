package com.github.tanhao1410.thesis.management.service;


import com.github.tanhao1410.thesis.common.domain.GroupDO;

import java.util.List;

public interface GroupService {

    GroupDO getNetworkById(String networkId) throws Exception;

    GroupDO createNetwork(GroupDO network) throws Exception;

    void updateNetwork(GroupDO network) throws Exception;

    List<GroupDO> getAllNetwork() throws Exception;

    void saveLocation(String jsonParam) throws Exception;

    void deleteNetworkById(String id)throws Exception;
}
