package com.github.tanhao1410.thesis.management.service;

import com.github.tanhao1410.thesis.common.domain.MonitoringItemDO;
import com.github.tanhao1410.thesis.common.domain.MonitoringRuleDO;

import java.util.List;

/**
 * @author tanhao
 * @date 2021/03/03 13:17
 */
public interface MonitoringItemService {
    /**
     * 获取所有的监控规则
     * @return
     */
    List<MonitoringRuleDO> getMonitoringRules();

    /**
     * 新建一个采集配置
     * @param itemDO
     * @return
     */
    MonitoringItemDO createMonitoringItem(MonitoringItemDO itemDO);

    /**
     *
     * 删除一个采集配置
     * @param id
     */
    void deleteMonitoringItemById(Long id);
}
