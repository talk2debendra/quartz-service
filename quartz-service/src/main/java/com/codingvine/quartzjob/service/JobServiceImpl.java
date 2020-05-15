package com.codingvine.quartzjob.service;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.quartz.ObjectAlreadyExistsException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.Trigger.TriggerState;
import org.quartz.TriggerKey;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import com.codingvine.quratzjob.util.JobUtil;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class JobServiceImpl implements JobService {

	@Autowired
	private ApplicationContext context;

	@Lazy
	@Autowired
	private SchedulerFactoryBean schedulerFactoryBean;

	/**
	 * Schedule a job by jobName at given date.
	 */
	@Override
	public boolean scheduleOneTimeJob(String jobName, String groupKey, Class<? extends QuartzJobBean> jobClass, Date date, JobDataMap jobDataMap, boolean isRecoverable) {
		log.info("Request received to scheduleJob");

		String jobKey = jobName;
		String triggerKey = jobName;

		JobDetail jobDetail = JobUtil.createJob(jobClass, true, context, jobKey, groupKey, jobDataMap, isRecoverable);

		log.info("creating trigger for key: " + jobKey + " at date :" + date);
		Trigger cronTriggerBean = JobUtil.createSingleTrigger(triggerKey, date, SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW);

		try {
			Scheduler scheduler = schedulerFactoryBean.getScheduler();
			Date dt = scheduler.scheduleJob(jobDetail, cronTriggerBean);
			log.info("Job with key jobKey: " + jobKey + " and group :" + groupKey + " scheduled successfully for date :" + dt);
			return true;
		} catch (SchedulerException e) {
			log.error("SchedulerException while scheduling job with key: " + jobKey + " message: ", e);
		}

		return false;
	}

	/**
	 * Schedule a job by jobName at given date.
	 */
	@Override
	public boolean scheduleCronJob(
			String jobName, String groupKey, Class<? extends QuartzJobBean> jobClass, Date date, String cronExpression, ZoneId timezone, JobDataMap jobDataMap, boolean isRecoverable) {

		log.info("Request received to schedule CronJob [Name: " + jobName + ", Group: " + groupKey + ", Cron: " + cronExpression + "]");

		String jobKey = jobName;
		String triggerKey = jobName;

		JobDetail jobDetail = JobUtil.createJob(jobClass, true, context, jobKey, groupKey, jobDataMap, isRecoverable);

		log.info("Creating trigger for key: " + jobKey + " at date: " + date);
		Trigger cronTriggerBean = JobUtil.createCronTrigger(triggerKey, date, cronExpression, timezone, SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW);

		boolean jobScheduled = false;

		try {

			if (isJobWithNamePresent(jobName, groupKey)) {
				jobScheduled = updateCronJob(jobName, date, cronExpression, timezone);

			} else {

				Scheduler scheduler = schedulerFactoryBean.getScheduler();
				Date dt = scheduler.scheduleJob(jobDetail, cronTriggerBean);

				jobScheduled = (dt != null);

				log.info("Job with key jobKey: " + jobKey + " and group :" + groupKey + " scheduled successfully for date: " + dt);
			}

		} catch (ObjectAlreadyExistsException e) {
			log.debug("Not an Exception. Job Entry is already present.");
		} catch (SchedulerException e) {
			log.error("SchedulerException while scheduling job with key: " + jobKey + " message :" + e.getMessage());
			log.error("", e);
		}

		log.info("Status of Scheduling Cron Job [Name: " + jobName + ", Group: " + groupKey + ", Cron: " + cronExpression + "] - " + jobScheduled);

		return jobScheduled;
	}

	/**
	 * Update one time scheduled job.
	 */
	@Override
	public boolean updateOneTimeJob(String jobName, Date date) {
		log.info("Request received for updating one time job.");

		String jobKey = jobName;

		log.info("Parameters received for updating one time job - jobKey: " + jobKey + ", date: " + date);
		try {
			Trigger newTrigger = JobUtil.createSingleTrigger(jobKey, date, SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW);

			Date dt = schedulerFactoryBean.getScheduler().rescheduleJob(TriggerKey.triggerKey(jobKey), newTrigger);
			log.info("Trigger associated with jobKey: " + jobKey + " rescheduled successfully for date :" + dt);

			return true;
		} catch (Exception e) {
			log.error("SchedulerException while updating one time job with key: " + jobKey + " message: ", e);
		}

		return false;
	}

	/**
	 * Update scheduled cron job.
	 */
	@Override
	public boolean updateCronJob(String jobName, Date date, String cronExpression, ZoneId timezone) {
		log.info("Request received for updating cron job.");

		String jobKey = jobName;

		log.info("Parameters received for updating cron job: [jobKey: " + jobKey + ", date: " + date + ", cron: " + cronExpression + "]");
		try {
			Trigger newTrigger = JobUtil.createCronTrigger(jobKey, date, cronExpression, timezone, SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW);

			Date dt = schedulerFactoryBean.getScheduler().rescheduleJob(TriggerKey.triggerKey(jobKey), newTrigger);
			log.info("Trigger associated with jobKey: " + jobKey + " rescheduled successfully for date :" + dt);
			return true;
		} catch (Exception e) {
			log.error("SchedulerException while updating cron job with key: " + jobKey, e);
			return false;
		}
	}

	/**
	 * Remove the indicated Trigger from the scheduler.
	 * If the related job does not have any other triggers, and the job is not durable, then the job will also be deleted.
	 */
	@Override
	public boolean unScheduleJob(String jobName) {
		log.info("Request received for Unscheduleding job.");

		String jobKey = jobName;

		TriggerKey tkey = new TriggerKey(jobKey);
		log.info("Parameters received for unscheduling job : key: " + jobKey);
		try {
			boolean status = schedulerFactoryBean.getScheduler().unscheduleJob(tkey);
			log.info("Trigger associated with jobKey: " + jobKey + " unscheduled with status :" + status);
			return status;
		} catch (SchedulerException e) {
			log.error("SchedulerException while unscheduling job with key: " + jobKey + " message: ", e);
			return false;
		}
	}

	/**
	 * Delete the identified Job from the Scheduler - and any associated Triggers.
	 */
	@Override
	public boolean deleteJob(String jobName, String groupKey) {
		log.info("Request received for deleting job.");

		String jobKey = jobName;

		JobKey jkey = new JobKey(jobKey, groupKey);
		log.info("Parameters received for deleting job : jobKey: " + jobKey);

		try {
			boolean status = schedulerFactoryBean.getScheduler().deleteJob(jkey);
			log.info("Job with jobKey: " + jobKey + " deleted with status :" + status);
			return status;
		} catch (SchedulerException e) {
			log.error("SchedulerException while deleting job with key: " + jobKey + " message: ", e);
			return false;
		}
	}

	/**
	 * Pause a job
	 */
	@Override
	public boolean pauseJob(String jobName, String groupKey) {
		log.info("Request received for pausing job.");

		String jobKey = jobName;
		JobKey jkey = new JobKey(jobKey, groupKey);
		log.info("Parameters received for pausing job : jobKey: " + jobKey + ", groupKey :" + groupKey);

		try {
			schedulerFactoryBean.getScheduler().pauseJob(jkey);
			log.info("Job with jobKey: " + jobKey + " paused succesfully.");
			return true;
		} catch (SchedulerException e) {
			log.error("SchedulerException while pausing job with key: " + jobName + " message: ", e);
			return false;
		}
	}

	/**
	 * Resume paused job
	 */
	@Override
	public boolean resumeJob(String jobName, String groupKey) {
		log.info("Request received for resuming job.");

		String jobKey = jobName;

		JobKey jKey = new JobKey(jobKey, groupKey);
		log.info("Parameters received for resuming job : jobKey: " + jobKey);
		try {
			schedulerFactoryBean.getScheduler().resumeJob(jKey);
			log.info("Job with jobKey: " + jobKey + " resumed succesfully.");
			return true;
		} catch (SchedulerException e) {
			log.error("SchedulerException while resuming job with key: " + jobKey + " message: ", e);
			return false;
		}
	}

	/**
	 * Start a job now
	 */
	@Override
	public boolean startJobNow(String jobName, String groupKey) {
		log.info("Request received for starting job now.");

		String jobKey = jobName;

		JobKey jKey = new JobKey(jobKey, groupKey);
		log.info("Parameters received for starting job now : jobKey: " + jobKey);
		try {
			schedulerFactoryBean.getScheduler().triggerJob(jKey);
			log.info("Job with jobKey: " + jobKey + " started now succesfully.");
			return true;
		} catch (SchedulerException e) {
			log.error("SchedulerException while starting job now with key: " + jobKey + " message: ", e);
			return false;
		}
	}

	/**
	 * Check if job is already running
	 */
	@Override
	public boolean isJobRunning(String jobName, String groupKey) {
		log.info("Request received to check if job is running");

		String jobKey = jobName;

		log.info("Parameters received for checking job is running now : jobKey: " + jobKey);
		try {

			List<JobExecutionContext> currentJobs = schedulerFactoryBean.getScheduler().getCurrentlyExecutingJobs();
			if (currentJobs != null) {
				for (JobExecutionContext jobCtx : currentJobs) {
					String jobNameDB = jobCtx.getJobDetail().getKey().getName();
					String groupNameDB = jobCtx.getJobDetail().getKey().getGroup();
					if (jobKey.equalsIgnoreCase(jobNameDB) && groupKey.equalsIgnoreCase(groupNameDB)) {
						return true;
					}
				}
			}
		} catch (SchedulerException e) {
			log.error("SchedulerException while checking job with key: " + jobKey + " is running. error message :", e);
		}

		return false;
	}

	/**
	 * Get all jobs
	 */
	@Override
	public List<Map<String, Object>> getAllJobs() {
		List<Map<String, Object>> list = new ArrayList<>();
		try {
			Scheduler scheduler = schedulerFactoryBean.getScheduler();

			for (String groupName : scheduler.getJobGroupNames()) {
				for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName))) {

					String jobName = jobKey.getName();
					String jobGroup = jobKey.getGroup();


					Map<String, Object> map = new HashMap<>();
					map.put("jobName", jobName);
					map.put("groupName", jobGroup);

					// get job's trigger
					List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
					if(CollectionUtils.isNotEmpty(triggers)) {
						Date scheduleTime = triggers.get(0).getStartTime();
						Date nextFireTime = triggers.get(0).getNextFireTime();
						Date lastFiredTime = triggers.get(0).getPreviousFireTime();
						map.put("scheduleTime", scheduleTime);
						map.put("lastFiredTime", lastFiredTime);
						map.put("nextFireTime", nextFireTime);
					}
					
					

					if (isJobRunning(jobName, jobGroup)) {
						map.put("jobStatus", "RUNNING");
					} else {
						String jobState = getJobState(jobName, jobGroup);
						map.put("jobStatus", jobState);
					}

					list.add(map);
				}

			}
		} catch (SchedulerException e) {
			log.error("SchedulerException while fetching all jobs. error message: ", e);
		}

		return list;
	}

	/**
	 * Check job exist with given name
	 */
	@Override
	public boolean isJobWithNamePresent(String jobName, String groupKey) {
		try {
			JobKey jobKey = new JobKey(jobName, groupKey);
			Scheduler scheduler = schedulerFactoryBean.getScheduler();
			if (scheduler.checkExists(jobKey)) {
				return true;
			}
		} catch (SchedulerException e) {
			log.error("SchedulerException while checking job with name and group exist: ", e);
		}

		return false;
	}

	/**
	 * Get the current state of job
	 */
	@Override
	public String getJobState(String jobName, String groupKey) {
		log.info("JobServiceImpl.getJobState()");

		try {
			JobKey jobKey = new JobKey(jobName, groupKey);

			Scheduler scheduler = schedulerFactoryBean.getScheduler();
			JobDetail jobDetail = scheduler.getJobDetail(jobKey);

			List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobDetail.getKey());

			if (triggers != null && !triggers.isEmpty()) {

				for (Trigger trigger : triggers) {

					TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());

					if (TriggerState.PAUSED.equals(triggerState)) {
						return TriggerState.PAUSED.name();

					} else if (TriggerState.BLOCKED.equals(triggerState)) {
						return TriggerState.BLOCKED.name();

					} else if (TriggerState.COMPLETE.equals(triggerState)) {
						return TriggerState.BLOCKED.name();

					} else if (TriggerState.ERROR.equals(triggerState)) {
						return TriggerState.BLOCKED.name();

					} else if (TriggerState.NONE.equals(triggerState)) {
						return TriggerState.BLOCKED.name();

					} else if (TriggerState.NORMAL.equals(triggerState)) {
						return "SCHEDULED";

					}
				}
			}
		} catch (SchedulerException e) {
			log.error("SchedulerException while checking job with name and group exist:", e);
		}

		return null;
	}

	/**
	 * Stop a job
	 */
	@Override
	public boolean stopJob(String jobName, String groupKey) {
		log.info("JobServiceImpl.stopJob()");
		try {
			String jobKey = jobName;

			Scheduler scheduler = schedulerFactoryBean.getScheduler();
			JobKey jkey = new JobKey(jobKey, groupKey);

			return scheduler.interrupt(jkey);

		} catch (SchedulerException e) {
			log.error("SchedulerException while stopping job. error message :", e);
		}
		return false;
	}
}