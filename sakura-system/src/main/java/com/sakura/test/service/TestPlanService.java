package com.sakura.test.service;

import com.sakura.common.core.service.BaseService;
import com.sakura.test.domain.TestPlan;

import java.util.List;
import java.util.Map;

/**
 * 测试管理-测试计划表Service接口
 * @author 刘智
 * @email liuzhi@sakura.com
 * @date 2023-11-02
 */
public interface TestPlanService extends BaseService<TestPlan> {

    Map<String,Object> findTestScenePage(TestPlan testPlan, List<?> list);

    /**
     * 批量删除测试管理-测试计划表
     * @param ids 需要删除的测试管理-测试计划表ID集合
     * @return 结果
     */
    public int deleteTestPlanByIds(String[] ids);

    /**
     * 批量删除测试管理-测试计划表
     * @param testPlan 测试计划
     * @return 结果
     */
    @Override
    boolean remove(TestPlan testPlan);

    /**
     * 批量删除测试管理-测试计划表
     * @param testPlan 测试计划
     * @return 结果
     */
    boolean addTestScene(TestPlan testPlan);
}