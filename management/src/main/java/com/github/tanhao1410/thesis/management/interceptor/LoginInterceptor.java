package com.github.tanhao1410.thesis.management.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.github.tanhao1410.thesis.management.bean.ResultContentVO;
import com.github.tanhao1410.thesis.management.bean.UserBean;
import com.github.tanhao1410.thesis.management.constant.RedisConfConstant;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;



/**
 * Created by hushanwen on 2020/7/3.
 */
@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {

    private static Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);

    private static String JSON_MEDIA_TYPE = "application/json";

    public static final String LOGIN_ATTRIBUTE_NAME = "__" + LoginInterceptor.class.getName() + "_loginAttributeName";


    public LoginInterceptor() {

    }

    private LoginContext getLoginContextInfo(HandlerMethod handlerMethod) {
        MethodParameter[] arr = handlerMethod.getMethodParameters();
        int len = arr.length;

        for (int i = 0; i < len; ++i) {
            MethodParameter param = arr[i];
            LoginContext loginContext = (LoginContext) param.getParameterAnnotation(LoginContext.class);
            if (loginContext != null) {
                return loginContext;
            }
        }
        return null;
    }

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler.getClass().isAssignableFrom(HandlerMethod.class)) {
            LoginContext loginContext = this.getLoginContextInfo((HandlerMethod) handler);
            if (loginContext == null) {
                return true;
            }

            //先从header中获取
            String token = request.getHeader("token");

            //如果冲head中，没有获取到token，从cookie中获取（应对H5）
            if (StringUtils.isBlank(token)) {
                Cookie[] cookies = request.getCookies();
                // 然后迭代之
                if (cookies != null && cookies.length > 0) { //如果没有设置过Cookie会返回null
                    for (Cookie cookie : cookies) {
                        if (cookie.getName().equals("token")) {
                            token = cookie.getValue();
                            System.out.println(token);
                        }
                    }
                }
            }

            //如果还是没有，则直接返回
            if (StringUtils.isBlank(token)) {
                return handleNotLogin(request, response, loginContext);
            }

            UserBean obj = null;
            try {
                String objIds = ExpireMap.get(RedisConfConstant.USER_LOGIN_TOKEN_PRE + token);
                obj = JSONObject.parseObject(objIds, UserBean.class);
            } catch (Exception e) {
                logger.error("instantiation  userRedisUtil  error", e);
            }

            if (obj != null) {
                request.setAttribute(LOGIN_ATTRIBUTE_NAME, obj);
                ExpireMap.expireKey(RedisConfConstant.USER_LOGIN_TOKEN_PRE + token, 3600, TimeUnit.SECONDS);
            } else {
                return handleNotLogin(request, response, loginContext);
            }
        }
        return true;
    }

    boolean handleNotLogin(HttpServletRequest request, HttpServletResponse response, LoginContext loginContext) {

        if (!loginContext.required()) {
            return true;
        } else {
            if (loginContext.isAjax()) {
                ResultContentVO result = new ResultContentVO();
                result.setCode(88);
                result.setResultMessage("用户未登录");
                String resultJson = JSONObject.toJSONString(result);
                response.setContentType(JSON_MEDIA_TYPE);
                response.setCharacterEncoding("UTF-8");

                try {
                    response.getWriter().write(resultJson);
                    response.getWriter().flush();
                } catch (IOException e) {
                    logger.error("write json error", e);
                }
            }
            return false;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
