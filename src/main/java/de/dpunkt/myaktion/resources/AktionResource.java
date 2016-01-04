package de.dpunkt.myaktion.resources;

import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import de.dpunkt.myaktion.model.Aktion;
import de.dpunkt.myaktion.services.AktionService;

@Path("/organisator/aktion")
public class AktionResource {
	@Inject
	private AktionService aktionService;
	
	@GET
	@Path("/list")
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public List<Aktion> getAllAktionen() {
		List<Aktion> allAktionen = aktionService.getAllAktionen();
		for (Aktion aktion : allAktionen) {
			aktion.setSpenden(null);
			aktion.setOrganisator(null);
		}
		return allAktionen;
	}
	
	@DELETE
	@Path("/{aktionId}")
	public void deleteAktion(@PathParam(value = "aktionId") Long aktionId) {
		aktionService.deleteAktion(aktionId);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Aktion addAktion(Aktion aktion) {
		return aktionService.addAktion(aktion);
	}
	
	@PUT
	@Path("/{aktionId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Aktion updateAktion(@PathParam(value = "aktionId") Long aktionId, Aktion newAktion) {
		Aktion aktion = aktionService.getAktion(aktionId);
		aktion.setName(newAktion.getName());
		aktion.setSpendenBetrag(newAktion.getSpendenBetrag());
		aktion.setSpendenZiel(newAktion.getSpendenZiel());
		newAktion = aktionService.updateAktion(aktion);
		newAktion.setSpenden(null);
		newAktion.setOrganisator(null);
		return newAktion;
	}
}