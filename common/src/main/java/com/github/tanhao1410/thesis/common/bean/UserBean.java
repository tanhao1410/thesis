package com.github.tanhao1410.thesis.common.bean;

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

    /**
     * 是否是管理员用户
     */
    private boolean isAdmin;

}
