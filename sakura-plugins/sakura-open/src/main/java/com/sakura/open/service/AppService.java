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

package com.sakura.open.service;

import com.sakura.open.model.query.AppQuery;
import com.sakura.open.model.req.AppReq;
import com.sakura.open.model.resp.AppDetailResp;
import com.sakura.open.model.resp.AppResp;
import com.sakura.open.model.resp.AppSecretGetResp;
import com.sakura.starter.extension.crud.service.BaseService;

/**
 * 应用业务接口
 *
 * @author chengzi
 * @since 2024/10/17 16:03
 */
public interface AppService extends BaseService<AppResp, AppDetailResp, AppQuery, AppReq> {
    /**
     * 根据ID查询应用密码
     *
     * @param id ID
     * @return 应用密码
     */
    AppSecretGetResp getAppSecretById(Long id);

    /**
     * 根据ID重置应用密码查看状态
     *
     * @param id ID
     */
    void resetAppSecretStatusById(Long id, String status);

    /**
     * 根据应用密钥重置应用密码查看状态
     *
     * @param appKey 应用密钥
     */
    void resetAppSecretStatusByAppkey(String appKey, String status);

    /**
     * 根据ID刷新应用密码
     *
     * @param id ID
     */
    void refreshAppSecretByID(Long id);

    /**
     * 根据应用密钥获取应用密码
     *
     * @param appKey 应用密钥
     * @return 应用密码
     */
    String getAppSecretByAppKey(String appKey);

    /**
     * 判断应用密钥是否存在
     *
     * @param appKey 应用密钥
     * @return 是否存在（true：存在；false：不存在）
     */
    boolean isExistAppKey(String appKey);

    /**
     * 判断应用密钥是否过期
     *
     * @param appKey 应用密钥
     * @return 是否过期（true：已过期；false：未过期）
     */
    boolean isExpireAppKey(String appKey);

}