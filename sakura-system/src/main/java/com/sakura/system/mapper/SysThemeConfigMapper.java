package com.sakura.system.mapper;

import com.sakura.common.core.mapper.BaseMapper;
import com.sakura.system.domain.SysThemeConfig;

/**
 * 用户主题信息记录Mapper接口
 * @author sakura
 * @email sakura@qq.com
 * @date 2021-04-29
 */
public interface SysThemeConfigMapper extends BaseMapper<SysThemeConfig>
{
    /**
     * 批量删除用户主题信息记录
     * @param ids 需要删除的数据ID
     * @return
     */
    public int deleteSysThemeConfigByIds(String[] ids);

    /**
     * 获取最大编号
     * @param sysThemeConfig
     * @return
     */
    public Integer findMaxSort(SysThemeConfig sysThemeConfig);

}
