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

package com.sakura.controller.system;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.sakura.system.service.UserRoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import com.sakura.system.model.query.RoleQuery;
import com.sakura.system.model.req.RoleReq;
import com.sakura.system.model.resp.RoleDetailResp;
import com.sakura.system.model.resp.RoleResp;
import com.sakura.system.service.RoleService;
import com.sakura.starter.extension.crud.annotation.CrudRequestMapping;
import com.sakura.starter.extension.crud.controller.BaseController;
import com.sakura.starter.extension.crud.enums.Api;

import java.util.List;

/**
 * 角色管理 API
 *
 * @author hagyao520
 * @since 2023/2/8 23:11
 */
@Tag(name = "角色管理 API")
@RestController
@RequiredArgsConstructor
@CrudRequestMapping(value = "/system/role", api = {Api.PAGE, Api.GET, Api.ADD, Api.UPDATE, Api.DELETE})
public class RoleController extends BaseController<RoleService, RoleResp, RoleDetailResp, RoleQuery, RoleReq> {

    private final UserRoleService userRoleService;

    @Operation(summary = "查询角色关联用户", description = "查询角色组绑定的关联用户")
    @GetMapping("/listRoleUsers/{id}")
    public List<Long> listUsers(@PathVariable("id") Long roleId) {
        return userRoleService.listUserIdByRoleId(roleId);
    }

    @Operation(summary = "关联用户", description = "批量关联用户")
    @SaCheckPermission("system:role:bindUsers")
    @PostMapping("/bindUsers/{id}")
    public void bindUsers(@PathVariable("id") Long roleId, @RequestBody List<Long> userIds) {
        userRoleService.bindUserIds(roleId, userIds);
    }
}
