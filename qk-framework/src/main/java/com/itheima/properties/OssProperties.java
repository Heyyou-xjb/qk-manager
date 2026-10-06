package com.itheima.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "qk.oss")
@Component
@Data
public class OssProperties {
    private String endpoint;
    private String region;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;
}
