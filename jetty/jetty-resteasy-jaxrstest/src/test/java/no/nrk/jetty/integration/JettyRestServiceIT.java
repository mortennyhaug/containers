package no.nrk.jetty.integration;

import static org.fest.assertions.Assertions.assertThat;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
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
import no.nrk.jetty.json.JsonInfo;
import no.nrk.jetty.rs.core.ApiRestApplication;
import no.nrk.jetty.rs.resources.JettyRestResource;

public class JettyRestServiceIT {
	
	private static UndertowJaxrsServer server;
	
	@BeforeClass
	public static void init() {
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
	public void info_whenCalled_thenReturnOkResponseWithInfo() throws Exception {
		//Arrange
		JettyRestResource resource = new JettyRestResource();
		ResteasyDeployment rd = new ResteasyDeployment();
		rd.setApplication(new ApiRestApplication());
		rd.getResources().add(resource);
		
		DeploymentInfo di = server.undertowDeployment(rd, "/api")
				.setClassLoader(getClass().getClassLoader())
				.setContextPath("/")
				.setDeploymentName("jetty-resteasy");
		
		server.deploy(di);
		
		//Act
		Response response = client.target(TestPortProvider.generateURL("/api/info")).request().get();

		//Assert
		assertThat(response.getStatus()).isEqualTo(Status.OK.getStatusCode());
		JsonInfo json = response.readEntity(JsonInfo.class);
		assertThat(json.info).isEqualTo("jaxrstest");
	}
	
}
