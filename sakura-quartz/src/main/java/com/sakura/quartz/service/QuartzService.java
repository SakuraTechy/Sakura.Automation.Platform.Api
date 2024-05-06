package com.sakura.quartz.service;

import com.sakura.quartz.domain.*;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;

import java.util.List;
import java.util.Map;

/**
 * @author qx
 * @date 2023/11/27
 * @des Quartz服务层
 */
public interface QuartzService {

    /**
     * 新增定时任务
     */
    Boolean addJob(QuartzModel quartzModel) throws SchedulerException;

    /**
     * 暂停定时任务
     */
    Boolean pauseJob(OperationModel operationModel) throws SchedulerException;

    /**
     * 继续定时任务
     */
    Boolean resumeJob(OperationModel operationModel) throws SchedulerException;

    /**
     * 删除定时任务
     */
    Boolean deleteJob(OperationModel operationModel) throws SchedulerException;

    /**
     * 查询所有计划中的任务列表
     */
    List<Map<String, Object>> queryAllJob() throws SchedulerException;

    /**
     * 查询正在运行的任务
     */
    List<Map<String, Object>> queryAllRunningJob() throws SchedulerException;
}