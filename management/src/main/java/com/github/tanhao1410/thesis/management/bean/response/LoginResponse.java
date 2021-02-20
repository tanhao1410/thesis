package com.github.tanhao1410.thesis.management.bean.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class LoginResponse implements Serializable {

    /**
     * 登陆成功之后的跳转地址
     */
    private String redirtUrl;

    /**
     * 登陆token
     */
    private String token;
}
