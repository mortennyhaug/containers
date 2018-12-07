package no.nrk.undertow.integration;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.jboss.resteasy.cdi.CdiInjectorFactory;
import org.jboss.resteasy.plugins.server.undertow.UndertowJaxrsServer;
import org.jboss.resteasy.spi.ResteasyDeployment;
import org.jboss.resteasy.test.TestPortProvider;
import org.jboss.weld.environment.servlet.Listener;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import io.undertow.Undertow;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.DeploymentInfo;
import no.nrk.undertow.json.JsonInfo;
import no.nrk.undertow.rs.core.ApiRestApplication;
import no.nrk.undertow.rs.resources.UndertowRestResource;

public class UndertowRestResourceIT {
	
	private static UndertowJaxrsServer server;
	
	private Client client;
	
	@BeforeClass
	public static void init() throws Exception {
		server = new UndertowJaxrsServer()
				.start(Undertow.builder().addHttpListener(TestPortProvider.getPort(), TestPortProvider.getHost()));

        ResteasyDeployment rd = new ResteasyDeployment();
        rd.setInjectorFactoryClass(CdiInjectorFactory.class.getName());
        rd.setApplicationClass(ApiRestApplication.class.getName());
        rd.setResourceClasses(Stream.of(UndertowRestResource.class.getName())
				.collect(Collectors.toList()));
    	
        DeploymentInfo di = server.undertowDeployment(rd, "/api")
                .setClassLoader(UndertowRestResourceIT.class.getClassLoader())
                .setContextPath("/")
                .setDeploymentName("Testing resources")
                .addListeners(Servlets.listener(Listener.class));
        
        server.deploy(di);
	}
	
	@AfterClass
	public static void stop() throws Exception {
		server.stop();
	}
	
	@Before
	public void before() throws Exception {
		client = ClientBuilder.newClient();
	}

	@After
	public void after() throws Exception {
		client.close();
	}
	
	@Test
	public void givenInfoExists_whenIGetTheInfo_thenReturnTheInfoAsJson() throws Exception {
		//Arrange
		String expected = "fake info repository";
		
		//Act
		Response response = client.target(TestPortProvider.generateURL("/api/info"))
				.request()
				.get();

		//Assert
		assertThat(response.getStatus()).isEqualTo(Status.OK.getStatusCode());
		JsonInfo actual = response.readEntity(JsonInfo.class);
		assertThat(actual.info).isEqualTo(expected);
	}
	
}
