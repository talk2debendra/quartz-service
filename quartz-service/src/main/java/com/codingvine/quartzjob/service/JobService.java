package com.codingvine.quartzjob.service;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.quartz.JobDataMap;
import org.springframework.scheduling.quartz.QuartzJobBean;

public interface JobService {
	
	/**
	 * Schedule a job to be execute only one time.
	 * 
	 * @param jobName Name of the Job
	 * @param groupKey Job group
	 * @param jobClass Job to be execute
	 * @param date Start Date & time
	 * @param jobDataMap Job data
	 * @param isRecoverable 
	 * */
	boolean scheduleOneTimeJob(String jobName, String groupKey, Class<? extends QuartzJobBean> jobClass, Date date, JobDataMap jobDataMap, boolean isRecoverable);

	
	//TBD- Javadoc comments to be add	
	boolean scheduleCronJob(String jobName, String groupName, Class<? extends QuartzJobBean> jobClass, Date date, String cronExpression, ZoneId timezone, JobDataMap jobDataMap, boolean isRecoverable);

	boolean updateOneTimeJob(String jobName, Date date);

	boolean updateCronJob(String jobName, Date date, String cronExpression, ZoneId timezone);

	boolean unScheduleJob(String jobName);

	boolean deleteJob(String jobName, String groupKey);

	boolean pauseJob(String jobName, String groupKey);

	boolean resumeJob(String jobName, String groupKey);

	boolean startJobNow(String jobName, String groupKey);

	boolean isJobRunning(String jobName, String groupKey);

	List<Map<String, Object>> getAllJobs();

	boolean isJobWithNamePresent(String jobName, String groupKey);

	String getJobState(String jobName, String groupKey);

	boolean stopJob(String jobName, String groupKey);
}