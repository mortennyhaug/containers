# Preparations
## Maven

To download dependencies and build project:

    mvn install
    
# Usage

This starts jetty and deploys as root app: `http://localhost:8080`
	
To start jetty:

    mvn jetty:run
    
A sample REST service is available at: `curl -X GET http://localhost:8080/api/info`
	
# Features

* REST services
* JSON object mapping
* Dependency injection
* No web.xml

## Modules

* ReastEasy 
* Jackson
* Weld

## Port and context path

To start jetty on a different port, or deploy the application under a different context path, add the following configuration to the maven jetty plugin:

	<plugin>
		<groupId>org.eclipse.jetty</groupId>
		<artifactId>jetty-maven-plugin</artifactId>
		<version>${jetty-maven-plugin.version}</version>
		<configuration>
			<webApp>
				<contextPath>/jetty</contextPath>
			</webApp>
			<httpConnector>
				<port>8081</port>
			</httpConnector>
		</configuration>
	</plugin>
