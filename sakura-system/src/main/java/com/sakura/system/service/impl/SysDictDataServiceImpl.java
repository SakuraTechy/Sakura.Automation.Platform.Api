package com.sakura.system.service.impl;

import com.sakura.common.constant.UserConstants;
import com.sakura.common.core.domain.BaseEntity;
import com.sakura.common.core.service.BaseServiceImpl;
import com.sakura.common.exception.BizException;
import com.sakura.common.exception.CustomException;
import com.sakura.common.utils.NumberUtils;
import com.sakura.common.utils.StringUtils;
import com.sakura.framework.cache.ConfigUtils;
import com.sakura.framework.cache.DictUtils;
import com.sakura.system.common.SysErrorCode;
import com.sakura.system.domain.SysConfig;
import com.sakura.system.domain.SysDictData;
import com.sakura.system.mapper.SysDictDataMapper;
import com.sakura.system.service.SysDictDataService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * 字典 业务层处理
 *
 * @author liuzhi
 */
@Service
public class SysDictDataServiceImpl extends BaseServiceImpl<SysDictDataMapper, SysDictData> implements SysDictDataService {

    /**
     * 根据字典类型和字典键值查询字典数据信息
     *
     * @param dictType  字典类型
     * @param dictValue 字典键值
     * @return 字典标签
     */
    @Override
    public String selectDictLabel(String dictType, String dictValue) {
        SysDictData sysDictData = new SysDictData();
        sysDictData.setDictType(dictType);
        sysDictData.setDictValue(dictValue);
        List<SysDictData> list = mapper.findList(sysDictData);
        if (!CollectionUtils.isEmpty(list)) {
            return list.get(0).getDictLabel();
        }
        return null;
    }

    /**
     * 批量删除字典数据信息
     *
     * @param dictDataIds 需要删除的字典数据ID
     * @return 结果
     */
    @Override
    @Transactional(readOnly = false)
    public void deleteDictDataByIds(String[] dictDataIds) {
        for (String dictDataId : dictDataIds) {
            SysDictData sysDictData = get(dictDataId);
            remove(sysDictData);
            DictUtils.clearDictCache(sysDictData.getDictType());
        }
    }

    /**
     * 新增保存字典数据信息
     *
     * @param sysDictData 字典数据信息
     * @return 结果
     */
    @Override
    public boolean save(SysDictData sysDictData) {
        boolean result = super.save(sysDictData);
        if (result) {
            DictUtils.setDictCache(sysDictData.getDictType());
        }
        return result;
    }

    @Override
    public void refreshCache() {

    }

    @Override
    public void loadingUserCache() {

    }

    @Override
    public void checkUserDataScope(String userId) {

    }

    /**
     * 校验字典类型称是否唯一
     *
     * @param sysDictData 字典类型
     * @return 结果
     */
    @Override
    public void checkDictDataValueUnique(SysDictData sysDictData) {
        SysDictData sysDictDataUnique = new SysDictData();
        sysDictDataUnique.setNotEqualId(sysDictData.getId());
        sysDictDataUnique.setDictValue(sysDictData.getDictValue());
        sysDictDataUnique.setDictType(sysDictData.getDictType());
        if (!CollectionUtils.isEmpty(mapper.findListWithUnique(sysDictDataUnique))) {
            throw new BizException(SysErrorCode.B_SYSDICDATA_DictValueAlreadyExist);
        }
    }

    /**
     * 获取最大排序
     *
     * @param sysDictData
     * @return
     */
    @Override
    public int findMaxSort(SysDictData sysDictData) {
        return NumberUtils.nextOrder(mapper.findMaxSort(sysDictData));
    }
}
