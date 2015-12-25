package de.dpunkt.myaktion.controller;

import java.io.Serializable;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import de.dpunkt.myaktion.model.Spende;
import de.dpunkt.myaktion.model.Spende.Status;
import de.dpunkt.myaktion.services.SpendeService;
import de.dpunkt.myaktion.util.LogTypes.FachLog;

@SessionScoped
@Named
public class GeldSpendenController implements Serializable {
	private static final long serialVersionUID = 5493038842003809106L;
	private String textColor = "000000";
	private String bgColor = "ffffff";
	private String titel = "Geld spenden";
	private Spende spende;
	private Long aktionId;
	
	@Inject
	private FacesContext facesContext;

	@Inject @FachLog
	private Logger logger;
	
	@Inject
	private SpendeService spendeService;

	public GeldSpendenController() {
	}
	
	@PostConstruct
	public void init() {
		this.spende = new Spende();
	}

	public Spende getSpende() {
		return spende;
	}

	public void setSpende(Spende spende) {
		this.spende = spende;
	}

	public String getTextColor() {
		return textColor;
	}

	public void setTextColor(String textColor) {
		this.textColor = textColor;
	}

	public String getBgColor() {
		return bgColor;
	}

	public void setBgColor(String bgColor) {
		this.bgColor = bgColor;
	}
	
	public String getTitel() {
		return titel;
	}
	
	public void setTitel(String titel) {
		this.titel = titel;
	}

	public Long getAktionId() {
		return aktionId;
	}

	public void setAktionId(Long aktionId) {
		this.aktionId = aktionId;
	}

	public String doSpende() {
		addSpende();
		FacesMessage facesMessage = new FacesMessage(
				FacesMessage.SEVERITY_INFO, "Vielen Dank für die Spende", null);
		facesContext.addMessage(null, facesMessage);
		return Pages.GELD_SPENDEN;
	}
	
	public void addSpende() {
		getSpende().setStatus(Status.IN_BEARBEITUNG);
		spendeService.addSpende(getAktionId(), getSpende());
		logger.info(spende.getSpenderName() + " hat " + spende.getBetrag() + " Euro gespendet.");
		init();
	}
}
