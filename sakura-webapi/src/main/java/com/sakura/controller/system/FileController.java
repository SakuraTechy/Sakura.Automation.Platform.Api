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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.sakura.system.model.query.FileQuery;
import com.sakura.system.model.req.FileReq;
import com.sakura.system.model.resp.FileResp;
import com.sakura.system.model.resp.FileStatisticsResp;
import com.sakura.system.service.FileService;
import com.sakura.starter.extension.crud.annotation.CrudRequestMapping;
import com.sakura.starter.extension.crud.controller.BaseController;
import com.sakura.starter.extension.crud.enums.Api;
import com.sakura.starter.log.core.annotation.Log;

/**
 * 文件管理 API
 *
 * @author hagyao520
 * @since 2023/12/23 10:38
 */
@Tag(name = "文件管理 API")
@RestController
@RequiredArgsConstructor
@CrudRequestMapping(value = "/system/file", api = {Api.PAGE, Api.UPDATE, Api.DELETE})
public class FileController extends BaseController<FileService, FileResp, FileResp, FileQuery, FileReq> {

    @Log(ignore = true)
    @Operation(summary = "查询文件资源统计", description = "查询文件资源统计")
    @SaCheckPermission("system:file:list")
    @GetMapping("/statistics")
    public FileStatisticsResp statistics() {
        return baseService.statistics();
    }
}