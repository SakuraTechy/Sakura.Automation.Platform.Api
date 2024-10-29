/*
 * Copyright (c) 2022-present Charles7c Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sakura;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.alicp.jetcache.anno.config.EnableMethodCache;
import com.github.xiaoymin.knife4j.spring.configuration.Knife4jProperties;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.x.file.storage.spring.EnableFileStorage;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sakura.starter.core.autoconfigure.project.ProjectProperties;
import com.sakura.starter.extension.crud.annotation.EnableCrudRestController;
import com.sakura.starter.web.annotation.EnableGlobalResponse;
import com.sakura.starter.web.model.R;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 启动程序
 *
 * @author hagyao520
 * @since 2022/12/8 23:15
 */
@Slf4j
@EnableFileStorage
@EnableMethodCache(basePackages = "com.sakura")
@EnableGlobalResponse
@EnableCrudRestController
@RestController
@SpringBootApplication
@RequiredArgsConstructor
@MapperScan("com.sakura.system.mapper")
public class SakuraApplication implements ApplicationRunner {

    private final ProjectProperties projectProperties;
    private final ServerProperties serverProperties;

    public static void main(String[] args) {
        SpringApplication.run(SakuraApplication.class, args);
    }

    @Hidden
    @SaIgnore
    @GetMapping("/")
    public R index() {
        return R.ok("%s service started successfully.".formatted(projectProperties.getName()), null);
    }

    @Override
    public void run(ApplicationArguments args) throws UnknownHostException {
        String hostAddress = NetUtil.getLocalhost().getHostAddress();
        hostAddress = InetAddress.getLocalHost().getHostAddress();
        Integer port = serverProperties.getPort();
        String contextPath = serverProperties.getServlet().getContextPath();
        String baseUrl = URLUtil.normalize("%s:%s%s".formatted(hostAddress, port, contextPath));
        log.info("----------------------------------------------");
        log.info("{} service started successfully.", projectProperties.getName());
        log.info("API 地址：{}", baseUrl);
        Knife4jProperties knife4jProperties = SpringUtil.getBean(Knife4jProperties.class);
        if (!knife4jProperties.isProduction()) {
            log.info("API 文档：{}/doc.html", baseUrl);
        }
        log.info("----------------------------------------------");
    }
}
