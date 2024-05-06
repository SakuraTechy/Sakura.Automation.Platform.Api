package com.sakura.quartz.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @author qx
 * @date 2023/11/27
 * @des 定时任务实体类
 */
@Data
public class QuartzModel implements Serializable {

    private String cron;

    private String jobGroup;

    private String jobName;

    private String triggerGroup;

    private String triggerName;

    private JobDetailModel jobData;

}