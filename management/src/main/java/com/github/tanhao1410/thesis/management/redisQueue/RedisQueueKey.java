package com.github.tanhao1410.thesis.management.redisQueue;

import org.springframework.stereotype.Service;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Service
public @interface RedisQueueKey {
    String source();
}