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

package com.sakura.controller.open;

import cn.dev33.satoken.annotation.SaCheckPermission;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import com.sakura.open.model.query.AppQuery;
import com.sakura.open.model.req.AppReq;
import com.sakura.open.model.resp.AppDetailResp;
import com.sakura.open.model.resp.AppResp;
import com.sakura.open.model.resp.AppSecretGetResp;
import com.sakura.open.service.AppService;
import com.sakura.starter.extension.crud.annotation.CrudRequestMapping;
import com.sakura.starter.extension.crud.controller.BaseController;
import com.sakura.starter.extension.crud.enums.Api;
import com.sakura.starter.extension.crud.model.resp.BaseIdResp;

/**
 * 应用管理 API
 *
 * @author chengzi
 * @since 2024/10/17 16:03
 */
@Tag(name = "应用管理 API")
@RestController
@RequiredArgsConstructor
@CrudRequestMapping(value = "/open/app", api = {Api.PAGE, Api.GET, Api.ADD, Api.UPDATE, Api.DELETE, Api.EXPORT})
public class AppController extends BaseController<AppService, AppResp, AppDetailResp, AppQuery, AppReq> {

    private final AppService appService;
    private final static String APP_DISABLED_KEY = "0";

    @Operation(summary = "刷新应用密码", description = "刷新应用密码")
    @Parameter(name = "id", description = "ID", example = "test", in = ParameterIn.PATH)
    @SaCheckPermission("open:app:refreshas")
    @GetMapping(value = "/{id}/refreshas")
    public void refreshAppSecret(@PathVariable Long id) {
        appService.refreshAppSecretByID(id);
    }

    @Operation(summary = "获取应用密码", description = "获取应用密码")
    @Parameter(name = "appKey", description = "应用密钥", example = "test", in = ParameterIn.PATH)
    @SaCheckPermission("open:app:getas")
    @GetMapping("/{id}/appsecret")
    public AppSecretGetResp getAppSecret(@PathVariable Long id) {
        return appService.getAppSecretById(id);
    }

    @Override
    public BaseIdResp<Long> add(AppReq req) {
        BaseIdResp<Long> baseIdResp = super.add(req);
        Long appId = baseIdResp.getId();
        appService.refreshAppSecretByID(appId);
        appService.resetAppSecretStatusById(appId, APP_DISABLED_KEY);
        return baseIdResp;
    }
}