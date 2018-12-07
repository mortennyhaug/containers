package no.nrk.tomcat.services;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import no.nrk.tomcat.dtos.Info;

@ApplicationScoped
public class InfoService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(InfoService.class);
	
	@Inject
	private Event<Info> infoEvent;
	
	@PostConstruct
	public void init() {
		LOGGER.info("Created InfoService");
	}
	
	public void startup() {
		LOGGER.info("Starting service");

	}

	public void sendFutureInfo(Info info) {
		LOGGER.info("Fire info event in the future");
		ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
		
		infoEvent.fire(info);
		
		executorService.schedule(
				() -> infoEvent.fire(info), 
				1, TimeUnit.SECONDS);
		
	}
	
}
