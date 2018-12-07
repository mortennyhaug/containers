package no.nrk.tomcat.rs.core;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import no.nrk.tomcat.services.InfoService;

@ApplicationPath("/api")
public class ApiRestApplication extends Application {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ApiRestApplication.class);
	
	@Inject
	private InfoService service;
	
	@PostConstruct
	public void init() {
		LOGGER.info("Created ApiRestApplication");
		
		LOGGER.info("Starting info service");
		service.startup();
	}

}
