package de.dpunkt.myaktion.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import de.dpunkt.myaktion.model.Aktion;
import de.dpunkt.myaktion.model.Spende;

@Stateless
public class SpendeServiceBean implements SpendeService {
	
	@Inject
	EntityManager entityManager;

	@Override
	public List<Spende> getSpendeList(Long aktionId) {
		Aktion managedAktion = entityManager.find(Aktion.class, aktionId);
		List<Spende> spenden = managedAktion.getSpenden();
		spenden.size();

		return spenden;
	}

	@Override
	public void addSpende(Long aktionId, Spende spende) {
		Aktion managedAktion = entityManager.find(Aktion.class, aktionId);
		spende.setAktion(managedAktion);
		
		entityManager.persist(spende);
	}

}
