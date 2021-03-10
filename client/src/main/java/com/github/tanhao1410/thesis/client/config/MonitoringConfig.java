package com.github.tanhao1410.thesis.client.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
@ConfigurationProperties(prefix = "monitoring")
public class MonitoringConfig {
    private List<com.github.tanhao1410.thesis.protocol.bean.MonitoringConfig> configs;
}