package com.sakura.system.mapper;

import com.sakura.common.core.mapper.BaseMapper;
import com.sakura.system.domain.SysEnvironment;

/**
 * @author wutun
 */
public interface SysEnvironmentMapper extends BaseMapper<SysEnvironment> {

    /**
     * 根据ID批量删除环境信息记录
     * @param ids
     * @return
     */
    boolean deleteSysEnvironmentByIds(String[] ids);

    /**
     * 添加sys_project_environment中间表记录
     * @param entity
     * @return
     */
    boolean insertProjectEnvironment(SysEnvironment entity);

    /**
     * 删除sys_project_environment中间表记录
     * @param entity
     * @return
     */
    boolean deleteProjectEnvironment(SysEnvironment entity);

    /**
     * 通过ID删除中间表记录
     * @param ids
     * @return
     */
    boolean deleteProjectEnvironmentByIds(String[] ids);
}
