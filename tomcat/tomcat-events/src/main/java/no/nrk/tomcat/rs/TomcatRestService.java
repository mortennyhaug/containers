package no.nrk.tomcat.rs;

import static javax.ws.rs.core.Response.noContent;
import static javax.ws.rs.core.Response.ok;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import no.nrk.tomcat.dtos.Info;
import no.nrk.tomcat.json.JsonInfo;
import no.nrk.tomcat.repositories.InfoRepository;
import no.nrk.tomcat.services.InfoService;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class TomcatRestService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TomcatRestService.class);
	
	@Inject
	private InfoRepository repository;
	
	@Inject
	private Event<Info> infoEvent;
	
	@Inject
	private InfoService infoService;
	
	@PostConstruct
	public void init() {
		LOGGER.info("Created TomcatRestService");
	}
	
	@GET
	@Path("info")
	public Response get() {
		LOGGER.info("Fetching current info from repository");

		return ok().entity(JsonInfo.fromInfo(repository.getInfo()))
				.build();
	}
	
	@POST
	@Path("info")
	public Response add(JsonInfo json) {
		LOGGER.info("Fire info event to add new info");

		Info info = JsonInfo.toInfo(json);
		infoEvent.fire(info);
		
		return noContent().build();
	}
	
	@POST
	@Path("info/future")
	public Response addFuture(JsonInfo json) {
		LOGGER.info("Fire info event in the future");
		ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
		
		Info info = JsonInfo.toInfo(json);
		infoEvent.fire(info);
		
		executorService.schedule(
				() -> infoEvent.fire(new Info("new future info")), 
				1, TimeUnit.SECONDS);
		
		return noContent().build();
	}

	@POST
	@Path("info/service")
	public Response addService(JsonInfo json) {
		LOGGER.info("Let service schedule future event");
		infoService.sendFutureInfo(JsonInfo.toInfo(json));
		
		return noContent().build();
	}

}
