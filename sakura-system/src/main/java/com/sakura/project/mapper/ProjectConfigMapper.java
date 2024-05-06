package com.sakura.project.mapper;

import com.sakura.common.core.mapper.BaseMapper;
import com.sakura.project.domain.ProjectConfig;
import org.apache.ibatis.annotations.Param;

/**
 * 项目配置Mapper接口
 *
 * @author 刘智
 * @email liuzhi@sakura.com
 * @date 2022-12-22
 */
public interface ProjectConfigMapper extends BaseMapper<ProjectConfig> {

    /**
     * 插入用户和项目关联数据
     * @param projectConfig 项目配置
     * @return 结果
     */
    boolean insertUserProject(ProjectConfig projectConfig);

    /**
     * 修改信息之前要先把改项目ID下的成员先全部删除，再根据修改的信息重新加入新加入的成员
     * @param projectConfig 项目配置
     * @return 结果
     */
    boolean deleteUserProject(ProjectConfig projectConfig);

    /**
     * 通过id批量删除项目成员关联信息
     * @param ids 批量删除ids
     * @return 结果
     */
    boolean deleteUserProjectByIds(String[] ids);

    /**
     * 批量删除项目配置
     *
     * @param ids 需要删除的项目配置ID集合
     * @return 结果
     */
    public int deleteProjectConfigByIds(@Param("ids") String[] ids, @Param("DEL_FLAG_DELETE") String DEL_FLAG_DELETE);

    /**
     * 更新项目配置状态
     *
     * @param projectConfig 项目配置
     * @return 结果
     */
    public int updateStatus(ProjectConfig projectConfig);
}