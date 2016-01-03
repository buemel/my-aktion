package de.dpunkt.myaktion.services;

//import javax.annotation.security.PermitAll;
//import javax.ejb.Stateless;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;

import de.dpunkt.myaktion.model.Organisator;
import de.dpunkt.myaktion.util.TransactionInterceptor;

//@Stateless
@RequestScoped
@Interceptors(TransactionInterceptor.class)
public class OrganisatorServiceBean implements OrganisatorService {
	
	@Inject
	EntityManager entityManager;

	@Override
	//@PermitAll
	public void addOrganisator(Organisator organisator) {
		entityManager.persist(organisator);
	}

}
