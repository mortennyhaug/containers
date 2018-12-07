# Preparations
## Tomcat DB driver

If you don't want to include the DB driver in the project, you need to make sure it is on the classpath of the tomcat server.

Download the H2 driver from: `http://mvnrepository.com/artifact/com.h2database/h2`

Or use maven dependency:

	<dependency>
	   <groupId>com.h2database</groupId>
	   <artifactId>h2</artifactId>
	   <version>${h2.version}</version>
	</dependency>

Copy `h2.jar` to `CATALINA_HOME/lib` 

## Maven

To download dependencies and build project:

    mvn install
    
# Usage

This deploys as root app to a tomcat server running on: `http://localhost:8080`
	
To deploy:

    mvn tomcat7:deploy
    
To redeploy (replace existing):

    mvn tomcat7:redeploy
    
To undeploy:

    mvn tomcat7:undeploy
    
A sample REST service is available at: `curl -X GET http://localhost:8080/api/info`
	
# Features

* DB integration
* REST services
* JSON object mapping
* Dependency injection
* No web.xml

# Modules

* ReastEasy 
* Jackson
* Weld
