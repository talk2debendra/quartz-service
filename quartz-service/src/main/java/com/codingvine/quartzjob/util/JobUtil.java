package com.codingvine.quartzjob.util;

import java.text.ParseException;
import java.time.ZoneId;
import java.util.Date;

import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

import com.codingvine.quartzjob.core.config.PersistableCronTriggerFactoryBean;

import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;

@Log4j2
@UtilityClass
public class JobUtil {

	/**
	 * Create Quartz Job.
	 * 
	 * @param jobClass
	 *            Class whose executeInternal() method needs to be called.
	 * @param isDurable
	 *            Job needs to be persisted even after completion. if true, job will be persisted, not otherwise.
	 * @param context
	 *            Spring application context.
	 * @param jobName
	 *            Job name.
	 * @param jobGroup
	 *            Job group.
	 * 
	 * @return JobDetail object
	 */
	public static JobDetail createJob(
			Class<? extends QuartzJobBean> jobClass,
			boolean isDurable,
			ApplicationContext context,
			String jobName,
			String jobGroup,
			JobDataMap jobDataMap,
			boolean isRecoverable) {

		JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();

		factoryBean.setJobClass(jobClass);
		factoryBean.setDurability(isDurable);
		factoryBean.setApplicationContext(context);
		factoryBean.setName(jobName);
		factoryBean.setGroup(jobGroup);
		factoryBean.setRequestsRecovery(isRecoverable);
		
		factoryBean.setJobDataMap(jobDataMap);

		factoryBean.afterPropertiesSet();

		return factoryBean.getObject();
	}

	/**
	 * Create cron trigger.
	 * 
	 * @param triggerName
	 *            Trigger name.
	 * @param startTime
	 *            Trigger start time.
	 * @param cronExpression
	 *            Cron expression.
	 * @param misFireInstruction
	 *            Misfire instruction (what to do in case of misfire happens).
	 * 
	 * @return Trigger
	 */
	public static Trigger createCronTrigger(String triggerName, Date startTime, String cronExpression, ZoneId timezone, int misFireInstruction) {

		PersistableCronTriggerFactoryBean factoryBean = new PersistableCronTriggerFactoryBean();

		factoryBean.setName(triggerName);
		factoryBean.setStartTime(startTime);
		factoryBean.setCronExpression(cronExpression);
		//factoryBean.setTimeZone(TimeZone.getTimeZone(timezone));
		factoryBean.setMisfireInstruction(misFireInstruction);

		try {
			factoryBean.afterPropertiesSet();
		} catch (ParseException e) {
			log.error("", e);
		}

		return factoryBean.getObject();
	}

	/**
	 * Create a Single trigger.
	 * 
	 * @param triggerName
	 *            Trigger name.
	 * @param startTime
	 *            Trigger start time.
	 * @param misFireInstruction
	 *            Misfire instruction (what to do in case of misfire happens).
	 * 
	 * @return Trigger
	 */
	public static Trigger createSingleTrigger(String triggerName, Date startTime, int misFireInstruction) {
		SimpleTriggerFactoryBean factoryBean = new SimpleTriggerFactoryBean();

		factoryBean.setName(triggerName);
		factoryBean.setStartTime(startTime);
		factoryBean.setMisfireInstruction(misFireInstruction);
		factoryBean.setRepeatCount(0);
		factoryBean.afterPropertiesSet();

		return factoryBean.getObject();
	}

}