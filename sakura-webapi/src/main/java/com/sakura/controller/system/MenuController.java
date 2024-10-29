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

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.sakura.system.model.query.MenuQuery;
import com.sakura.system.model.req.MenuReq;
import com.sakura.system.model.resp.MenuResp;
import com.sakura.system.service.MenuService;
import com.sakura.starter.core.constant.StringConstants;
import com.sakura.starter.core.util.URLUtils;
import com.sakura.starter.core.util.validate.ValidationUtils;
import com.sakura.starter.extension.crud.annotation.CrudRequestMapping;
import com.sakura.starter.extension.crud.controller.BaseController;
import com.sakura.starter.extension.crud.enums.Api;
import com.sakura.starter.extension.crud.model.resp.BaseIdResp;
import com.sakura.starter.extension.crud.util.ValidateGroup;

/**
 * 菜单管理 API
 *
 * @author hagyao520
 * @since 2023/2/15 20:35
 */
@Tag(name = "菜单管理 API")
@RestController
@CrudRequestMapping(value = "/system/menu", api = {Api.TREE, Api.GET, Api.ADD, Api.UPDATE, Api.DELETE})
public class MenuController extends BaseController<MenuService, MenuResp, MenuResp, MenuQuery, MenuReq> {

    @Override
    public BaseIdResp<Long> add(@Validated(ValidateGroup.Crud.Add.class) @RequestBody MenuReq req) {
        this.checkPath(req);
        return super.add(req);
    }

    @Override
    public void update(@Validated(ValidateGroup.Crud.Update.class) @RequestBody MenuReq req, @PathVariable Long id) {
        this.checkPath(req);
        super.update(req, id);
    }

    /**
     * 检查路由地址格式
     *
     * @param req 创建或修改信息
     */
    private void checkPath(MenuReq req) {
        Boolean isExternal = ObjectUtil.defaultIfNull(req.getIsExternal(), false);
        String path = req.getPath();
        ValidationUtils.throwIf(Boolean.TRUE.equals(isExternal) && !URLUtils
            .isHttpUrl(path), "路由地址格式错误，请以 http:// 或 https:// 开头");
        // 非外链菜单参数修正
        if (Boolean.FALSE.equals(isExternal)) {
            ValidationUtils.throwIf(URLUtils.isHttpUrl(path), "路由地址格式错误");
            req.setPath(StrUtil.isBlank(path) ? path : StrUtil.prependIfMissing(path, StringConstants.SLASH));
            req.setName(StrUtil.removePrefix(req.getName(), StringConstants.SLASH));
            req.setComponent(StrUtil.removePrefix(req.getComponent(), StringConstants.SLASH));
        }
    }
}
