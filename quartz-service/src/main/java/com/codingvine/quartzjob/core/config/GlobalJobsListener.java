package com.codingvine.quartzjob.core.config;

import java.util.Objects;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class GlobalJobsListener implements JobListener {

	@Override
	public String getName() {
		return "globalJob";
	}

	// Run this if job is about to be executed.
	@Override
	public void jobToBeExecuted(JobExecutionContext context) {
		log.debug("JobsListener.jobToBeExecuted()");
		String jobName = context.getJobDetail().getKey().toString();
		log.info("Job : " + jobName + " is going to start...");
	}

	@Override
	public void jobExecutionVetoed(JobExecutionContext context) {
		log.debug("JobsListener.jobExecutionVetoed()");
	}

	
	
	//Run this after job has been executed
	@Override
	public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
		log.debug("JobsListener.jobWasExecuted()");
		
		String jobName = context.getJobDetail().getKey().toString();
		log.info("Job : " + jobName + " ...Fired Instance Id: "+context.getFireInstanceId()+"is finished...");

		if (Objects.nonNull(jobException) && !jobException.getMessage().equals("")) {
			log.error("Exception thrown by: " + jobName
				+ " Exception: " + jobException.getMessage());
		}
		
		
	}

}