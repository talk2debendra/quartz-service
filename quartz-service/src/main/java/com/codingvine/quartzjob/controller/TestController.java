/**
 * 
 */
package com.codingvine.quartzjob.controller;

import java.time.ZonedDateTime;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codingvine.quratzjob.jobs.examples.Examples;
import com.codingvine.quratzjob.jobs.examples.JobDto;
import com.codingvine.quratzjob.jobs.examples.ScheduleEmailResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 *
 **/
@Api
@RestController
@RequestMapping("/test")
public class TestController {
	
	
	
	@GetMapping
	public ResponseEntity<String> test(){
		return ResponseEntity.ok("Working.......");
	}

	@Autowired
	Examples examples;
	
	@ApiOperation("Retrievs all scheduled jobs")
	@GetMapping("/jobs")
	public ResponseEntity getAllJobs(){
		return ResponseEntity.ok(examples.getAllJobs());
	}
	
	
	@PostMapping("/schedule/single")
	public ResponseEntity scheduleOneTimeJob(@RequestBody JobDto dto) {
		
		ZonedDateTime dateTime = ZonedDateTime.of(dto.getJobScheduleTime(), dto.getTimeZone());
		if(dateTime.isBefore(ZonedDateTime.now())) {
            ScheduleEmailResponse scheduleEmailResponse = new ScheduleEmailResponse(false,
                    "dateTime must be after current time");
            return ResponseEntity.badRequest().body(scheduleEmailResponse);
        }
		
		return ResponseEntity.ok(examples.schedule(dto.getJobName(),
				dto.getGroupName(),  Date.from(dateTime.toInstant()),
				dto.getCronExpression(), dto.getTimeZone()));
	}
	
	
	@PostMapping("/schedule/cron")
	public ResponseEntity scheduleCronJob(@RequestBody JobDto dto) {
		
		ZonedDateTime dateTime = ZonedDateTime.of(dto.getJobScheduleTime(), dto.getTimeZone());
		if(dateTime.isBefore(ZonedDateTime.now())) {
            ScheduleEmailResponse scheduleEmailResponse = new ScheduleEmailResponse(false,
                    "dateTime must be after current time");
            return ResponseEntity.badRequest().body(scheduleEmailResponse);
        }

		return ResponseEntity.ok(examples.schedule(dto.getJobName(),
				dto.getGroupName(), Date.from(dateTime.toInstant()),
				dto.getCronExpression(), dto.getTimeZone()));
	}
	
	
	@GetMapping("/job/unschedule")
	public ResponseEntity unscheduleJob(@RequestParam(required = true) String jobName) {
		return ResponseEntity.ok(examples.unschedule(jobName));
	}
	
	
	@GetMapping("/job/pause")
	public ResponseEntity pauseJob(@RequestParam(required = true) String jobName, @RequestParam(required = true) String groupName) {
		return ResponseEntity.ok(examples.pause(jobName,groupName));
	}
	
	
	@GetMapping("/job/start")
	public ResponseEntity startJob(@RequestParam(required = true) String jobName, @RequestParam(required = true) String groupName) {
		return ResponseEntity.ok(examples.startJobNow(jobName, groupName));
	}
	
	
	@GetMapping("/job/stop")
	public ResponseEntity stopJob(@RequestParam(required = true) String jobName, @RequestParam(required = true) String groupName) {
		return ResponseEntity.ok(examples.stopJob(jobName, groupName));
	}
}