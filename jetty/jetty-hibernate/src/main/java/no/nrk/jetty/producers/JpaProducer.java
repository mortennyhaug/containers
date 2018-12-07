package no.nrk.jetty.producers;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@ApplicationScoped
public class JpaProducer {
	
	private static final String TEST_PU = "testPU";
	
	private EntityManagerFactory emf;
	
	@PostConstruct
	public void init() {
		emf = Persistence.createEntityManagerFactory(TEST_PU);
	}
	
	@PreDestroy
	public void destroy() {
		emf.close();
	}
	
	@Produces
	public EntityManager getEm() {
		return emf.createEntityManager();
	}
	
	public void closeEm(@Disposes EntityManager em) {
		em.close();
	}

}
