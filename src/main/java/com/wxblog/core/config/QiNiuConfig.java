package com.wxblog.core.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author: NightWish
 * @create:
 * @description:
 **/
@Data
@Component
@ConfigurationProperties(prefix="qiniu")
@PropertySource(value = "classpath:qiniu.yml")
public class QiNiuConfig {

    @Value("${accessKey}")
    private String accessKey;
    @Value("${secretKey}")
    private String secretKey;
    @Value("${bucket}")
    private String bucket;
    @Value("${domain}")
    private String domain;
    @Value("${waterMark}")
    private String waterMark;
}
