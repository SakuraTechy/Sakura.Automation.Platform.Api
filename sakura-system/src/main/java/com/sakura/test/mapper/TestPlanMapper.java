package com.sakura.test.mapper;

import com.sakura.common.core.mapper.BaseMapper;
import com.sakura.project.domain.ProjectConfig;
import org.apache.ibatis.annotations.Param;
import com.sakura.test.domain.TestPlan;

/**
 * 测试管理-测试计划表Mapper接口
 * @author 刘智
 * @email liuzhi@sakura.com
 * @date 2023-11-02
 */
public interface TestPlanMapper extends BaseMapper<TestPlan>
{

    /**
     * 批量删除测试管理-测试计划表
     * @param ids 需要删除的测试管理-测试计划表ID集合
     * @return
     */
    public int deleteTestPlanByIds(@Param("ids") String[] ids, @Param("DEL_FLAG_DELETE") String DEL_FLAG_DELETE);

    /**
     * 更新测试计划状态
     *
     * @param testPlan 测试计划
     * @return 结果
     */
    public int updateStatus(TestPlan testPlan);

}