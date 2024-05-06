package com.sakura.test.service;

import com.sakura.common.core.service.BaseService;
import com.sakura.test.domain.TestReport;

import java.util.List;
import java.util.Map;

/**
 * 测试管理-测试报告Service接口
 * @author 刘智
 * @email liuzhi@sakura.com
 * @date 2023-11-02
 */
public interface TestReportService extends BaseService<TestReport> {
    /**
     * 批量删除测试管理-测试报告
     * @param ids 需要删除的测试管理-测试报告ID集合
     * @return 结果
     */
    public int deleteTestReportByIds(String[] ids);

    /**
     * 批量删除测试管理-测试报告
     * @param testReport 测试计划
     * @return 结果
     */
    @Override
    boolean remove(TestReport testReport);

    boolean uploadResults(TestReport testReport);
}