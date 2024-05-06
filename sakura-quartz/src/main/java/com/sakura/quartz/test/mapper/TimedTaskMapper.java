package com.sakura.quartz.test.mapper;

import com.sakura.common.core.mapper.BaseMapper;
import com.sakura.quartz.test.domain.TimedTask;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 测试管理-定时任务表Mapper接口
 * @author 刘智
 * @email liuzhi@sakura.com
 * @date 2024-02-28
 */
public interface TimedTaskMapper extends BaseMapper<TimedTask>
{

    /**
     * 批量删除测试管理-定时任务表
     * @param ids 需要删除的测试管理-定时任务表ID集合
     * @return
     */
    public int deleteTimedTaskByIds(@Param("ids") String[] ids, @Param("DEL_FLAG_DELETE") String DEL_FLAG_DELETE);

    /**
     * 更新定时任务状态
     *
     * @param timedTask 定时任务
     * @return 结果
     */
    public int updateStatus(TimedTask timedTask);

    /**
     * 分页获取定时任务执行日志
     *
     * @param timedTask 定时任务
     * @return 结果
     */
    List<TimedTask> getLogList(TimedTask timedTask);
}