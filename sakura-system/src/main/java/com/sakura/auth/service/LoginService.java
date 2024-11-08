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

package com.sakura.auth.service;

import com.sakura.auth.model.resp.RouteResp;
import jakarta.servlet.http.HttpServletRequest;
import me.zhyd.oauth.model.AuthUser;

import java.util.List;

/**
 * 登录业务接口
 *
 * @author hagyao520
 * @since 2022/12/21 21:48
 */
public interface LoginService {

//    /**
//     * 用户注册
//     *
//     * @param username 用户名
//     * @param password 密码
//     * @param request  请求对象
//     * @return 令牌
//     */
//    String signup(String username, String password, HttpServletRequest request);

    /**
     * 账号登录
     *
     * @param username 用户名
     * @param password 密码
     * @param request  请求对象
     * @return 令牌
     */
    String accountLogin(String username, String password, HttpServletRequest request);

    /**
     * 手机号登录
     *
     * @param phone 手机号
     * @return 令牌
     */
    String phoneLogin(String phone);

    /**
     * 邮箱登录
     *
     * @param email 邮箱
     * @return 令牌
     */
    String emailLogin(String email);

    /**
     * 三方账号登录
     *
     * @param authUser 三方账号信息
     * @return 令牌
     */
    String socialLogin(AuthUser authUser);

    /**
     * 构建路由树
     *
     * @param userId 用户 ID
     * @return 路由树
     */
    List<RouteResp> buildRouteTree(Long userId);
}
