package com.sakura.system.service.impl;

import com.sakura.common.constant.UserConstants;
import com.sakura.common.core.service.BaseServiceImpl;
import com.sakura.common.exception.BizException;
import com.sakura.common.exception.CustomException;
import com.sakura.common.utils.StringUtils;
import com.sakura.framework.cache.ConfigUtils;
import com.sakura.framework.cache.DictUtils;
import com.sakura.system.common.SysErrorCode;
import com.sakura.system.domain.SysConfig;
import com.sakura.system.domain.SysDictData;
import com.sakura.system.domain.SysDictType;
import com.sakura.system.mapper.SysDictDataMapper;
import com.sakura.system.mapper.SysDictTypeMapper;
import com.sakura.system.service.SysDictTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 字典 业务层处理
 *
 * @author liuzhi
 */
@Service
public class SysDictTypeServiceImpl extends BaseServiceImpl<SysDictTypeMapper, SysDictType> implements SysDictTypeService {

    @Autowired(required = false)
    private SysDictDataMapper sysDictDataMapper;

    /**
     * 批量删除字典类型信息
     *
     * @param dictIds 需要删除的字典ID
     * @return 结果
     */
    @Override
    @Transactional(readOnly = false)
    public void deleteDictTypeByIds(String[] dictIds) {
        for (String dictId : dictIds) {
            SysDictType dictType = get(dictId);
            SysDictData sysDictData = new SysDictData();
            sysDictData.setDictType(dictType.getDictType());
            List<SysDictData> dictDataList = sysDictDataMapper.findList(sysDictData);
            if (dictDataList.size() > 0) {
                throw new CustomException(String.format("%1$s存在字典信息,不能删除", dictType.getDictName()));
            }
        }
        mapper.deleteDictTypeByIds(dictIds, SysDictType.DEL_FLAG_DELETE);
    }

    /**
     * 清空缓存数据
     */
    @Override
    public void refreshCache() {
        DictUtils.clearDictCache();
        loadingDictCache();
    }

    @Override
    public void loadingUserCache() {

    }

    @Override
    public void checkUserDataScope(String userId) {

    }

    /**
     * 新增保存字典类型信息
     *
     * @param dictType 字典类型信息
     * @return 结果
     */
    @Override
    public boolean save(SysDictType dictType) {
        boolean result = super.save(dictType);
        if (result) {
            DictUtils.clearDictCache();
        }
        return result;
    }

    /**
     * 校验字典类型称是否唯一
     *
     * @param dict 字典类型
     * @return 结果
     */
    @Override
    public void checkDictTypeUnique(SysDictType dict) {
        SysDictType sysDictTypeUnique = new SysDictType();
        sysDictTypeUnique.setNotEqualId(dict.getId());
        sysDictTypeUnique.setDictType(dict.getDictType());
        if (!CollectionUtils.isEmpty(mapper.findListWithUnique(sysDictTypeUnique))) {
            throw new BizException(SysErrorCode.B_SYSDICTTYPE_DictTypeAlreadyExist);
        }
    }

    /**
     * 加载参数缓存数据
     */
    @Override
    public void loadingDictCache()
    {
        List<SysDictType> sysDictTypeList = findList(new SysDictType());
        for (SysDictType sysDictType : sysDictTypeList)
        {
            DictUtils.setDictCache(sysDictType.getDictType());
        }
    }
}
