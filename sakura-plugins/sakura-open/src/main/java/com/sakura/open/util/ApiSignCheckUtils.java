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

package com.sakura.open.util;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.context.model.SaRequest;

import java.util.List;

/**
 * API签名验证工具类
 *
 * @author chengzi
 * @since 2024/10/25 15:31
 */
public class ApiSignCheckUtils {

    private ApiSignCheckUtils() {
    }

    /**
     * 判断请求是否包含sign参数
     *
     * @return 是否包含sign参数（true：包含；false：不包含）
     */
    public static boolean isExistSignParam() {
        SaRequest saRequest = SaHolder.getRequest();
        List<String> paramNames = saRequest.getParamNames();
        return paramNames.stream().anyMatch(paramName -> paramName.equals("sign"));
    }

}
