package com.sakura.system.service;

import com.sakura.common.core.service.BaseService;
import com.sakura.system.domain.SysDictData;
import com.sakura.system.domain.SysDictType;

import java.util.List;

/**
 * 字典 业务层
 *
 * @author liuzhi
 */
public interface SysDictTypeService extends BaseService<SysDictType>
{
    /**
     * 批量删除字典信息
     *
     * @param dictIds 需要删除的字典ID
     * @return 结果
     */
    public void deleteDictTypeByIds(String[] dictIds);

    /**
     * 清空缓存数据
     */
    public void refreshCache();

    /**
     * 校验字典类型称是否唯一
     *
     * @param dictType 字典类型
     * @return 结果
     */
    public void checkDictTypeUnique(SysDictType dictType);

    /**
     * 加载参数缓存数据
     */
    public void loadingDictCache();
}
