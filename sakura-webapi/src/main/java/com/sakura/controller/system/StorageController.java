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

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RestController;
import com.sakura.system.model.query.StorageQuery;
import com.sakura.system.model.req.StorageReq;
import com.sakura.system.model.resp.StorageResp;
import com.sakura.system.service.StorageService;
import com.sakura.starter.extension.crud.annotation.CrudRequestMapping;
import com.sakura.starter.extension.crud.controller.BaseController;
import com.sakura.starter.extension.crud.enums.Api;

/**
 * 存储管理 API
 *
 * @author hagyao520
 * @since 2023/12/26 22:09
 */
@Tag(name = "存储管理 API")
@RestController
@CrudRequestMapping(value = "/system/storage", api = {Api.PAGE, Api.GET, Api.ADD, Api.UPDATE, Api.DELETE})
public class StorageController extends BaseController<StorageService, StorageResp, StorageResp, StorageQuery, StorageReq> {
}