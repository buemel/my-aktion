package de.dpunkt.myaktion.services;

import java.io.Serializable;
import java.security.Principal;
import java.util.List;
import java.util.logging.Logger;

//import javax.annotation.Resource;
//import javax.annotation.security.RolesAllowed;
//import javax.ejb.SessionContext;
//import javax.ejb.Stateless;
import javax.enterprise.context.RequestScoped;
//import javax.ejb.TransactionManagement;
//import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
//import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
//import javax.transaction.UserTransaction;

import de.dpunkt.myaktion.model.Aktion;
import de.dpunkt.myaktion.model.Organisator;
import de.dpunkt.myaktion.util.LogTypes.TecLog;
import de.dpunkt.myaktion.util.TransactionInterceptor;
//import de.dpunkt.myaktion.util.TransactionInterceptor;

//@TransactionManagement(TransactionManagementType.BEAN)
//@Interceptors(TransactionInterceptor.class)
//@RolesAllowed("Organisator")
//@Stateless
@RequestScoped
@Interceptors(TransactionInterceptor.class)
public class AktionServiceBean implements AktionService, Serializable {
	private static final long serialVersionUID = -5091381921805941879L;

	@Inject @TecLog
	private  Logger logger;
	
	@Inject
	private EntityManager entityManager;
	
	@Inject
	private Principal principal;
	
//	@Resource
//	private SessionContext sessionContext;
	
//	@Resource
//	private UserTransaction userTransaction;

	@Override
	public List<Aktion> getAllAktionen() {
		logger.info("returning all Aktionen");
		TypedQuery<Aktion> query = entityManager.createNamedQuery(Aktion.findByOrganisator, Aktion.class);
		query.setParameter("organisator", getLoggedinOrganisator());
		
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
//	public void addAktion(Aktion aktion) {
//		try {
//			userTransaction.begin();
//			Organisator organisator = getLoggedinOrganisator();
//			aktion.setOrganisator(organisator);
//			entityManager.persist(aktion);
//			userTransaction.commit();
//		} catch (Exception e) {
//			try {
//				userTransaction.rollback();
//				System.err.println("addAktion - Transaktion wurde zurückgerollt. Aktion: " + aktion.getName());
//			} catch (Exception e2) {
//				System.err.println(
//						"addAktion - Fehler beim Zurückrollen von Transaktion. Aktion: " + aktion.getName());
//			}
//		}
//	}
	public void addAktion(Aktion aktion) {
		Organisator organisator = getLoggedinOrganisator();
		aktion.setOrganisator(organisator);
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
	
	private Organisator getLoggedinOrganisator() {
		//String organisatorEmail = sessionContext.getCallerPrincipal().getName();
		String organisatorEmail = principal.getName();
		
		Organisator organisator = entityManager.createNamedQuery(Organisator.findByEmail, Organisator.class)
				.setParameter("email", organisatorEmail).getSingleResult();
		return organisator;
	}
	
}
