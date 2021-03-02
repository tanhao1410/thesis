package com.github.tanhao1410.thesis.management.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.tanhao1410.thesis.common.bean.ActionResult;
import com.github.tanhao1410.thesis.common.bean.UserBean;
import com.github.tanhao1410.thesis.common.domain.DeviceDO;
import com.github.tanhao1410.thesis.management.service.DeviceService;
import com.github.tanhao1410.thesis.user.interceptor.LoginContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/device")
@Controller
class DeviceController {

    @Autowired
    DeviceService deviceService;


    /**
     * 获取所有的设备
     */
    @ResponseBody
    @RequestMapping( method = RequestMethod.GET)
    public ResponseEntity getAllNetwork(@RequestParam("groupId") Long groupId) {

        ActionResult result = new ActionResult();
        try {
            List<DeviceDO> list = deviceService.getAllDevice(groupId);
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
            deviceService.deleteNodeById(id);
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
            DeviceDO node1 = deviceService.createNode(node);
            return ResponseEntity.status(HttpStatus.OK).body(node1);
        } catch (Exception e) {
            result.setMsg(e.toString());
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
    }
}