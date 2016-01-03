package de.dpunkt.myaktion.services;

import javax.annotation.security.PermitAll;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import de.dpunkt.myaktion.model.Organisator;

@Stateless
public class OrganisatorServiceBean implements OrganisatorService {
	
	@Inject
	EntityManager entityManager;

	@Override
	@PermitAll
	public void addOrganisator(Organisator organisator) {
		entityManager.persist(organisator);
	}

}
