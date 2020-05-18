package com.codingvine.quartzjob.jobs.examples;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ScheduleEmailRequest implements  Serializable{
	private static final long serialVersionUID = 1L;

	@Email
    @NotEmpty
    private String email;

    @NotEmpty
    private String subject;

    @NotEmpty
    private String body;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime dateTime;

    private ZoneId timeZone;

}