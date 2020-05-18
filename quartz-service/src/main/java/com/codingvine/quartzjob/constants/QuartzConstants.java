/**
 * 
 */
package com.codingvine.quartzjob.constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public class QuartzConstants {

	public static final String INSTANCE_NAME = "org.quartz.scheduler.instanceName";
	public static final String INSTANCE_ID = "org.quartz.scheduler.instanceId";
	public static final String THREAD_COUNT = "org.quartz.threadPool.threadCount";
	public static final String JOB_STORE_CLASS = "org.quartz.jobStore.class";
	public static final String JOB_STORE_DIVER_DELEGATE_CLASS = "org.quartz.jobStore.driverDelegateClass";
	public static final String JOB_STORE_USE_PROPERTIES = "org.quartz.jobStore.useProperties";
	public static final String JOB_STORE_MISFIRE_THRESHOLD = "org.quartz.jobStore.misfireThreshold";
	public static final String JOB_STORE_TABLE_PREFIX = "org.quartz.jobStore.tablePrefix";
	public static final String JOB_STORE_IS_CLUSTERED = "org.quartz.jobStore.isClustered";
	public static final String JOB_STORE_CLUSTER_CHECKIN_INTERVAL = "org.quartz.jobStore.clusterCheckinInterval";
	public static final String SHUTDOWN_HOOK_CLASS = "org.quartz.plugin.shutdownHook.class";
	public static final String SHUTDOWN_HOOK_CLEAN_SHUTDOWN = "org.quartz.plugin.shutdownHook.cleanShutdown";

	public static final String WRAP_JOB_EXECUTION_IN_USER_TRANSACTION="org.quartz.scheduler.wrapJobExecutionInUserTransaction";


	public static final String TRIGGER_HISTORY_CLASS = "org.quartz.plugin.triggHistory.class";

	public static final String TRIGGER_HISTORY_FIRED_MESSAGE = "org.quartz.plugin.triggHistory.triggerFiredMessage";
	public static final String TRIGGER_HISTORY_COMPLETE_MESSAGE = "org.quartz.plugin.triggHistory.triggerCompleteMessage";
	public static final String TRIGGER_HISTORY_MIS_FIRED_MESSAGE = "org.quartz.plugin.triggHistory.triggerMisfiredMessage"; 
}