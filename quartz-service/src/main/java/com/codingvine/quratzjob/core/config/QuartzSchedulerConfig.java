package com.codingvine.quratzjob.core.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
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

	@Bean
	public SchedulerFactoryBean schedulerFactoryBean() {

		SchedulerFactoryBean factory = new SchedulerFactoryBean();
		factory.setOverwriteExistingJobs(true);
		factory.setDataSource(dataSource);
		factory.setQuartzProperties(QuatzUtil.getQuartzProperties(environment));

		AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
		jobFactory.setApplicationContext(applicationContext);
		factory.setJobFactory(jobFactory);

		return factory;
	}

}