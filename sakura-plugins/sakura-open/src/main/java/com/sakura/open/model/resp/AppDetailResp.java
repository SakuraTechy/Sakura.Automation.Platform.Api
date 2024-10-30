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

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import com.sakura.starter.extension.crud.model.resp.BaseDetailResp;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * 应用详情信息
 *
 * @author chengzi
 * @since 2024/10/17 16:03
 */
@Data
@ExcelIgnoreUnannotated
@Schema(description = "应用详情信息")
public class AppDetailResp extends BaseDetailResp {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 应用名称
     */
    @Schema(description = "应用名称")
    @ExcelProperty(value = "应用名称")
    private String name;

    /**
     * 应用密钥
     */
    @Schema(description = "应用密钥")
    @ExcelProperty(value = "应用密钥")
    private String appKey;

    /**
     * 应用状态
     */
    @Schema(description = "应用状态")
    @ExcelProperty(value = "应用状态")
    private String status;

    /**
     * 失效时间
     */
    @Schema(description = "失效时间")
    @ExcelProperty(value = "失效时间")
    private LocalDateTime expirationTime;

    /**
     * 应用描述
     */
    @Schema(description = "应用描述")
    @ExcelProperty(value = "应用描述")
    private String appDesc;
}