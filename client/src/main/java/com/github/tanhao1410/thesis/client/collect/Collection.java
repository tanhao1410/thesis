package com.github.tanhao1410.thesis.client.collect;

import java.lang.annotation.*;

/**
 * 自定义注解，表明该类属于采集方法类，系统运行后，会自动加载
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Collection {
    String value();
}
