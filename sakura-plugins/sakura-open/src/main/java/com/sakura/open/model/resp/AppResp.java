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
import com.sakura.starter.extension.crud.model.resp.BaseResp;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * 应用信息
 *
 * @author chengzi
 * @since 2024/10/17 16:03
 */
@Data
@Schema(description = "应用信息")
public class AppResp extends BaseResp {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 应用名称
     */
    @Schema(description = "应用名称")
    private String name;

    /**
     * APPKEY
     */
    @Schema(description = "应用密钥")
    private String appKey;

    /**
     * 应用状态
     */
    @Schema(description = "应用状态")
    private String status;

    /**
     * 失效时间
     */
    @Schema(description = "失效时间")
    private LocalDateTime expirationTime;

    /**
     * 应用描述
     */
    @Schema(description = "应用描述")
    private String appDesc;

}