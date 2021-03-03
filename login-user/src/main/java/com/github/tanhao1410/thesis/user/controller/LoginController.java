package com.github.tanhao1410.thesis.user.controller;


import com.github.tanhao1410.thesis.common.bean.ResultContentVO;
import com.github.tanhao1410.thesis.common.bean.UserBean;
import com.github.tanhao1410.thesis.common.bean.request.LoginOutRequest;
import com.github.tanhao1410.thesis.common.bean.request.LoginRequest;
import com.github.tanhao1410.thesis.common.bean.response.LoginResponse;
import com.github.tanhao1410.thesis.common.utils.RSAUtils;
import com.github.tanhao1410.thesis.user.interceptor.LoginContext;
import com.github.tanhao1410.thesis.user.service.IUserBusinessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/loginUser")
public class LoginController {

    @Resource
    private IUserBusinessService userBusinessService;

    /**
     * 用户登陆
     *
     * @param request
     * @param response
     * @param loginRequest
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public ResultContentVO<LoginResponse> login(HttpServletRequest request, HttpServletResponse response,
                                                @RequestBody LoginRequest loginRequest) {

        final ResultContentVO<LoginResponse> result = userBusinessService.login(loginRequest.getUserName(), loginRequest.getPassword());
        //登录成功
        if (result.getCode() == 0) {
            //设置cookie
            Cookie cookie = new Cookie("token", result.getResult().getToken());
            cookie.setPath("/");
            response.addCookie(cookie);
            return result;
        }
        return result;
    }

    /**
     * 获取加密密码的公钥
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/rsaPublicKey", method = RequestMethod.GET)
    @ResponseBody
    public ResultContentVO<Map<String,String>> encodeKey(HttpServletRequest request) {
        Map<String,String> res = new HashMap<>();
        res.put("publicKey", RSAUtils.publicKey);
        return ResultContentVO.Su(res);
    }

    /**
     * 获取用户信息
     *
     * @param request
     * @param user
     * @return
     */
    @RequestMapping(value = "/userInfo", method = RequestMethod.GET)
    @ResponseBody
    public ResultContentVO<Map<String, Object>> userInfo(HttpServletRequest request, HttpServletResponse response,
                                                         @LoginContext UserBean user) {
        return userBusinessService.userInfo(user);
    }

    /**
     * 退出登陆
     *
     * @param request
     * @param user
     * @return
     */
    @RequestMapping(value = "/logOut", method = RequestMethod.POST)
    @ResponseBody
    public ResultContentVO<String> loginOut(HttpServletRequest request, @LoginContext UserBean user) {

        //先从header中获取
        String token = request.getHeader("token");
        //如果冲head中，没有获取到token，从cookie中获取（应对H5）
        if (org.apache.commons.lang3.StringUtils.isBlank(token)) {
            Cookie[] cookies = request.getCookies();
            // 然后迭代之
            if (cookies != null && cookies.length > 0) { //如果没有设置过Cookie会返回null
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("token")) {
                        token = cookie.getValue();
                    }
                }
            }
        }
        LoginOutRequest loginOutRequest = new LoginOutRequest();
        loginOutRequest.setToken(token);
        return userBusinessService.loginOut(loginOutRequest);
    }


}
