package com.sakura.web.core.config;

import java.util.ArrayList;
import java.util.List;

import com.sakura.common.config.SakuraConfig;
import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;

import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;

/**
 * Swagger3的接口配置
 *
 * @author liuzhi
 */
@EnableSwagger2
@EnableKnife4j
@Configuration
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfig {
    
    private static final Logger log = LoggerFactory.getLogger(SwaggerConfig.class);

    /** 系统基础配置 */
    @Autowired(required = false)
    private SakuraConfig sakuraConfig;

    /** 是否开启swagger */
    @Value("${swagger.enabled}")
    private boolean enabled;

    /** 设置请求的统一前缀 */
    @Value("${swagger.pathMapping}")
    private String pathMapping;

    /**
     * @Author 刘智
     * <p> swagger 配置 </p>
     */
    @Bean
    public Docket createRestApi(Environment environment) {
        //设置要显示的swagger环境
        Profiles profiles = Profiles.of("dev", "test","druid");
        //通过environment.acceptsProfiles判断是否处于自己设定的环境中
        boolean flag = environment.acceptsProfiles(profiles);
        log.info("11111111111111111111:"+Boolean.toString(flag));
        List<Parameter> parameters = new ArrayList<>();
        parameters.add(new ParameterBuilder()
                .name("token")
                .description("认证token")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .required(false)
                .build());
        // 构建文档
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                // 是否启用Swagger
                .enable(enabled)
                // 用来创建该API的基本信息，展示在文档的页面中（自定义展示的信息）
                .apiInfo(apiInfo())
                // 添加统一请求头
                .globalOperationParameters(parameters)
                // 分组名称
                .groupName("Swagger-v1.0")
                // 设置查询哪些接口暴露给Swagger展示
                .select()
                // 扫描指定包中的swagger注解
                // .apis(RequestHandlerSelectors.basePackage("com.ruoyi.project.tool.swagger"))
                // 扫描所有有注解的api，用这种方式更灵活
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                /*
                 * https://blog.csdn.net/qq_41604890/article/details/108875865
                 * 配置如何通过path过滤,即这里只扫描请求以/kuang开头的接口
                 *.paths(PathSelectors.ant("/kuang/**"))
                 * 接口请求地址前缀rest 比如 @RequestMapping(value = "/rest/article/{id}") 可以写 /.* 
                 *.paths(PathSelectors.regex("/rest/.*"))
                 */
                // 扫描所有
                .paths(PathSelectors.any())
                .build()
                /* 设置安全模式，swagger可以设置访问token */
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts())
                .pathMapping(pathMapping);
        return docket;

    }

    /**
     * @Author 刘智
     * <p> 文档信息</p>
     * @Param []
     * @Return springfox.documentation.service.ApiInfo
     */
    private ApiInfo apiInfo() {
        // 用ApiInfoBuilder进行定制，文档对象构建器
        return new ApiInfoBuilder()
                // 设置标题
                .title("自动化测试平台-接口文档")
                // 描述
                .description("自动化测试平台-接口文档")
                // 版本
                .version("版本号:" + sakuraConfig.getVersion())
                // 作者信息
                .contact(new Contact(sakuraConfig.getName(), null, null))
                // 测试服务器
                .termsOfServiceUrl("http://localhost:8080/api/swagger-ui/index.html")
                // 声明许可
                .license("声明许可")
                // 许可路径
                .licenseUrl("http://localhost:8080/api/swagger-ui/index.html")
                .build();
    }

    /**
     * 安全模式，这里指定token通过Authorization头请求头传递
     */
    private List<SecurityScheme> securitySchemes() {
        List<SecurityScheme> apiKeyList = new ArrayList<SecurityScheme>();
        apiKeyList.add(new ApiKey("Authorization", "Authorization", In.HEADER.toValue()));
        return apiKeyList;
    }

    /**
     * 安全上下文
     */
    private List<SecurityContext> securityContexts() {
        List<SecurityContext> securityContexts = new ArrayList<>();
        securityContexts.add(SecurityContext.builder().securityReferences(defaultAuth())
                .operationSelector(o -> o.requestMappingPattern().matches("/.*")).build());
        return securityContexts;
    }

    /**
     * 默认的安全上引用
     */
    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        List<SecurityReference> securityReferences = new ArrayList<>();
        securityReferences.add(new SecurityReference("Authorization", authorizationScopes));
        return securityReferences;
    }
}
