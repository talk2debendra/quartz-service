# quartz-service

A microservice which illustrates the use of Quartz in spring boot.
- You can register a simple Job, a CRON job to send email
- You can dynamically pause,stop,start the quartz scheduler
- Uses JdbcStore
-http://www.quartz-scheduler.org/documentation/2.4.0-SNAPSHOT/configuration.html

- Run the quartz.sql file
- Run the application
- http://localhost:8090/quartz-service/swagger-ui.html#/


EX : to register a CRON for every 1 minute
http://localhost:8090/quartz-service/test/schedule/cron
-Payload
{
  "cronExpression": "0 0/1 * 1/1 * ? *",
  "groupName": "cornJob4Every1Minutes",
  "jobName": "cornJob4Every1Minutes",
  "jobScheduleTime": "2020-05-15 17:40:00",
  "timeZone": "Asia/Kolkata"
}

# Depedency modules
import the following module to your classpath
- https://github.com/talk2debendra/core-utils.git

# Reference tutorial
https://flylib.com/books/en/2.65.1/
