package no.nrk.undertow.rs.resources;

import static javax.ws.rs.core.Response.ok;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import no.nrk.undertow.json.JsonInfo;
import no.nrk.undertow.repositories.InfoRepository;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class UndertowRestResource {
	
	@Inject
	private InfoRepository repository;
	
	@GET
	@Path("info")
	public Response info() {
		return ok().entity(JsonInfo.from(repository.getInfo()))
				.build();
	}

}
