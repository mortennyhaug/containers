package no.nrk.jetty.rs.core;

import javax.annotation.PostConstruct;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationPath("/api")
public class ApiRestApplication extends Application {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ApiRestApplication.class);
	
	@PostConstruct
	public void init() {
		LOGGER.info("REST API initialized");
	}

}
