# Maven
## Preparations

To download dependencies and build project:

    mvn install
    
## Usage

This deploys as root app to a tomcat server running on:

	http://localhost:8080
	
To deploy:

    mvn tomcat7:deploy
    
To redeploy (replace existing):

    mvn tomcat7:redeploy
    
To undeploy:

    mvn tomcat7:undeploy
    
A sample REST service is available at:

	curl -X GET http://localhost:8080/api/info
	
# Features

* REST services
* JSON object mapping
* Dependency injection
* No web.xml

# Modules

* ReastEasy 
* Jackson
* Weld
