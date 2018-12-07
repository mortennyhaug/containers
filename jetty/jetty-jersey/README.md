# Maven
## Preparations

To download dependencies and build project:

    mvn install
    
## Usage

To start jetty:

    mvn jetty:run
    
This starts jetty on:

	http://localhost:8080
	
A sample REST service is available at:

	curl -X GET http://localhost:8080/api/info
	
## Features

* REST services
* JSON object mapping
* Dependency injection
* No web.xml

## Modules

* Jersey 
* Moxy
* Weld
