package com.sakura.system.service;

import com.sakura.common.core.service.BaseService;
import com.sakura.system.domain.SysThemeConfig;

/**
 * 用户主题信息记录Service接口
 * @author sakura
 * @email sakura@qq.com
 * @date 2021-04-29
 */
public interface SysThemeConfigService extends BaseService<SysThemeConfig>
{
    /**
     * 批量删除用户主题信息记录
     * @param ids 需要删除的用户主题信息记录ID
     * @return
     */
    public int deleteSysThemeConfigByIds(String[] ids);

    /**
    * 获取最大编号
    * @param sysThemeConfig
    * @return
    */
    public int findMaxSort(SysThemeConfig sysThemeConfig);

}
