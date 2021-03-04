package com.github.tanhao1410.thesis.management.service;


import com.github.tanhao1410.thesis.common.bean.UserBean;
import com.github.tanhao1410.thesis.common.domain.GroupDO;

import java.util.List;

public interface GroupService {

    /**
     * 根据设备组id获取设备组信息
     * @param groupId
     * @return
     * @throws Exception
     */
    GroupDO getNetworkById(Long groupId) throws Exception;

    /**
     * 创建设备组
     * @param network
     * @return
     * @throws Exception
     */
    GroupDO createNetwork(GroupDO network) throws Exception;

    /**
     * 获取所有的设备组
     * @return
     * @throws Exception
     */
    List<GroupDO> getAllNetwork(UserBean userBean) throws Exception;

    /**
     * 保存设备组位置信息
     * @param jsonParam
     * @throws Exception
     */
    void saveLocation(String jsonParam) throws Exception;

    /**
     * 删除设备组
     * @param id
     * @throws Exception
     */
    void deleteNetworkById(Long id)throws Exception;
}
