package de.dpunkt.myaktion.services;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import de.dpunkt.myaktion.model.Aktion;
import de.dpunkt.myaktion.util.LogTypes.TecLog;

@Stateless
public class AktionServiceBean implements AktionService, Serializable {
	private static final long serialVersionUID = -5091381921805941879L;

	@Inject @TecLog
	private  Logger logger;
	
	@Inject
	private EntityManager entityManager;

	@Override
	public List<Aktion> getAllAktionen() {
		logger.info("returning all Aktionen");
		TypedQuery<Aktion> query = entityManager.createNamedQuery(Aktion.findAll, Aktion.class);
		
		List<Aktion> aktionen = query.getResultList();
		
		for (Aktion aktion: aktionen) {
			Double bisherGespendet = getBisherGespendet(aktion);
			aktion.setBisherGespendet(bisherGespendet);
		}
		
		return aktionen;
	}

	private Double getBisherGespendet(Aktion aktion) {
		TypedQuery<Double> query = entityManager.createNamedQuery(Aktion.getBisherGespendet, Double.class);
		query.setParameter("action", aktion);
		Double result = query.getSingleResult();
		
		if (result == null) {
			result = 0d;
		}
		
		return result;
	}

	@Override
	public void addAktion(Aktion aktion) {
		entityManager.persist(aktion);
	}

	@Override
	public void updateAktion(Aktion aktion) {
		entityManager.merge(aktion);
	}

	@Override
	public void deleteAktion(Aktion aktion) {
		Aktion managedAktion = entityManager.find(Aktion.class, aktion.getId());
		entityManager.remove(managedAktion);
	}
	
}
