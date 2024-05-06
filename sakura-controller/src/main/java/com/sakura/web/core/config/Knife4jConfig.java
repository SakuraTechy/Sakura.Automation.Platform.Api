package com.sakura.web.core.config;

import io.swagger.annotations.ApiOperation;
import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
     * @Author 刘智
     * <p> knife4j 配置 </p>
     */
@EnableSwagger2
@EnableKnife4j
@Configuration
@Import(BeanValidatorPluginsConfiguration.class)
public class Knife4jConfig {

    /** 设置请求的统一前缀 */
    // @Value("${knife4j.pathMapping}")
    private String pathMapping;

    /**
     * API分组
     */
    @Bean(value = "apiV1")
    public Docket apiV1() {
        String groupName="Knife4-v1.0";
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .host("https://www.baidu.com")
                .apiInfo(apiInfo())
                .groupName(groupName)
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build()
                .pathMapping(pathMapping);
        return docket;
    }

    // @Bean(value = "apiV2")
    // public Docket apiV2() {
    //     Docket docket = new Docket(DocumentationType.SWAGGER_2)
    //             .host("https://www.baidu.com")
    //             .apiInfo(apiInfo())
    //             .groupName("Knife4-v2.0")
    //             .select()
    //             .apis(RequestHandlerSelectors.basePackage("com.sakura.web.controller.v2"))
    //             .paths(PathSelectors.any())
    //             .build()
    //             .pathMapping(pathMapping);
    //     return docket;
    // }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            .title("自动化测试平台-接口文档")
            .description("自动化测试平台-接口文档")
            .termsOfServiceUrl("http://localhost:8080/doc.html")
            .contact(new Contact("刘智", null, null))
            .version("1.0")
            .build();
    }
}