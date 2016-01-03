package de.dpunkt.myaktion.controller;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.bind.DatatypeConverter;

import de.dpunkt.myaktion.model.Organisator;
import de.dpunkt.myaktion.services.OrganisatorService;
import de.dpunkt.myaktion.util.LogTypes.FachLog;

@SessionScoped
@Named
public class OrganisatorEditController implements Serializable {
	private static final long serialVersionUID = 8638311727811178218L;
	
	private Organisator organisator;
	
	@Inject @FachLog
	private Logger logger;

	@Inject
	private OrganisatorService organisatorService;
	
	@PostConstruct
	private void init() {
		this.organisator = new Organisator();
	}
	
	public String doCreate() {
		addOrganisator();
		return Pages.ORGANISATOR_ADD;
	}
	
	private void addOrganisator() {
		String passwortPlain = organisator.getPasswort();
		String passwortHashed = hashPasswort(passwortPlain);
		organisator.setPasswort(passwortHashed);
		organisatorService.addOrganisator(organisator);
		organisator.setPasswort(passwortPlain);
		logger.info("Neuer Organisator mit email '" + organisator.getEmail() + "' hinzugefügt.");
		init();
	}

	private String hashPasswort(String passwortPlain) {
		String passwortHashed;
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA-256");
			md.update(passwortPlain.getBytes("UTF-8"));
			passwortHashed = DatatypeConverter.printHexBinary(md.digest()).toLowerCase();
		} catch (Exception e) {
			logger.severe(e.getMessage());
			passwortHashed = "ERROR";
		}
		
		return passwortHashed;
	}

	public Organisator getOrganisator() {
		return organisator;
	}
	
	public void setOrganisator(Organisator organisator) {
		this.organisator = organisator;
	}
	
	public String getTitle() {
		return "Organisator hinzufügen";
	}

}
