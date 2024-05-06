package com.sakura.quartz.service.impl;

import com.sakura.quartz.domain.*;
import com.sakura.quartz.service.QuartzService;
import com.sakura.quartz.task.SimpleTask;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author qx
 * @date 2023/11/27
 * @des Quartz服务层实现类
 */
@Slf4j
@Service
public class QuartzServiceImpl implements QuartzService {

    @Autowired
    private Scheduler scheduler;

    @Override
    public Boolean addJob(QuartzModel quartzModel) throws SchedulerException {
        log.info("quartzModel:{}", quartzModel);
        String cron = quartzModel.getCron();
        JobDetail jobDetail = JobBuilder.newJob(SimpleTask.class).withIdentity(quartzModel.getJobName(), quartzModel.getJobGroup()).build();
        JobDetailModel jobDetailModel = quartzModel.getJobData();
        if (!Objects.isNull(jobDetailModel)) {
            jobDetail.getJobDataMap().put("jobDetailModel", jobDetailModel);
        }
        CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(quartzModel.getTriggerName(), quartzModel.getTriggerGroup()).startNow()
                .withSchedule(CronScheduleBuilder.cronSchedule(cron)).build();
        scheduler.scheduleJob(jobDetail, trigger);
//        scheduler.standby();
        if (!scheduler.isShutdown()) {
            scheduler.start();
        }
        log.info("----定时任务成功添加进quartz队列中----");
        return true;
    }

    @Override
    public Boolean pauseJob(OperationModel operationModel) throws SchedulerException {
        scheduler.pauseJob(JobKey.jobKey(operationModel.getJobName(), operationModel.getJobGroup()));
        log.info("暂停定时任务成功");
        return true;
    }

    @Override
    public Boolean resumeJob(OperationModel operationModel) throws SchedulerException {
        scheduler.resumeJob(JobKey.jobKey(operationModel.getJobName(), operationModel.getJobGroup()));
        log.info("恢复定时任务成功");
        return true;
    }

    @Override
    public Boolean deleteJob(OperationModel operationModel) throws SchedulerException {
        TriggerKey triggerKey = TriggerKey.triggerKey(operationModel.getJobName(), operationModel.getJobGroup());
        // 停止触发器
        scheduler.resumeTrigger(triggerKey);
        // 移除触发器
        scheduler.unscheduleJob(triggerKey);
        scheduler.deleteJob(JobKey.jobKey(operationModel.getJobName(), operationModel.getJobGroup()));
        log.info("删除定时任务成功");
        return true;
    }

    @Override
    public List<Map<String, Object>> queryAllJob() throws SchedulerException {
        List<Map<String, Object>> jobList = new ArrayList<>();
        GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
        Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);
        for (JobKey jobKey : jobKeys) {
            List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
            for (Trigger trigger : triggers) {
                jobList.add(this.buildMap(jobKey, trigger));
            }
        }
        return jobList;
    }

    @Override
    public List<Map<String, Object>> queryAllRunningJob() throws SchedulerException {
        List<Map<String, Object>> jobList = new ArrayList<>();
        List<JobExecutionContext> executingJobs = scheduler.getCurrentlyExecutingJobs();
        for (JobExecutionContext executingJob : executingJobs) {
            JobDetail jobDetail = executingJob.getJobDetail();
            JobKey jobKey = jobDetail.getKey();
            Trigger trigger = executingJob.getTrigger();
            jobList.add(this.buildMap(jobKey, trigger));
        }
        return jobList;
    }

    /**
     * 返回指定的map集合
     */
    private Map<String, Object> buildMap(JobKey jobKey, Trigger trigger) throws SchedulerException {
        Map<String, Object> map = new HashMap<>();
        map.put("jobName", jobKey.getName());
        map.put("jobGroupName", jobKey.getGroup());
        map.put("description", "触发器:" + trigger.getKey());
        Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
        map.put("jobStatus", triggerState.name());
        if (trigger instanceof CronTrigger) {
            CronTrigger cronTrigger = (CronTrigger) trigger;
            String cronExpression = cronTrigger.getCronExpression();
            map.put("jobTime", cronExpression);
        }
        return map;
    }
}