package com.sakura.project.service;

import com.sakura.common.core.service.BaseService;
import com.sakura.project.domain.ProjectConfig;

/**
 * 项目配置Service接口
 *
 * @author 刘智
 * @email liuzhi@sakura.com
 * @date 2022-12-22
 */
public interface ProjectConfigService extends BaseService<ProjectConfig> {

    /**
     * 修改项目配置信息
     * @param projectConfig 项目配置
     * @return 结果
     */
    boolean edit(ProjectConfig projectConfig);

    /**
     * 批量删除项目配置
     *
     * @param ids 需要删除的项目配置ID集合
     * @return 结果
     */
    public int deleteProjectConfigByIds(String[] ids);
    
    /**
     * 批量删除项目配置
     * @param projectConfig 项目配置
     * @return 结果
     */
    @Override
    boolean remove(ProjectConfig projectConfig);

    /**
     * 更新项目配置状态
     * @param projectConfig 项目配置
     * @return 结果
     */
    public int updateStatus(ProjectConfig projectConfig);

//    /**
//     * 获取项目环境配置
//     * @param projectConfig 项目配置
//     * @return 结果
//     */
//    public SysYamlConfig.Project getEnvironmentConfig(ProjectConfig projectConfig);
}