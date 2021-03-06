package no.nrk.jetty.rs.resources;

import static javax.ws.rs.core.Response.ok;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import no.nrk.jetty.json.JsonInfo;
import no.nrk.jetty.repositories.InfoRepository;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class JettyRestResource {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(JettyRestResource.class);
	
	@Inject
	private InfoRepository repository;
	
	@GET
	@Path("info")
	public Response info() {
		LOGGER.debug("Requesting info from system");
		
		return ok().entity(JsonInfo.from(repository.getInfo()))
				.build();
	}

}
