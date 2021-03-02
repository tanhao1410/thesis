package com.github.tanhao1410.thesis.management.controller;

import com.alibaba.fastjson.JSONObject;

import com.github.tanhao1410.thesis.common.bean.ActionResult;
import com.github.tanhao1410.thesis.common.bean.UserBean;
import com.github.tanhao1410.thesis.common.domain.GroupDO;
import com.github.tanhao1410.thesis.management.service.GroupService;
import com.github.tanhao1410.thesis.user.interceptor.LoginContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/group")
@Controller
class GroupController {

    @Autowired
    GroupService groupService;


    @RequestMapping(value = "/saveLocation", method = RequestMethod.POST)
    public ResponseEntity saveLocation(@RequestBody String jsonParam) {
        ActionResult result = new ActionResult();
        try {
            groupService.saveLocation(jsonParam);
            return ResponseEntity.ok(null);
        } catch (Exception e) {
            result.setMsg(e.toString());
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

//    @RequestMapping(value = "/{groupId}", method = RequestMethod.GET)
//    public ResponseEntity getNetworkById(@PathVariable("groupId") String networkId) {
//        ActionResult result = new ActionResult();
//        try {
//            GroupDO network = groupService.getNetworkById(networkId);
//            if (null == network ) {
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//            }
//            return ResponseEntity.ok(network);
//        } catch (Exception e) {
//            result.setMsg(e.toString());
//            e.printStackTrace();
//        }
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//    }

    /**
     * 创建设备组
     */
    @ResponseBody
    @RequestMapping( method = RequestMethod.POST)
    public ResponseEntity createNetwork(@RequestBody String json) {

        //把json串转换成对象
        GroupDO network = JSONObject.parseObject(json,GroupDO.class);

        ActionResult result = new ActionResult();

        try {
            GroupDO network1 = groupService.createNetwork(network);
            return ResponseEntity.status(HttpStatus.OK).body(network1);
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
    public ResponseEntity deleteNode(@RequestParam("id") Long id) {

        ActionResult result = new ActionResult();
        try {
            groupService.deleteNetworkById(id);
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } catch (Exception e) {
            result.setMsg(e.toString());
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
    }

    /**
     * 获取所有的网络
     */
    @ResponseBody
    @RequestMapping( method = RequestMethod.GET)
    public ResponseEntity getAllNetwork(UserBean user) {

        ActionResult result = new ActionResult();
        try {
            List<GroupDO> list = groupService.getAllNetwork(user);
            return ResponseEntity.status(HttpStatus.OK).body(list);
        } catch (Exception e) {
            result.setMsg(e.toString());
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
    }


}