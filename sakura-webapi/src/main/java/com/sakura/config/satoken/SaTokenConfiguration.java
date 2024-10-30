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

package com.sakura.config.satoken;

import cn.dev33.satoken.SaManager;
import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.context.model.SaRequest;
import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.sign.SaSignUtil;
import cn.dev33.satoken.stp.StpInterface;
import cn.dev33.satoken.stp.StpUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.sakura.common.context.UserContext;
import com.sakura.common.context.UserContextHolder;
import com.sakura.open.sign.OpenSignTemplate;
import com.sakura.starter.auth.satoken.autoconfigure.SaTokenExtensionProperties;
import com.sakura.starter.core.constant.StringConstants;
import com.sakura.starter.core.util.validate.CheckUtils;

import java.util.List;

/**
 * Sa-Token 配置
 *
 * @author hagyao520
 * @since 2022/12/19 22:13
 */
@Configuration
@RequiredArgsConstructor
public class SaTokenConfiguration {

    private final SaTokenExtensionProperties properties;
    private final LoginPasswordProperties loginPasswordProperties;
    private final OpenSignTemplate openSignTemplate;

    /**
     * Sa-Token 权限认证配置
     */
    @Bean
    public StpInterface stpInterface() {
        return new SaTokenPermissionImpl();
    }

    /**
     * SaToken 拦截器配置
     */
    @Bean
    public SaInterceptor saInterceptor() {
        SaManager.setSaSignTemplate(openSignTemplate);
        return new SaExtensionInterceptor(handle -> SaRouter.match(StringConstants.PATH_PATTERN)
            .notMatch(properties.getSecurity().getExcludes())
            .check(r -> {
                // 拦截验证sign
                // 判断是否包含sign参数
                SaRequest saRequest = SaHolder.getRequest();
                List<String> paramNames = saRequest.getParamNames();
                boolean matchParamSign = paramNames.stream().anyMatch(paramName -> paramName.equals("sign"));
                // 如果包含sign参数走SaToken API接口参数签名验证
                if (matchParamSign) {
                    try {
                        SaSignUtil.checkRequest(saRequest);
                    } catch (Exception e) {
                        CheckUtils.throwIf(true, e.getMessage());
                    }
                } else {
                    // 如果不包含sign参数走登录token验证
                    StpUtil.checkLogin();
                    if (SaRouter.isMatchCurrURI(loginPasswordProperties.getExcludes())) {
                        return;
                    }
                    UserContext userContext = UserContextHolder.getContext();
                    CheckUtils.throwIf(userContext.isPasswordExpired(), "密码已过期，请修改密码");
                }
            }));
    }
}
