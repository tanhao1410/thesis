package com.github.tanhao1410.thesis.management.service;



import com.github.tanhao1410.thesis.management.bean.ResultContentVO;
import com.github.tanhao1410.thesis.management.bean.UserBean;
import com.github.tanhao1410.thesis.management.bean.request.LoginOutRequest;
import com.github.tanhao1410.thesis.management.bean.response.LoginResponse;

import java.util.Map;

/**
 * @author tanhao
 * @date 2020/7/17 18:16
 */
public interface IUserBusinessService {

    /**
     * 登录用户
     * @param userName
     * @param password
     * @return
     */
    ResultContentVO<LoginResponse> login(String userName, String password);

    /**
     * 用户信息
     * @param user
     * @return
     */
    ResultContentVO<Map<String, Object>> userInfo(UserBean user);


    /**
     * 退出登录
     * @param loginOutRequest
     * @return
     */
    ResultContentVO<String> loginOut(LoginOutRequest loginOutRequest);


}
