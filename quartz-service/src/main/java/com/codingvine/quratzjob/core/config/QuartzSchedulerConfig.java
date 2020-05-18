package com.codingvine.quratzjob.core.config;

import javax.sql.DataSource;

import org.quartz.JobListener;
import org.quartz.TriggerListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import com.codingvine.quratzjob.util.QuatzUtil;

@Configuration
public class QuartzSchedulerConfig {

	@Autowired
	private DataSource dataSource;

	@Autowired
	private Environment environment;

	@Autowired
	private ApplicationContext applicationContext;

	
	@Autowired
	@Qualifier(value = "transactionManager")
	private JpaTransactionManager transactionManager;
	
	@Bean
	public SchedulerFactoryBean schedulerFactoryBean() {

		AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
		jobFactory.setApplicationContext(applicationContext);
		
		
		SchedulerFactoryBean factory = new SchedulerFactoryBean();
		factory.setOverwriteExistingJobs(true);
		factory.setDataSource(dataSource);
		factory.setQuartzProperties(QuatzUtil.getQuartzProperties(environment));
		factory.setJobFactory(jobFactory);
		
		factory.setTransactionManager(transactionManager);
		factory.setGlobalJobListeners(jobsListener());
		
		factory.setGlobalTriggerListeners(globalTriggerListner());
		return factory;
	}

	@Bean
	public TriggerListener globalTriggerListner() {
		return new GlobalTriggerListner();
	}


	@Bean
	public JobListener jobsListener() {
		return new GlobalJobsListener();
	}
	
}