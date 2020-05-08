# bootstrap-project

Steps to convert bootstrap project to your service project before importing project in your IDE

1. Clone or pull `bootstrap-project` on your system

2. Copy the bootstrap project to new folder for your application using -

	`cp -R bootstrap-project your_service-project`

3. Now go to `your_service-project` using `cd your_service-project` and run following commands -
```
	git init
	
	Edit the file vim .git/config and update the remote URL to
	git remote add origin https://github.com/user/your_service.git
```
	
4. Edit the `pom.xml`
	
	a. Change `codingvine-parent` to `your_service-parent` in `artifactId` and `name` (top of file)
	b. Change `bootstrap` to `your_service` in `module` (at the center of file)
	c. Change profile id from `bootstrap-project` to `your_service-project` (at the bottom of file)
	d. Save and exit
	
5. Now rename the bootstrap folder to your_service folder using -

	`mv bootstrap your_service`
	
6. Go to renamed folder `your_service` and edit `pom.xml`
```
	a. Change `bootstrap` to `your_service` in `artifactId` and `name` and update the description as well (top of file)
	b. Change `codingvine-parent` to `your_service-parent` in `artifactId` in parent tag (top of file)
	c. Change `bootstrap` to `your_service` in `finalName` in `build` plugin (at the bottom of file)
```

7. Import the `your_service-project` in your IDE

8. Rename the package `com.codingvine.bootstrap` to `com.codingvine.your_project` and organize imports

9. Rename `BootstrapApplication.java` to `YourAppNameApplication.java`

10. Change basePackages for repository and entity in `DatabaseConfig.java`

11. Change controller scan in `SwaggerConfig.java`

12. Update `context-path`, `application-name`, access log file name, database name, kafka client id, kafka group id in `application.properties`

13. Update log file name in `log4j2.xml` 

14. Commit the code.

