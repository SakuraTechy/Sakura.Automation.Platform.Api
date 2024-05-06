package com.sakura.quartz.cluster.entity;

import lombok.Data;

import java.util.Date;

/** 
 * @author pdai
 *
 */
@Data
public class JobDetails{
	private String cronExpression;	
	private String jobClassName;	
	private String triggerGroupName;
	private String triggerName;
	private String jobGroupName;
	private String jobName;
	private Date nextFireTime;
	private Date previousFireTime;
	private Date startTime;
	private String timeZone;
	private String status;
}
