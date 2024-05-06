package com.sakura.project.mapper;

import com.sakura.common.core.mapper.BaseMapper;
import com.sakura.project.domain.EnvironmentConfig;
import org.apache.ibatis.annotations.Param;

/**
 * 环境配置Mapper接口
 *
 * @author 刘智
 * @email liuzhi@sakura.com
 * @date 2022-12-26
 */
public interface EnvironmentConfigMapper extends BaseMapper<EnvironmentConfig> {
    /**
     * 更新环境配置状态（修改项目下其他环境为关闭状态）
     *
     * @param environmentConfig 环境配置
     * @return 结果
     */
    public boolean closeStatus(EnvironmentConfig environmentConfig);

    /**
     * 更新环境配置状态
     *
     * @param environmentConfig 环境配置
     * @return 结果
     */
    public boolean updateStatus(EnvironmentConfig environmentConfig);

    /**
     * 获取当前项目下已启用的环境数量
     * @param environmentConfig 环境配置
     * @return 结果
     */
    int getStatusNum(EnvironmentConfig environmentConfig);

    /**
     * 批量删除环境配置
     *
     * @param ids 需要删除的环境配置ID集合
     * @return 结果
     */
    public int deleteEnvironmentConfigByIds(@Param("ids") String[] ids, @Param("DEL_FLAG_DELETE") String DEL_FLAG_DELETE);
}