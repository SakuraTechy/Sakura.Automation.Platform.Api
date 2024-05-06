package com.sakura.project.mapper;

import com.sakura.common.core.mapper.BaseMapper;
import com.sakura.project.domain.AutomationConfig;
import org.apache.ibatis.annotations.Param;

/**
 * 自动化配置Mapper接口
 *
 * @author 刘智
 * @email liuzhi@sakura.com
 * @date 2022-12-26
 */
public interface AutomationConfigMapper extends BaseMapper<AutomationConfig> {
    /**
     * 更新自动化配置状态（修改项目下其他自动化为关闭状态）
     *
     * @param automationConfig 自动化配置
     * @return 结果
     */
    public int closeStatus(AutomationConfig automationConfig);

    /**
     * 更新自动化配置状态
     *
     * @param automationConfig 自动化配置
     * @return 结果
     */
    public int updateStatus(AutomationConfig automationConfig);

    /**
     * 批量删除自动化配置
     *
     * @param ids 需要删除的自动化配置ID集合
     * @return 结果
     */
    public int deleteAutomationConfigByIds(@Param("ids") String[] ids, @Param("DEL_FLAG_DELETE") String DEL_FLAG_DELETE);
}