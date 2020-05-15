/**
 * 
 */
package com.codingvine.quratzjob.util;

import java.util.Properties;

import org.springframework.core.env.Environment;

import com.codingvine.quratzjob.constants.QuartzConstants;

import lombok.experimental.UtilityClass;

/**
 * 
 */
@UtilityClass
public class QuatzUtil {

	public static Properties getQuartzProperties(Environment environment) {

		Properties properties = new Properties();

		properties.put(QuartzConstants.INSTANCE_ID, environment.getRequiredProperty(QuartzConstants.INSTANCE_ID));
		properties.put(QuartzConstants.INSTANCE_NAME, environment.getRequiredProperty(QuartzConstants.INSTANCE_NAME));
		properties.put(QuartzConstants.THREAD_COUNT, environment.getRequiredProperty(QuartzConstants.THREAD_COUNT));
		properties.put(QuartzConstants.JOB_STORE_CLASS, environment.getRequiredProperty(QuartzConstants.JOB_STORE_CLASS));
		properties.put(QuartzConstants.JOB_STORE_DIVER_DELEGATE_CLASS, environment.getRequiredProperty(QuartzConstants.JOB_STORE_DIVER_DELEGATE_CLASS));
		properties.put(QuartzConstants.JOB_STORE_USE_PROPERTIES, environment.getRequiredProperty(QuartzConstants.JOB_STORE_USE_PROPERTIES));
		properties.put(QuartzConstants.JOB_STORE_MISFIRE_THRESHOLD, environment.getRequiredProperty(QuartzConstants.JOB_STORE_MISFIRE_THRESHOLD));
		properties.put(QuartzConstants.JOB_STORE_TABLE_PREFIX, environment.getRequiredProperty(QuartzConstants.JOB_STORE_TABLE_PREFIX));
		properties.put(QuartzConstants.JOB_STORE_IS_CLUSTERED, environment.getRequiredProperty(QuartzConstants.JOB_STORE_IS_CLUSTERED));
		properties.put(QuartzConstants.JOB_STORE_CLUSTER_CHECKIN_INTERVAL, environment.getRequiredProperty(QuartzConstants.JOB_STORE_CLUSTER_CHECKIN_INTERVAL));
		properties.put(QuartzConstants.SHUTDOWN_HOOK_CLASS, environment.getRequiredProperty(QuartzConstants.SHUTDOWN_HOOK_CLASS));
		properties.put(QuartzConstants.SHUTDOWN_HOOK_CLEAN_SHUTDOWN, environment.getRequiredProperty(QuartzConstants.SHUTDOWN_HOOK_CLEAN_SHUTDOWN));

		
		properties.put(QuartzConstants.WRAP_JOB_EXECUTION_IN_USER_TRANSACTION, environment.getRequiredProperty(QuartzConstants.WRAP_JOB_EXECUTION_IN_USER_TRANSACTION));
		
		return properties;
	}

}