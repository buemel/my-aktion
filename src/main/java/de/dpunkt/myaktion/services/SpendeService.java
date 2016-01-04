package de.dpunkt.myaktion.services;

import java.util.List;

import de.dpunkt.myaktion.model.Spende;
import de.dpunkt.myaktion.services.exceptions.ObjectNotFoundException;

public interface SpendeService {
	List<Spende> getSpendeList(Long aktionId);
	List<Spende> getSpendeListPublic(Long aktionId) throws ObjectNotFoundException;
	void addSpende(Long aktionId, Spende spende);
	void transferSpende();
}
