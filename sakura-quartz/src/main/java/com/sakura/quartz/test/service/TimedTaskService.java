package com.sakura.quartz.test.service;

import com.sakura.common.core.service.BaseService;
import com.sakura.common.exception.job.TaskException;
import com.sakura.quartz.test.domain.TimedTask;
import com.github.pagehelper.PageInfo;
import org.quartz.SchedulerException;

/**
 * 测试管理-定时任务表Service接口
 * @author 刘智
 * @email liuzhi@sakura.com
 * @date 2024-02-28
 */
public interface TimedTaskService extends BaseService<TimedTask> {
    /**
     * 批量删除测试管理-定时任务表
     * @param ids 需要删除的测试管理-定时任务表ID集合
     * @return 结果
     */
    public int deleteTimedTaskByIds(String[] ids);

    boolean edit(TimedTask timedTask) throws SchedulerException, TaskException;

    /**
     * 批量删除测试管理-定时任务表
     * @param timedTask 定时任务
     * @return 结果
     */
    @Override
    boolean remove(TimedTask timedTask);

    /**
     * 分页查询获取执行日志
     * @param timedTask 定时任务
     * @return 结果
     */
    PageInfo<TimedTask> getLogList(TimedTask timedTask);
}