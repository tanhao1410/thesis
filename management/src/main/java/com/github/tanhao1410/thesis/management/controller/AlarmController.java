package com.github.tanhao1410.thesis.management.controller;



import com.github.tanhao1410.thesis.common.bean.ActionResult;
import com.github.tanhao1410.thesis.common.bean.UserBean;
import com.github.tanhao1410.thesis.common.bean.response.AlarmListResponse;
import com.github.tanhao1410.thesis.common.bean.response.GroupAlarmInfoResponse;
import com.github.tanhao1410.thesis.common.domain.AlarmDO;
import com.github.tanhao1410.thesis.management.service.AlarmService;
import com.github.tanhao1410.thesis.user.interceptor.LoginContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@RequestMapping("/alarm")
@Controller
public class AlarmController {

    @Autowired
    AlarmService alarmService;

    /**
     * 获取某设备组下的所有的告警
     */
    @ResponseBody
    @RequestMapping( method = RequestMethod.GET)
    public ResponseEntity getDeviceAlarmsByGroupId(@RequestParam("groupId") Long groupId) {

        ActionResult result = new ActionResult();
        try {
            AlarmListResponse response = alarmService.getAllAlarm(groupId);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            result.setMsg(e.toString());
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
    }

    /**
     * 获取所有的设备组告警信息
     */
    @ResponseBody
    @RequestMapping( "/groupAlarmInfo")
    public ResponseEntity groupAlarmInfo(@LoginContext UserBean userBean)  {

        ActionResult result = new ActionResult();
        try {
            List<GroupAlarmInfoResponse> res = alarmService.groupAlarmInfo(userBean);
            return ResponseEntity.status(HttpStatus.OK).body(res);
        } catch (Exception e) {
            result.setMsg(e.toString());
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
    }


}
