// package com.sakura.web.core.config;

// import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.context.annotation.Import;

// import io.swagger.annotations.ApiOperation;
// import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
// import springfox.documentation.builders.ApiInfoBuilder;
// import springfox.documentation.builders.PathSelectors;
// import springfox.documentation.builders.RequestHandlerSelectors;
// import springfox.documentation.service.ApiInfo;
// import springfox.documentation.spi.DocumentationType;
// import springfox.documentation.spring.web.plugins.Docket;
// import springfox.documentation.swagger2.annotations.EnableSwagger2;
 
// @EnableSwagger2
// @EnableKnife4j
// @Configuration
// @Import(BeanValidatorPluginsConfiguration.class)
// public class SwaggerConfiguration {
 
 
//     @Bean
//     public Docket defaultApi2() {
//         Docket docket=new Docket(DocumentationType.SWAGGER_2)
//                 .apiInfo(apiInfo())
//                 .groupName("2.X版本")
//                 .select()
//                 .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
//                 .paths(PathSelectors.any())
//                 .build();
//         return docket;
//     }
//     private ApiInfo apiInfo() {
//         return new ApiInfoBuilder()
//                 .title("接口说明")
//                 .description("DEMO服务接口说明")
//                 .termsOfServiceUrl("http://localhost:8080/")
//                 .version("1.0")
//                 .build();
//     }
// }