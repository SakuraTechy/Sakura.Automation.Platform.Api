package com.sakura.system.mapper;

import com.sakura.common.core.mapper.BaseMapper;
import com.sakura.system.domain.SysPortlet;

import java.util.List;

/**
 * 门户小页管理Mapper接口
 * @author sakura
 * @email sakura@qq.com
 * @date 2021-05-10
 */
public interface SysPortletMapper extends BaseMapper<SysPortlet>
{
    /**
     * 批量删除门户小页管理
     * @param ids 需要删除的数据ID
     * @return
     */
    public int deleteSysPortletByIds(String[] ids);

    /**
     * 获取最大编号
     * @param sysPortlet
     * @return
     */
    public Integer findMaxSort(SysPortlet sysPortlet);

    /**
     * 返回小页授权集合
     * @param sysPortlet
     * @return
     */
    public List<SysPortlet> findListByRoleId(SysPortlet sysPortlet);

    /**
     * 设计界面根据角色id返回集合
     * @param sysPortlet
     * @return
     */
    public List<SysPortlet> getPortletListByRoleId(SysPortlet sysPortlet);
    /**
     * 设计界面根据用户id返回集合
     * @param sysPortlet
     * @return
     */
    public List<SysPortlet> getPortletListByUserId(SysPortlet sysPortlet);


}
