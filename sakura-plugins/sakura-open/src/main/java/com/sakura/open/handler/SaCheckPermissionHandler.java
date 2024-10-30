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

package com.sakura.open.handler;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.handler.SaAnnotationHandlerInterface;
import org.springframework.stereotype.Component;
import com.sakura.open.util.ApiSignCheckUtils;

import java.lang.reflect.Method;

import static cn.dev33.satoken.annotation.handler.SaCheckPermissionHandler._checkMethod;

/**
 * 重定义注解 SaCheckPermission 的处理器
 *
 * @author chengzi
 * @since 2024/10/25 12:03
 */
@Component
public class SaCheckPermissionHandler implements SaAnnotationHandlerInterface<SaCheckPermission> {

    @Override
    public Class<SaCheckPermission> getHandlerAnnotationClass() {
        return SaCheckPermission.class;
    }

    @Override
    public void checkMethod(SaCheckPermission at, Method method) {
        if (!ApiSignCheckUtils.isExistSignParam()) {
            _checkMethod(at.type(), at.value(), at.mode(), at.orRole());
        }
    }

}
