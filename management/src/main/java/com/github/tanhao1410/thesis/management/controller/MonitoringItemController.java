package com.github.tanhao1410.thesis.management.controller;



import com.alibaba.fastjson.JSONObject;
import com.github.tanhao1410.thesis.common.bean.ActionResult;
import com.github.tanhao1410.thesis.common.bean.response.AlarmListResponse;
import com.github.tanhao1410.thesis.common.domain.DeviceDO;
import com.github.tanhao1410.thesis.common.domain.MonitoringItemDO;
import com.github.tanhao1410.thesis.common.domain.MonitoringRuleDO;
import com.github.tanhao1410.thesis.management.service.AlarmService;
import com.github.tanhao1410.thesis.management.service.MonitoringItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/monitoringItem")
@Controller
public class MonitoringItemController {

    @Autowired
    private MonitoringItemService monitoringItemService;

    /**
     * 获取所有的告警
     */
    @ResponseBody
    @RequestMapping( method = RequestMethod.GET)
    public ResponseEntity monitoringRules() {

        ActionResult result = new ActionResult();
        try {
            List<MonitoringRuleDO> response = monitoringItemService.getMonitoringRules();
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            result.setMsg(e.toString());
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
    }

    /**
     * 新建一个采集配置
     */
    @ResponseBody
    @RequestMapping( method = RequestMethod.POST)
    public ResponseEntity createMonitoringItem(@RequestBody String json) {
        //把json串转换成对象
        MonitoringItemDO itemDO = JSONObject.parseObject(json, MonitoringItemDO.class);
        ActionResult result = new ActionResult();
        try {
            MonitoringItemDO item = monitoringItemService.createMonitoringItem(itemDO);
            return ResponseEntity.status(HttpStatus.OK).body(item);
        } catch (Exception e) {
            result.setMsg(e.toString());
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
    }

    /**
     * 删除指定任务
     */
    @ResponseBody
    @RequestMapping( "/delete")
    public ResponseEntity deleteMonitoringItem(@RequestParam("id") Long id) {

        ActionResult result = new ActionResult();
        try {
            monitoringItemService.deleteMonitoringItemById(id);
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } catch (Exception e) {
            result.setMsg(e.toString());
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
    }


}
