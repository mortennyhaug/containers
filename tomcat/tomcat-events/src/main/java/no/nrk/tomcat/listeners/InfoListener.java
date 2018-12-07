package no.nrk.tomcat.listeners;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import no.nrk.tomcat.dtos.Info;
import no.nrk.tomcat.repositories.InfoRepository;

public class InfoListener {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(InfoListener.class);

	@Inject
	private InfoRepository repository;
	
	@PostConstruct
	public void init() {
		LOGGER.info("Created InfoListener");
	}
	
	public void onAnyInfoEvent(@Observes Info info) {
		LOGGER.info("Observing info \"{}\"", info.getInfo());
		LOGGER.info("Adding info to repository");
		repository.addInfo(info);
	}
	
}
