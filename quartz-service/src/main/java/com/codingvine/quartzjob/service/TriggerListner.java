package com.codingvine.quartzjob.service;

import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.Trigger.CompletedExecutionInstruction;
import org.quartz.TriggerListener;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class TriggerListner implements TriggerListener {

	@Override
	public String getName() {
		return "globalTrigger";
	}

	@Override
	public void triggerFired(Trigger trigger, JobExecutionContext context) {
		log.debug("TriggerListner.triggerFired()");
	}

	@Override
	public boolean vetoJobExecution(Trigger trigger, JobExecutionContext context) {
		log.debug("TriggerListner.vetoJobExecution()");
		return false;
	}

	@Override
	public void triggerMisfired(Trigger trigger) {
		log.debug("TriggerListner.triggerMisfired()");
		String jobName = trigger.getJobKey().getName();
		log.debug("Job name: " + jobName + " is misfired");

	}

	@Override
	public void triggerComplete(Trigger trigger, JobExecutionContext context, CompletedExecutionInstruction triggerInstructionCode) {
		log.debug("TriggerListner.triggerComplete()");
	}
}