package com.github.tanhao1410.thesis.user.interceptor;

import com.github.tanhao1410.thesis.common.bean.UserBean;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * Created by lizhengwu on 2017/9/7.
 */
public class LoginInfoResolver implements HandlerMethodArgumentResolver  {
    public LoginInfoResolver() {
    }

    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterAnnotation(LoginContext.class) != null;
    }

    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        UserBean user = (UserBean) webRequest.getAttribute(LoginInterceptor.LOGIN_ATTRIBUTE_NAME, NativeWebRequest.SCOPE_REQUEST);
//        UserBean userBean = JSONObject.parseObject(user,UserBean.class);
//        HttpServletRequest servletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
//        UserBean  user = (UserBean)servletRequest.getAttribute(LoginInterceptor.LOGIN_ATTRIBUTE_NAME);
        return user;
    }
}
