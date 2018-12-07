package no.nrk.jetty.integration;

import static javax.ws.rs.core.Response.ok;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.reflect.core.Reflection.field;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.jboss.resteasy.plugins.server.undertow.UndertowJaxrsServer;
import org.jboss.resteasy.spi.ResteasyDeployment;
import org.jboss.resteasy.test.TestPortProvider;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import io.undertow.servlet.api.DeploymentInfo;

public class ResteasyUndertowTest {
	
	private static UndertowJaxrsServer server;
	
	@ApplicationPath("/api")
	public static class MyApp extends Application {
		@Override
		public Set<Class<?>> getClasses() {
			return Stream.of(Resource.class)
					.collect(Collectors.toSet());
		}
	}

	@Path("/test")
	public static class Resource {
		
		private String entity = "hello world";
		
		@GET
		@Produces("text/plain")
		public Response get() {
			return ok().entity(entity).build();
		}

		@Override
		public String toString() {
			return "Resource [entity=" + entity + "]";
		}
	}
	
	@BeforeClass
	public static void init() throws Exception {
		server = new UndertowJaxrsServer().start();
	}

	@AfterClass
	public static void stop() throws Exception {
		server.stop();
	}
	
	private Client client;
	
	@Before
	public void before() throws Exception {
		client = ClientBuilder.newClient();
	}
	
	@After
	public void after() throws Exception {
		client.close();
	}


	@Test
	public void testApplicationPath() throws Exception {
		server.deploy(MyApp.class);
		Response response = client.target(TestPortProvider.generateURL("/api/test")).request().get();
		assertThat(response.getStatus()).isEqualTo(Status.OK.getStatusCode());
		assertThat(response.readEntity(String.class)).isEqualTo("hello world");
	}

	@Test
	public void testApplicationContext() throws Exception {
		server.deploy(MyApp.class, "/root");
		Response response = client.target(TestPortProvider.generateURL("/root/test")).request().get();
		assertThat(response.getStatus()).isEqualTo(Status.OK.getStatusCode());
		assertThat(response.readEntity(String.class)).isEqualTo("hello world");
	}

	@Test
	public void testDeploymentInfo() throws Exception {
		DeploymentInfo di = server.undertowDeployment(MyApp.class);
		di.setContextPath("/di");
		di.setDeploymentName("DI");
		
		server.deploy(di);
		Response response = client.target(TestPortProvider.generateURL("/di/api/test")).request().get();
		assertThat(response.getStatus()).isEqualTo(Status.OK.getStatusCode());
		assertThat(response.readEntity(String.class)).isEqualTo("hello world");
	}

	@Test
	public void testRestEasyDeployment() throws Exception {
		Resource resource = new Resource();
		field("entity").ofType(String.class).in(resource).set("Bienvenido");
		
		ResteasyDeployment rd = new ResteasyDeployment();
		rd.setApplication(new MyApp());
		rd.getResources().add(resource);
		
		System.out.println("Resources: " + rd.getResources().stream()
				.map(r -> r.toString()).collect(Collectors.toList()));
		
		DeploymentInfo di = server.undertowDeployment(rd, "/api");
		di.setClassLoader(getClass().getClassLoader());
		di.setContextPath("/rd");
		di.setDeploymentName("DI");
		
		server.deploy(di);
		Response response = client.target(TestPortProvider.generateURL("/rd/api/test")).request().get();
		assertThat(response.getStatus()).isEqualTo(Status.OK.getStatusCode());
		assertThat(response.readEntity(String.class)).isEqualTo("Bienvenido");
	}

}
