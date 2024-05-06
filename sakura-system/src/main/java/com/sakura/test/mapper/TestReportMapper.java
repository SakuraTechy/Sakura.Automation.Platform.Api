package com.sakura.test.mapper;

import com.sakura.common.core.mapper.BaseMapper;
import com.sakura.system.domain.SysScene;
import com.sakura.test.domain.TestReport;
import org.apache.ibatis.annotations.Param;

/**
 * 测试管理-测试报告Mapper接口
 * @author 刘智
 * @email liuzhi@sakura.com
 * @date 2023-11-02
 */
public interface TestReportMapper extends BaseMapper<TestReport>
{

    /**
     * 批量删除测试管理-测试报告
     * @param ids 需要删除的测试管理-测试报告ID集合
     * @return
     */
    public int deleteTestReportByIds(@Param("ids") String[] ids, @Param("DEL_FLAG_DELETE") String DEL_FLAG_DELETE);

    /**
     * 更新测试报告状态
     *
     * @param testReport 测试报告
     * @return 结果
     */
    boolean updateStatus(TestReport testReport);

    /**
     * 上传测试场景结果
     * @param testReport
     * @return
     */
    boolean uploadResults(TestReport testReport);

}