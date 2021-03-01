package com.github.tanhao1410.thesis.management.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource(locations= {"classpath:spring/spring-reids.xml"})
@ComponentScan(basePackages = {"com.github.tanhao1410.thesis.mq"})
public class MsgQueueConfig {
}
