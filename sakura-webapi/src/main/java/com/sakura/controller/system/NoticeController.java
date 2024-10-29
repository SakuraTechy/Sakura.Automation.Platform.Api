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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.sakura.system.model.query.NoticeQuery;
import com.sakura.system.model.req.NoticeReq;
import com.sakura.system.model.resp.NoticeDetailResp;
import com.sakura.system.model.resp.NoticeResp;
import com.sakura.system.service.NoticeService;
import com.sakura.starter.core.util.validate.ValidationUtils;
import com.sakura.starter.extension.crud.annotation.CrudRequestMapping;
import com.sakura.starter.extension.crud.controller.BaseController;
import com.sakura.starter.extension.crud.enums.Api;
import com.sakura.starter.extension.crud.model.resp.BaseIdResp;
import com.sakura.starter.extension.crud.util.ValidateGroup;

import java.time.LocalDateTime;

/**
 * 公告管理 API
 *
 * @author hagyao520
 * @since 2023/8/20 10:55
 */
@Tag(name = "公告管理 API")
@RestController
@CrudRequestMapping(value = "/system/notice", api = {Api.PAGE, Api.GET, Api.ADD, Api.UPDATE, Api.DELETE})
public class NoticeController extends BaseController<NoticeService, NoticeResp, NoticeDetailResp, NoticeQuery, NoticeReq> {

    @Override
    public BaseIdResp<Long> add(@Validated(ValidateGroup.Crud.Add.class) @RequestBody NoticeReq req) {
        this.checkTime(req);
        return super.add(req);
    }

    @Override
    public void update(@Validated(ValidateGroup.Crud.Update.class) @RequestBody NoticeReq req, @PathVariable Long id) {
        this.checkTime(req);
        super.update(req, id);
    }

    /**
     * 检查时间
     *
     * @param req 创建或修改信息
     */
    private void checkTime(NoticeReq req) {
        LocalDateTime effectiveTime = req.getEffectiveTime();
        LocalDateTime terminateTime = req.getTerminateTime();
        if (null != effectiveTime && null != terminateTime) {
            ValidationUtils.throwIf(terminateTime.isBefore(effectiveTime), "终止时间必须晚于生效时间");
        }
    }
}