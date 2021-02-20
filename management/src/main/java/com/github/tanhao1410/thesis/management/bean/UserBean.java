package com.github.tanhao1410.thesis.management.bean;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @Author hushanwen
 */
@Setter
@Getter
public class UserBean implements Serializable {

    private Long id;

    /**
     * 登陆token
     */
    private String token;

    /**
     * 用户名
     */
    private String userName;

}
