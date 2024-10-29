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

package com.sakura.common.config.mybatis;

import cn.hutool.core.convert.Convert;
import com.sakura.common.context.UserContextHolder;
import com.sakura.starter.extension.datapermission.enums.DataScope;
import com.sakura.starter.extension.datapermission.filter.DataPermissionUserContextProvider;
import com.sakura.starter.extension.datapermission.model.RoleContext;
import com.sakura.starter.extension.datapermission.model.UserContext;

import java.util.stream.Collectors;

/**
 * 数据权限用户上下文提供者
 *
 * @author hagyao520
 * @since 2023/12/21 21:19
 */
public class DefaultDataPermissionUserContextProvider implements DataPermissionUserContextProvider {

    @Override
    public boolean isFilter() {
        return !UserContextHolder.isAdmin();
    }

    @Override
    public UserContext getUserContext() {
        com.sakura.common.context.UserContext context = UserContextHolder.getContext();
        UserContext userContext = new UserContext();
        userContext.setUserId(Convert.toStr(context.getId()));
        userContext.setDeptId(Convert.toStr(context.getDeptId()));
        userContext.setRoles(context.getRoles()
            .stream()
            .map(r -> new RoleContext(Convert.toStr(r.getId()), DataScope.valueOf(r.getDataScope().name())))
            .collect(Collectors.toSet()));
        return userContext;
    }
}
