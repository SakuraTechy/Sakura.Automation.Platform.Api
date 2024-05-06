package com.sakura.system.mapper;

import com.sakura.common.core.mapper.BaseMapper;
import com.sakura.system.domain.SysEnvironment;
import com.sakura.system.domain.SysProject;
import com.sakura.system.domain.SysVersion;

import java.util.HashMap;
import java.util.List;

/**
 * @author wutun
 *
 */
public interface SysProjectMapper extends BaseMapper<SysProject> {

    /**
     * 批量删除项目信息
     * @param ids
     * @return
     */
    int deleteSysProjectByIds(String[] ids);

    /**
     * 中间表数据插入
     * @param entity
     * @return
     */
    boolean insertUserProject(SysProject entity);

    /**
     * 修改信息之前要先把改项目ID下的成员先全部删除，再根据修改的信息重新加入新加入的成员
     * @param entity
     * @return
     */
    boolean deleteUserProject(SysProject entity);

    /**
     * 获取项目下的环境配置信息
     * @param id
     * @return
     */
    List<SysEnvironment> getConfigDetail(String id);

    /**
     * 通过ID批量删除中间表数据
     * @param ids
     * @return
     */
    boolean deleteUserProjectByIds(String[] ids);

    /**
     * 编辑环境配置信息
     * @param sysEnvironment
     * @return
     */
    int editEnvironmentConfig(SysEnvironment sysEnvironment);

    /**
     * 删除项目下的配置信息
     * @param map
     * @return
     */
    boolean removeConfig(HashMap<String, String> map);

    /**
     * 编辑版本的名称
     * @param sysVersion
     */
    void editProjectVersion(SysVersion sysVersion);
    void editProjectVersion(SysEnvironment sysEnvironment);
    void editProjectVersion(SysProject sysProject);

    /**
     * 通过项目ID删除项目相关环境
     * @param ids
     */
    void deleteEnvironmentProjectByProjectIds(String[] ids);
}
