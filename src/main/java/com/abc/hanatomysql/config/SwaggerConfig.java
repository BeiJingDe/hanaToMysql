package com.abc.hanatomysql.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @Author Z-7
 * @Date 2022/8/18
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public Docket api(Environment environment) {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .enable(true)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.abc.hanatomysql.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        //设置作者信息
        Contact contact=new Contact("darkak","","darkak@163.com");
        return new ApiInfoBuilder()
                .title("hanaToMysql")//标题
                .description("hana数据同步mysql数据")//描述
                .version("1.0")
                .contact(contact)
                .build();
    }
}