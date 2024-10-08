package com.sakura.system.mapper;

import java.util.List;
import com.sakura.system.domain.SysRoleMenu;
import io.lettuce.core.dynamic.annotation.Param;

/**
 * 角色与菜单关联表 数据层
 * 
 * @author liuzhi
 */
public interface SysRoleMenuMapper
{
    /**
     * 查询菜单使用数量
     * 
     * @param menuId 菜单ID
     * @return 结果
     */
    public int checkMenuExistRole(String menuId);

    /**
     * 通过角色ID删除角色和菜单关联
     * 
     * @param roleId 角色ID
     * @return 结果
     */
    public int deleteRoleMenuByRoleId(String roleId);

    /**
     * 批量删除角色菜单关联信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteRoleMenu(String[] ids);

    /**
     * 批量新增角色菜单信息
     * 
     * @param roleMenuList 角色菜单列表
     * @return 结果
     */
    public int batchRoleMenu(List<SysRoleMenu> roleMenuList);
    /**
     * 批量删除角色下菜单授权
     * @return 结果
     */
    public int batchDelRoleMenu(SysRoleMenu sysRoleMenu);

}
