package com.codingvine.quartzjob.jobs;

import java.lang.management.ManagementFactory;
import java.util.Date;

import org.quartz.InterruptableJob;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.UnableToInterruptJobException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.codingvine.quartzjob.service.EmailService;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class CronJob extends QuartzJobBean implements InterruptableJob {

	@Autowired
	private EmailService emailService;

	@Override
	protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		JobKey key = jobExecutionContext.getJobDetail().getKey();
		log.info(ManagementFactory.getRuntimeMXBean().getName() + " : " + new Date());
		log.info("Cron Job started with key :" + key.getName() + ", Group :" + key.getGroup() + " , Thread Name :" + Thread.currentThread().getName() + " ,Time now :" + new Date());


		// *********** For retrieving stored key-value pairs ***********/
		JobDataMap jobDataMap = jobExecutionContext.getMergedJobDataMap();
		String subject = jobDataMap.getString("subject");
		String body = jobDataMap.getString("body");
		String recipientEmail = jobDataMap.getString("email");



		emailService.sendMail(recipientEmail, subject, body);

	}

	@Override
	public void interrupt() throws UnableToInterruptJobException {
		log.info("Stopping thread... ");
	}

}