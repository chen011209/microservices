package cn.itcast.user.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

//@Data
//@Component
//@ConfigurationProperties(prefix = "pattern")
public class PatternProperties {

    /**
     * 配置优先级:远程指定环境>远程共享>本地
     */

    private String dateformat;
    private String envSharedValue;
    private String name;
}
