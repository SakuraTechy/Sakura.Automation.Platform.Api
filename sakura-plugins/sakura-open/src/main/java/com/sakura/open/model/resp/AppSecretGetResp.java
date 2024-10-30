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

package com.sakura.open.model.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;

/**
 * 应用密钥/密码信息
 *
 * @author chengzi
 * @since 2024/10/17 16:03
 */
@Data
@Schema(description = "应用密钥/密码信息")
public class AppSecretGetResp {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 应用密钥
     */
    @Schema(description = "应用密钥")
    private String appKey;

    /**
     * 应用密码
     */
    @Schema(description = "应用密码")
    private String appSecret;
}
