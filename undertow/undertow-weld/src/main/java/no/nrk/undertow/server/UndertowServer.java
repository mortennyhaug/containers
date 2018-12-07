package no.nrk.undertow.server;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.ServletException;

import org.jboss.resteasy.cdi.CdiInjectorFactory;
import org.jboss.resteasy.plugins.server.undertow.UndertowJaxrsServer;
import org.jboss.resteasy.spi.ResteasyDeployment;
import org.jboss.weld.environment.servlet.Listener;

import io.undertow.Undertow;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.DeploymentInfo;
import no.nrk.undertow.rs.core.ApiRestApplication;
import no.nrk.undertow.rs.resources.UndertowRestResource;

public class UndertowServer {
	
    public static void main(String[] args) throws ServletException {
    	UndertowJaxrsServer server = new UndertowJaxrsServer()
    			.start(Undertow.builder().addHttpListener(8080, "localhost"));
    	
        ResteasyDeployment rd = new ResteasyDeployment();
        rd.setInjectorFactoryClass(CdiInjectorFactory.class.getName());
        rd.setApplicationClass(ApiRestApplication.class.getName());
        rd.setResourceClasses(Stream.of(UndertowRestResource.class.getName())
				.collect(Collectors.toList()));
    	
        DeploymentInfo di = server.undertowDeployment(rd, "/api")
                .setClassLoader(UndertowServer.class.getClassLoader())
                .setContextPath("/")
                .setDeploymentName("Undertow Application")
                .addListeners(Servlets.listener(Listener.class));
        
        server.deploy(di);
    }

}
