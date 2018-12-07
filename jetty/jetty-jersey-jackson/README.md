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
* Jackson
* Weld

## Notes

To get jackson working with jersey, have the application extend `org.glassfish.jersey.server.ReourceConfig` instead of `javax.ws.rs.core.Application` and register the package name where the rest resources are located. The package scan is recursive.

	@ApplicationPath("/api")
	public class ApiRestApplication extends ResourceConfig {
		public ApiRestApplication() {
			packages("no.nrk.jetty.rs");
		}
	}


