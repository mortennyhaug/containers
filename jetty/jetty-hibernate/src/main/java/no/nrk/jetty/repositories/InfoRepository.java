package no.nrk.jetty.repositories;

import static no.nrk.jetty.entities.InfoEntity.anInfoEntity;
import static no.nrk.jetty.entities.InfoEntity.selectInfoByKey;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import no.nrk.jetty.entities.InfoEntity;

@ApplicationScoped
public class InfoRepository {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(InfoRepository.class);
	
	@Inject
	private EntityManager em;
	
	@PostConstruct
	public void init() {
		LOGGER.info("Info repository initialized");
		
		em.getTransaction().begin();
		try {
			InfoEntity entity = selectInfoByKey("info");
			if (entity == null) {
				em.persist(anInfoEntity("info", 
						"jetty + resteasy, weld and hibernate"));
			}
			
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw e;
		}
	}
	
	public String getInfo() {
		em.getTransaction().begin();
		try {
			InfoEntity entity = selectInfoByKey("info");
			
			em.getTransaction().commit();
			
			if (entity != null) {
				return entity.getValue();
			} else {
				return "no info";
			}
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw e;
		}
	}
	
	private InfoEntity selectInfoByKey(String key) {
		List<InfoEntity> list = em.createNamedQuery(selectInfoByKey, InfoEntity.class)
			.setParameter("key", "info")
			.getResultList();
		
		if (list.isEmpty()) {
			return null;
		} else {
			return list.get(0);
		}
	}

}
