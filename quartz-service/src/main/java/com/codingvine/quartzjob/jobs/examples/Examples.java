package com.codingvine.quartzjob.jobs.examples;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.quartz.JobDataMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codingvine.quartzjob.jobs.CronJob;
import com.codingvine.quartzjob.jobs.SimpleJob;
import com.codingvine.quartzjob.service.JobService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class Examples {

	@Autowired
	private JobService jobService;

	public boolean schedule(String jobName, String groupName,
			Date jobScheduleTime,
			String cronExpression, ZoneId timeZone) {
		log.info("JobController.schedule()");

		if (!jobService.isJobWithNamePresent(jobName, groupName)) {
			JobDataMap dataMap = buildEmailJobDetail();
			boolean isRecoverable = true;
			if (cronExpression == null || cronExpression.trim().equals("")) {
				// Single Trigger
				dataMap.put("body", "This is a demo email from quartz(SimpaleJob type) service--"+ UUID.randomUUID());
				return jobService.scheduleOneTimeJob(jobName, groupName, SimpleJob.class, jobScheduleTime, dataMap, isRecoverable);
			} else {
				// Cron Trigger
				dataMap.put("body", "This is a demo email from quartz(CORN type) service--"+ UUID.randomUUID());
				return jobService.scheduleCronJob(jobName, groupName, CronJob.class, jobScheduleTime, cronExpression, timeZone, dataMap, isRecoverable);
			}
		}
		return false;
	}
	
	
	 private JobDataMap buildEmailJobDetail() {
	        JobDataMap jobDataMap = new JobDataMap();

	        jobDataMap.put("email", "quartzdemoacc@gmail.com");
	        jobDataMap.put("subject", "Demo mail-"+UUID.randomUUID());
	       

	      return jobDataMap;
	    }

	
	

	public boolean unschedule(String jobName) {
		log.info("JobController.unschedule()");
		return jobService.unScheduleJob(jobName);
	}

	public boolean delete(String jobName, String groupName) {
		log.info("JobController.delete()");

		if (jobService.isJobWithNamePresent(jobName, groupName)) {
			boolean isJobRunning = jobService.isJobRunning(jobName, groupName);

			if (!isJobRunning) {
				return jobService.deleteJob(jobName, groupName);

			}
		}
		return false;
	}

	public boolean pause(String jobName, String groupName) {
		log.info("JobController.pause()");

		if (jobService.isJobWithNamePresent(jobName, groupName)) {

			boolean isJobRunning = jobService.isJobRunning(jobName, groupName);

			if (!isJobRunning) {
				return jobService.pauseJob(jobName, groupName);
			}
		}
		return false;
	}

	public boolean resume(String jobName, String groupName) {
		log.info("JobController.resume()");

		if (jobService.isJobWithNamePresent(jobName, groupName)) {
			String jobState = jobService.getJobState(jobName, groupName);

			if (jobState.equals("PAUSED")) {
				log.info("Job current state is PAUSED, Resuming job...");
				return jobService.resumeJob(jobName, groupName);
			}
		}
		return false;
	}

	public void updateJob(String jobName, String groupName,
			 Date jobScheduleTime,
			String cronExpression, ZoneId timeZone) {
		log.info("JobController.updateJob()");

		// Edit Job
		if (jobService.isJobWithNamePresent(jobName, groupName)) {

			if (cronExpression == null || cronExpression.trim().equals("")) {
				// Single Trigger
				jobService.updateOneTimeJob(jobName, jobScheduleTime);
			} else {
				// Cron Trigger
				jobService.updateCronJob(jobName, jobScheduleTime, cronExpression, timeZone);
			}
		}
	}

	public List<Map<String, Object>> getAllJobs() {
		log.info("JobController.getAllJobs()");
		return jobService.getAllJobs();
	}

	public boolean checkJobName(String jobName, String groupName) {
		log.info("JobController.checkJobName()");

		return jobService.isJobWithNamePresent(jobName, groupName);
	}

	public boolean isJobRunning(String jobName, String groupName) {
		log.info("JobController.isJobRunning()");

		return jobService.isJobRunning(jobName, groupName);
	}

	public String getJobState(String jobName, String groupName) {
		log.info("JobController.getJobState()");

		return jobService.getJobState(jobName, groupName);
	}

	public boolean stopJob(String jobName, String groupName) {
		log.info("JobController.stopJob()");

		if (jobService.isJobWithNamePresent(jobName, groupName) && jobService.isJobRunning(jobName, groupName)) {
			return jobService.stopJob(jobName, groupName);
		}
		return false;
	}

	public boolean startJobNow(String jobName, String groupName) {
		log.info("JobController.startJobNow()");

		if (jobService.isJobWithNamePresent(jobName, groupName) && !jobService.isJobRunning(jobName, groupName)) {
			return jobService.startJobNow(jobName, groupName);
		}
		return false;
	}
}