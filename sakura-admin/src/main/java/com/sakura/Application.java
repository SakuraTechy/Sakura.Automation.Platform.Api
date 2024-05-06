/*
 * @Author: 刘智
 * @Date: 2021-12-03 21:22:21
 * @version: v1.0
 * @LastEditors: 刘智
 * @LastEditTime: 2021-12-14 10:20:34
 * @FilePath: \Sakura.Test.Platform.Api\sakura-admin\src\main\java\com\sakura\SakuraApplication.java
 * @Description: 该文件的信息，比如做了什么，文件的传参，出参等
 */
package com.sakura;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.env.Environment;

import springfox.documentation.spring.web.SpringfoxWebMvcConfiguration;

/**
 * 启动程序
 *
 * @author liuzhi
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
@ConditionalOnClass(SpringfoxWebMvcConfiguration.class)
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) throws UnknownHostException {
        System.setProperty("spring.devtools.restart.enabled", "false");
        ConfigurableApplicationContext application=SpringApplication.run(Application.class, args);

        StringBuffer sakura = new StringBuffer("(♥◠‿◠)ﾉﾞ  Sakura启动成功   ლ(´ڡ`ლ)ﾞ  \n");
        sakura.append("███████╗ █████╗ ██╗  ██╗██╗   ██╗██████╗  █████╗ \n");
        sakura.append("██╔════╝██╔══██╗██║ ██╔╝██║   ██║██╔══██╗██╔══██╗\n");
        sakura.append("███████╗███████║█████╔╝ ██║   ██║██████╔╝███████║\n");
        sakura.append("╚════██║██╔══██║██╔═██╗ ██║   ██║██╔══██╗██╔══██║\n");
        sakura.append("███████║██║  ██║██║  ██╗╚██████╔╝██║  ██║██║  ██║\n");
        sakura.append("╚══════╝╚═╝  ╚═╝╚═╝  ╚═╝ ╚═════╝ ╚═╝  ╚═╝╚═╝  ╚═╝\n");
        System.out.println(sakura);

        Environment env = application.getEnvironment();
        System.out.println("select count(1) from sys_user where user_name = 'admin11' limit 1");
        logger.info("select count(1) from sys_user where user_name = 'admin11' limit 1");
		logger.info("\n----------------------------------------------------------\n\t" +
						"Application '{}' is running! Access URLs:\n\t" +
						"Local: \t\thttp://localhost:{}\n\t" +
						"External: \thttp://{}:{}\n\t"+
                        "Swagger3: \thttp://{}:{}/swagger-ui/index.html\n\t"+
                        "Knife4j: \thttp://{}:{}/doc.html\n"+
						"----------------------------------------------------------",
				env.getProperty("spring.application.name"),
				env.getProperty("server.port"),
				InetAddress.getLocalHost().getHostAddress(),
				env.getProperty("server.port"),
                InetAddress.getLocalHost().getHostAddress(),
				env.getProperty("server.port"),
				InetAddress.getLocalHost().getHostAddress(),
				env.getProperty("server.port"));
    }
}
