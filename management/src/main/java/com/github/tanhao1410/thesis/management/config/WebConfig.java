package com.github.tanhao1410.thesis.management.config;

import com.github.tanhao1410.thesis.user.interceptor.LoginInfoResolver;
import com.github.tanhao1410.thesis.user.interceptor.LoginInterceptor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

@Configuration
@ComponentScan(basePackages = {"com.github.tanhao1410.thesis.user","com.github.tanhao1410.thesis.email"})
public class WebConfig extends WebMvcConfigurerAdapter {

    /**
     * 增加拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor());
    }

    /**
     * 增加参数解析器
     * @param argumentResolvers
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        super.addArgumentResolvers(argumentResolvers);
        argumentResolvers.add(new LoginInfoResolver());

    }

}
