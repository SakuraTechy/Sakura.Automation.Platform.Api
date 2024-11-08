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

package com.sakura.controller.auth;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import com.sakura.common.config.properties.CaptchaProperties;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jodd.util.StringUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.sakura.auth.model.req.AccountLoginReq;
import com.sakura.auth.model.req.EmailLoginReq;
import com.sakura.auth.model.req.PhoneLoginReq;
import com.sakura.auth.model.resp.LoginResp;
import com.sakura.auth.model.resp.RouteResp;
import com.sakura.auth.model.resp.UserInfoResp;
import com.sakura.auth.service.LoginService;
import com.sakura.common.constant.CacheConstants;
import com.sakura.common.context.UserContext;
import com.sakura.common.context.UserContextHolder;
import com.sakura.common.util.SecureUtils;
import com.sakura.system.model.resp.UserDetailResp;
import com.sakura.system.service.UserService;
import com.sakura.starter.cache.redisson.util.RedisUtils;
import com.sakura.starter.core.util.ExceptionUtils;
import com.sakura.starter.core.util.validate.ValidationUtils;
import com.sakura.starter.log.core.annotation.Log;

import java.util.List;

/**
 * 认证 API
 *
 * @author hagyao520
 * @since 2022/12/21 20:37
 */
@Log(module = "登录")
@Tag(name = "认证 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private static final String CAPTCHA_EXPIRED = "验证码已失效";
    private static final String CAPTCHA_ERROR = "验证码错误";
    private final LoginService loginService;
    private final UserService userService;
    private final CaptchaProperties captchaProperties;

    @SaIgnore
    @Operation(summary = "账号注册", description = "根据账号和密码进行注册")
    @PostMapping("/signup")
    public LoginResp signup(@Validated @RequestBody AccountLoginReq loginReq, HttpServletRequest request) {
        String captchaKey = CacheConstants.CAPTCHA_KEY_PREFIX + loginReq.getUuid();
        String captcha = RedisUtils.get(captchaKey);
        ValidationUtils.throwIfBlank(captcha, CAPTCHA_EXPIRED);
        RedisUtils.delete(captchaKey);
        ValidationUtils.throwIfNotEqualIgnoreCase(loginReq.getCaptcha(), captcha, CAPTCHA_ERROR);
        // 用户登录
        String rawPassword = ExceptionUtils.exToNull(() -> SecureUtils.decryptByRsaPrivateKey(loginReq.getPassword()));
        ValidationUtils.throwIfBlank(rawPassword, "密码解密失败");
        String token = loginService.accountLogin(loginReq.getUsername(), rawPassword, request);
        return LoginResp.builder().token(token).build();
    }

    @SaIgnore
    @Operation(summary = "账号登录", description = "根据账号和密码进行登录认证")
    @PostMapping("/account")
    public LoginResp accountLogin(@Validated @RequestBody AccountLoginReq loginReq, HttpServletRequest request) {
        String captchaKey = CacheConstants.CAPTCHA_KEY_PREFIX + loginReq.getUuid();
        String captcha = RedisUtils.get(captchaKey);
        ValidationUtils.throwIfBlank(captcha, CAPTCHA_EXPIRED);
        RedisUtils.delete(captchaKey);
        ValidationUtils.throwIfNotEqualIgnoreCase(loginReq.getCaptcha(), captcha, CAPTCHA_ERROR);
        // 用户登录
        String rawPassword = ExceptionUtils.exToNull(() -> SecureUtils.decryptByRsaPrivateKey(loginReq.getPassword()));
        ValidationUtils.throwIfBlank(rawPassword, "密码解密失败");
        String token = loginService.accountLogin(loginReq.getUsername(), rawPassword, request);
        return LoginResp.builder().token(token).build();
    }

    @SaIgnore
    @Operation(summary = "手机号登录", description = "根据手机号和验证码进行登录认证")
    @PostMapping("/phone")
    public LoginResp phoneLogin(@Validated @RequestBody PhoneLoginReq loginReq) {
        String phone = loginReq.getPhone();
        String captcha = loginReq.getCaptcha();
        if(!StringUtil.equals(captcha,captchaProperties.getSms().getCode())){
            String captchaKey = CacheConstants.CAPTCHA_KEY_PREFIX + phone;
            String captcha1 = RedisUtils.get(captchaKey);
            ValidationUtils.throwIfBlank(captcha1, CAPTCHA_EXPIRED);
            ValidationUtils.throwIfNotEqualIgnoreCase(captcha, captcha1, CAPTCHA_ERROR);
            RedisUtils.delete(captchaKey);
        }
        String token = loginService.phoneLogin(phone);
        return LoginResp.builder().token(token).build();
    }

    @SaIgnore
    @Operation(summary = "邮箱登录", description = "根据邮箱和验证码进行登录认证")
    @PostMapping("/email")
    public LoginResp emailLogin(@Validated @RequestBody EmailLoginReq loginReq) {
        String email = loginReq.getEmail();
        String captcha = loginReq.getCaptcha();
        if(!StringUtil.equals(captcha,captchaProperties.getSms().getCode())) {
            String captchaKey = CacheConstants.CAPTCHA_KEY_PREFIX + email;
            String captcha1 = RedisUtils.get(captchaKey);
            ValidationUtils.throwIfBlank(captcha, CAPTCHA_EXPIRED);
            ValidationUtils.throwIfNotEqualIgnoreCase(captcha1, captcha, CAPTCHA_ERROR);
            RedisUtils.delete(captchaKey);
        }
        String token = loginService.emailLogin(email);
        return LoginResp.builder().token(token).build();
    }

    @Operation(summary = "用户退出", description = "注销用户的当前登录")
    @Parameter(name = "Authorization", description = "令牌", required = true, example = "Bearer xxxx-xxxx-xxxx-xxxx", in = ParameterIn.HEADER)
    @PostMapping("/logout")
    public Object logout() {
        Object loginId = StpUtil.getLoginId(-1L);
        StpUtil.logout();
        return loginId;
    }

    @Log(ignore = true)
    @Operation(summary = "获取用户信息", description = "获取登录用户信息")
    @GetMapping("/user/info")
    public UserInfoResp getUserInfo() {
        UserContext userContext = UserContextHolder.getContext();
        UserDetailResp userDetailResp = userService.get(userContext.getId());
        UserInfoResp userInfoResp = BeanUtil.copyProperties(userDetailResp, UserInfoResp.class);
        userInfoResp.setPermissions(userContext.getPermissions());
        userInfoResp.setRoles(userContext.getRoleCodes());
        userInfoResp.setPwdExpired(userContext.isPasswordExpired());
        return userInfoResp;
    }

    @Log(ignore = true)
    @Operation(summary = "获取路由信息", description = "获取登录用户的路由信息")
    @GetMapping("/route")
    public List<RouteResp> listRoute() {
        return loginService.buildRouteTree(UserContextHolder.getUserId());
    }
}