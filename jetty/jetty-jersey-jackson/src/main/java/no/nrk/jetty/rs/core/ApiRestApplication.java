package no.nrk.jetty.rs.core;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("/api")
public class ApiRestApplication extends ResourceConfig {

	public ApiRestApplication() {
		packages("no.nrk.jetty.rs");
	}

}
