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

package com.sakura.system.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.sakura.system.mapper.DictMapper;
import com.sakura.system.model.entity.DictDO;
import com.sakura.system.model.query.DictQuery;
import com.sakura.system.model.req.DictReq;
import com.sakura.system.model.resp.DictResp;
import com.sakura.system.service.DictItemService;
import com.sakura.system.service.DictService;
import com.sakura.starter.core.util.validate.CheckUtils;
import com.sakura.starter.extension.crud.model.resp.LabelValueResp;
import com.sakura.starter.extension.crud.service.impl.BaseServiceImpl;

import java.util.List;
import java.util.Optional;

/**
 * 字典业务实现
 *
 * @author hagyao520
 * @since 2023/9/11 21:29
 */
@Service
@RequiredArgsConstructor
public class DictServiceImpl extends BaseServiceImpl<DictMapper, DictDO, DictResp, DictResp, DictQuery, DictReq> implements DictService {

    private final DictItemService dictItemService;

    @Override
    protected void beforeAdd(DictReq req) {
        String name = req.getName();
        CheckUtils.throwIf(this.isNameExists(name, null), "新增失败，[{}] 已存在", name);
        String code = req.getCode();
        CheckUtils.throwIf(this.isCodeExists(code, null), "新增失败，[{}] 已存在", code);
    }

    @Override
    protected void beforeUpdate(DictReq req, Long id) {
        String name = req.getName();
        CheckUtils.throwIf(this.isNameExists(name, id), "修改失败，[{}] 已存在", name);
        DictDO oldDict = super.getById(id);
        CheckUtils.throwIfNotEqual(req.getCode(), oldDict.getCode(), "不允许修改字典编码");
    }

    @Override
    protected void beforeDelete(List<Long> ids) {
        List<DictDO> list = baseMapper.lambdaQuery()
            .select(DictDO::getName, DictDO::getIsSystem)
            .in(DictDO::getId, ids)
            .list();
        Optional<DictDO> isSystemData = list.stream().filter(DictDO::getIsSystem).findFirst();
        CheckUtils.throwIf(isSystemData::isPresent, "所选字典 [{}] 是系统内置字典，不允许删除", isSystemData.orElseGet(DictDO::new)
            .getName());
        dictItemService.deleteByDictIds(ids);
    }

    @Override
    public List<LabelValueResp> listEnumDict() {
        List<String> enumDictNameList = dictItemService.listEnumDictNames();
        return enumDictNameList.stream().map(name -> new LabelValueResp(name, name)).toList();
    }

    /**
     * 名称是否存在
     *
     * @param name 名称
     * @param id   ID
     * @return 是否存在
     */
    private boolean isNameExists(String name, Long id) {
        return baseMapper.lambdaQuery().eq(DictDO::getName, name).ne(null != id, DictDO::getId, id).exists();
    }

    /**
     * 编码是否存在
     *
     * @param code 编码
     * @param id   ID
     * @return 是否存在
     */
    private boolean isCodeExists(String code, Long id) {
        return baseMapper.lambdaQuery().eq(DictDO::getCode, code).ne(null != id, DictDO::getId, id).exists();
    }
}