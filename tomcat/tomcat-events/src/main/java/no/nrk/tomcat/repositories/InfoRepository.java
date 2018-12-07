package no.nrk.tomcat.repositories;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import no.nrk.tomcat.dtos.Info;

@ApplicationScoped
public class InfoRepository {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(InfoRepository.class);
	
	private Info currentInfo;
	
	@PostConstruct
	public void init() {
		LOGGER.info("Created InfoRepository");
		currentInfo = new Info("tomcat + resteasy and weld, no web.xml");
	}
	
	public Info getInfo() {
		return currentInfo;
	}
	
	public Info addInfo(Info info) {
		currentInfo = info;
		return currentInfo;
	}

}
