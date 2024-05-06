package com.sakura.quartz.controller;

import com.sakura.quartz.domain.*;
import com.sakura.quartz.service.QuartzService;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author qx
 * @date 2023/11/27
 * @des Quartz控制层
 */
@RestController
@RequestMapping("/quartz")
public class QuartzController {

    @Autowired
    private QuartzService quartzService;

    /**
     * 新增定时任务
     */
    @PostMapping("/add")
    public Boolean addTask(@RequestBody QuartzModel quartzModel) throws SchedulerException {
        return quartzService.addJob(quartzModel);
    }

    /**
     * 暂停定时任务
     */
    @PostMapping("/pause")
    public Boolean pauseTask(@RequestBody OperationModel operationModel) throws SchedulerException {
        return quartzService.pauseJob(operationModel);
    }

    /**
     * 恢复定时任务
     */
    @PostMapping("/resume")
    public Boolean resumeTask(@RequestBody OperationModel operationModel) throws SchedulerException {
        return quartzService.resumeJob(operationModel);
    }

    /**
     * 删除定时任务
     */
    @PostMapping("/delete")
    public Boolean deleteTask(@RequestBody OperationModel operationModel) throws SchedulerException {
        return quartzService.deleteJob(operationModel);
    }

    /**
     * 查看所有定时任务
     */
    @GetMapping("/queryAllJob")
    public List<Map<String, Object>> queryAllJob() throws SchedulerException {
        return quartzService.queryAllJob();
    }

    /**
     * 查看所有正在运行的定时任务
     */
    @GetMapping("/queryAllRunningJob")
    public List<Map<String, Object>> queryAllRunningJob() throws SchedulerException {
        return quartzService.queryAllRunningJob();
    }
}