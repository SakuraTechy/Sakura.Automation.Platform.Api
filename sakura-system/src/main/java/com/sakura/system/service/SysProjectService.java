package com.sakura.system.service;

import com.sakura.common.core.service.BaseService;
import com.sakura.system.domain.SysEnvironment;
import com.sakura.system.domain.SysProject;
import com.sakura.system.domain.SysYamlConfig;

import java.io.IOException;
import java.util.List;

/**
 * @author wutun
 * @data 2022/8/24 14:12
 */
public interface SysProjectService extends BaseService<SysProject> {

    /**
     * 插入项目信息记录
     * @param sysProject
     * @return
     */
    boolean insert(SysProject sysProject);

    /**
     * id批量删除项目信息记录
     * @param ids
     * @return
     */
    int deleteSysProjectByIds(String[] ids);

    /**
     *编辑环境配置
     * @return
     */
    boolean editEnvironmentConfig(List<SysEnvironment> sysEnvironmentList);

    /**
     * 获取环境配置列表信息
     * @param id
     * @return
     */
//    List getConfigDetail1(String id) throws IOException, NoSuchFieldException;
    SysYamlConfig.Project getConfigDetail(String id) throws IOException, NoSuchFieldException;

    /**
     * 删除项目下的环境配置
     * @param projectId
     * @param environmentId
     * @return
     */
    boolean removeConfig(String projectId, String environmentId);
}
