package com.sakura.quartz.task;

import com.sakura.quartz.domain.JobDetailModel;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author qx
 * @date 2023/11/24
 * @des 定时任务执行器
 */
@Slf4j
@Component("simpleTask")
public class SimpleTask extends QuartzJobBean {

    /**
     * 根据不同的jobDetail携带的type参数区分不同的业务
     */
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        synchronized (this) {
            log.info("开始执行定时任务...........");
            JobDetailModel jobDetailModel = (JobDetailModel) context.getJobDetail().getJobDataMap().get("jobDetailModel");
            Integer type = jobDetailModel.getType();
            String content = jobDetailModel.getContent();
            try {
                switch (type) {
                    case 1:
                        log.info("task1被执行,content:{}", content);
                        Thread.sleep(5000);
                        log.info("-------task1定时任务执行结束------");
                        break;
                    case 2:
                        log.info("task2被执行,content:{}", content);
                        Thread.sleep(5000);
                        log.info("-------task2定时任务执行结束------");
                        break;
                    default:
                        log.info("-------定时任务执行结束------");
                        log.info("-------定时任务执行结束------");
                }
            } catch (Throwable t) {
                log.error(t.getMessage(), t);
            }
            log.info("定时任务执行结束..............");
        }
    }
}