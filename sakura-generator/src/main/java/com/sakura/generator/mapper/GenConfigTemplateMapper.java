package com.sakura.generator.mapper;

import com.sakura.common.core.mapper.BaseMapper;
import com.sakura.generator.domain.GenConfigTemplate;

import java.util.List;


/**
 * 模板配置Mapper接口
 *
 * @author liuzhi
 * @date 2021-03-06
 */
public interface GenConfigTemplateMapper extends BaseMapper<GenConfigTemplate>
{

    /**
     * 唯一性校验判断
     *
     * @return 结果
     */
    @Override
    public List<GenConfigTemplate> findListWithUnique(GenConfigTemplate genConfigTemplate);

    /**
     * 批量删除模板配置
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteGenConfigTemplateByIds(String[] ids);

    /**
     * 查询最大编号
     * @param
     * @return int
     */
    public Integer findMaxSort(GenConfigTemplate genConfigTemplate);
}