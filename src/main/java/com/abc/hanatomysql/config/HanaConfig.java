package com.abc.hanatomysql.config;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author Z-7
 * @Date 2022/8/18
 */
@ApiModel(value = "hana数据库信息")
@Data
@Component
@ConfigurationProperties(prefix = "sap")
public class HanaConfig {
    @Value("url")
    private String url;

    @Value("username")
    private String userName;

    @Value("password")
    private String password;

    @Value("driver")
    private String driver;
}
