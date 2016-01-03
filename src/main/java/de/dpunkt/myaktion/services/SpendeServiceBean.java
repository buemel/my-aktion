package de.dpunkt.myaktion.services;

import java.util.List;
import java.util.logging.Logger;

//import javax.annotation.security.PermitAll;
//import javax.annotation.security.RolesAllowed;
//import javax.ejb.Stateless;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import de.dpunkt.myaktion.model.Aktion;
import de.dpunkt.myaktion.model.Spende;
import de.dpunkt.myaktion.model.Spende.Status;
import de.dpunkt.myaktion.util.LogTypes.FachLog;
import de.dpunkt.myaktion.util.PerformanceAuditor;
import de.dpunkt.myaktion.util.TransactionInterceptor;

//@Stateless
@RequestScoped
public class SpendeServiceBean implements SpendeService {
	
	@Inject
	EntityManager entityManager;
	
	@Inject @FachLog
	private Logger logger;

	@Override
	//@RolesAllowed("Organisator")
	@Interceptors(TransactionInterceptor.class)
	public List<Spende> getSpendeList(Long aktionId) {
		Aktion managedAktion = entityManager.find(Aktion.class, aktionId);
		List<Spende> spenden = managedAktion.getSpenden();
		spenden.size();

		return spenden;
	}

	@Override
	//@PermitAll
	@Interceptors(TransactionInterceptor.class)
	public void addSpende(Long aktionId, Spende spende) {
		Aktion managedAktion = entityManager.find(Aktion.class, aktionId);
		spende.setAktion(managedAktion);
		
		entityManager.persist(spende);
	}

	@Override
	//@PermitAll
	@Interceptors(PerformanceAuditor.class)
	public void transferSpende() {
		logger.info("Zu bearbeitende Spenden werden überwiesen.");
		TypedQuery<Spende> query = entityManager.createNamedQuery(Spende.findByStatus, Spende.class);
		query.setParameter("status", Status.IN_BEARBEITUNG);
		List<Spende> spenden = query.getResultList();
		int count = 0;
		for (Spende spende : spenden) {
			spende.setStatus(Status.UEBERWIESEN);
			count++;
		}
		logger.info("Es wurden " + count + " Spenden überwiesen.");
	}
	
}
