package com.github.tanhao1410.thesis.management.bean.request;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class LoginRequest implements Serializable {

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码--加密之后的（对称）
     */
    private String password;

}
