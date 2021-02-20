package com.github.tanhao1410.thesis.management.interceptor;

import org.springframework.web.bind.annotation.Mapping;

import java.lang.annotation.*;

/**
 */
@Target({ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Mapping
public @interface LoginContext {
    boolean required() default true;

    boolean isAjax() default true;
}
