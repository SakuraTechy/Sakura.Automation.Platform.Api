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

package com.sakura.open.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.IdUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.sakura.open.mapper.AppMapper;
import com.sakura.open.model.entity.AppDO;
import com.sakura.open.model.query.AppQuery;
import com.sakura.open.model.req.AppReq;
import com.sakura.open.model.resp.AppDetailResp;
import com.sakura.open.model.resp.AppResp;
import com.sakura.open.model.resp.AppSecretGetResp;
import com.sakura.open.service.AppService;
import com.sakura.starter.extension.crud.service.impl.BaseServiceImpl;

import java.time.LocalDateTime;

/**
 * 应用业务实现
 *
 * @author chengzi
 * @since 2024/10/17 16:03
 */
@Service
@RequiredArgsConstructor
public class AppServiceImpl extends BaseServiceImpl<AppMapper, AppDO, AppResp, AppDetailResp, AppQuery, AppReq> implements AppService {

    // 已激活
    private final static String APP_ENABLED_KEY = "1";
    // 未激活
    private final static String APP_DISABLED_KEY = "0";

    @Override
    public AppSecretGetResp getAppSecretById(Long id) {
        AppDO app = baseMapper.lambdaQuery().eq(AppDO::getId, id).one();
        String appSecret = "********";
        if (app.getSecretStatus().equals(APP_DISABLED_KEY)) {
            appSecret = app.getAppSecret();
            this.resetAppSecretStatusById(id, APP_ENABLED_KEY);
        }
        AppSecretGetResp appSecretGetResp = new AppSecretGetResp();
        appSecretGetResp.setAppKey(app.getAppKey());
        appSecretGetResp.setAppSecret(appSecret);
        return appSecretGetResp;
    }

    @Override
    public void resetAppSecretStatusById(Long id, String status) {
        baseMapper.lambdaUpdate().set(AppDO::getSecretStatus, status).eq(AppDO::getId, id).update();
    }

    @Override
    public void resetAppSecretStatusByAppkey(String appKey, String status) {
        baseMapper.lambdaUpdate().set(AppDO::getSecretStatus, status).eq(AppDO::getAppKey, appKey).update();
    }

    @Override
    public void refreshAppSecretByID(Long id) {
        baseMapper.lambdaUpdate().set(AppDO::getAppSecret, IdUtil.simpleUUID()).eq(AppDO::getId, id).update();
        this.resetAppSecretStatusById(id, APP_DISABLED_KEY);
    }

    @Override
    public String getAppSecretByAppKey(String appKey) {
        return baseMapper.lambdaQuery().select(AppDO::getAppSecret).eq(AppDO::getAppKey, appKey).one().getAppSecret();
    }

    @Override
    public boolean isExistAppKey(String appKey) {
        return baseMapper.lambdaQuery().eq(AppDO::getAppKey, appKey).exists();
    }

    @Override
    public boolean isExpireAppKey(String appKey) {
        LocalDateTime expirationTime = baseMapper.lambdaQuery()
            .select(AppDO::getExpirationTime)
            .eq(AppDO::getAppKey, appKey)
            .one()
            .getExpirationTime();
        return expirationTime.isBefore(LocalDateTimeUtil.of(DateUtil.date()));
    }
}