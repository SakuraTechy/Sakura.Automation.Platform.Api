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
import com.sakura.system.model.query.DictItemQuery;
import com.sakura.system.model.req.DictItemReq;
import com.sakura.system.model.resp.DictItemResp;
import com.sakura.system.service.DictItemService;
import com.sakura.starter.extension.crud.annotation.CrudRequestMapping;
import com.sakura.starter.extension.crud.controller.BaseController;
import com.sakura.starter.extension.crud.enums.Api;
import com.sakura.starter.log.core.annotation.Log;

/**
 * 字典项管理 API
 *
 * @author hagyao520
 * @since 2023/9/11 21:29
 */
@Log(module = "字典管理")
@Tag(name = "字典项管理 API")
@RestController
@CrudRequestMapping(value = "/system/dict/item", api = {Api.PAGE, Api.GET, Api.ADD, Api.UPDATE, Api.DELETE})
public class DictItemController extends BaseController<DictItemService, DictItemResp, DictItemResp, DictItemQuery, DictItemReq> {
}