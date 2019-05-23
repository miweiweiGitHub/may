package com.wei.cn.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "system")
public class ConfigInfo {

    private String uploadPath;

    public static ConfigInfo getConfigInfo() {
        return new ConfigInfo();
    }
}
