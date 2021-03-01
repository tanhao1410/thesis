package com.github.tanhao1410.thesis.common.bean.request;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author hushawen
 * @date 2020/7/8 15:23
 */
@Setter
@Getter
public class LoginOutRequest implements Serializable {

    /**
     * token
     */
    private String token;
}
