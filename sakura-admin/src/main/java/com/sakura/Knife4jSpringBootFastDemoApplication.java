package com.sakura;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

/**
 * @author liuzhi
 */
@SpringBootApplication
public class Knife4jSpringBootFastDemoApplication {
	private static final Logger logger= LoggerFactory.getLogger(Knife4jSpringBootFastDemoApplication.class);

    public static void main1(String[] args) throws UnknownHostException {
        ConfigurableApplicationContext application = SpringApplication.run(Knife4jSpringBootFastDemoApplication.class, args);
        Environment env = application.getEnvironment();
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
				env.getProperty("server.port")
		);
    }
}
