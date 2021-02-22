package com.github.tanhao1410.thesis.management.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.tanhao1410.thesis.management.bean.ActionResult;
import com.github.tanhao1410.thesis.management.domain.DeviceDO;
import com.github.tanhao1410.thesis.management.service.NodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/node")
@Controller
class DeviceController {

    @Autowired
    NodeService nodeService;


    /**
     * 获取所有的设备
     */
    @ResponseBody
    @RequestMapping( method = RequestMethod.GET)
    public ResponseEntity getAllNetwork(@RequestParam("networkId") String networkId) {

        ActionResult result = new ActionResult();
        try {
            List<DeviceDO> list = nodeService.getAllNetwork(networkId);
            return ResponseEntity.status(HttpStatus.OK).body(list);
        } catch (Exception e) {
            result.setMsg(e.toString());
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
    }

    /**
     * 删除指定设备
     */
    @ResponseBody
    @RequestMapping( "/delete")
    public ResponseEntity deleteNode(@RequestParam("id") String id) {

        ActionResult result = new ActionResult();
        try {
            nodeService.deleteNodeById(id);
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } catch (Exception e) {
            result.setMsg(e.toString());
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
    }

    /**
     * 创建设备
     */
    @ResponseBody
    @RequestMapping( method = RequestMethod.POST)
    public ResponseEntity createNode(@RequestBody String json) {
        //把json串转换成对象
        DeviceDO node = JSONObject.parseObject(json,DeviceDO.class);
        ActionResult result = new ActionResult();
        try {
            DeviceDO node1 = nodeService.createNode(node);
            return ResponseEntity.status(HttpStatus.OK).body(node1);
        } catch (Exception e) {
            result.setMsg(e.toString());
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
    }
}