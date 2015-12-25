package de.dpunkt.myaktion.controller;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import de.dpunkt.myaktion.model.Aktion;
import de.dpunkt.myaktion.model.FormConfig;

@SessionScoped
@Named
public class SpendeFormEditController implements Serializable {
	private static final long serialVersionUID = -4210085664588144340L;
	private Aktion aktion;
	private FormConfig formConfig;
	
	@Inject
	private HttpServletRequest req;

	public SpendeFormEditController() {
		formConfig = new FormConfig();
	}
	
	public String doOk() {
		return Pages.AKTION_LIST;
	}

	private String getAppUrl() {
		String scheme = req.getScheme();
		String serverName = req.getServerName();
		int serverPort = req.getServerPort();
		String contextPath = req.getContextPath();
		return scheme + "://" + serverName + ":" + serverPort + contextPath;
	}

	public String getUrl() {
		return getAppUrl() + "/" + Pages.GELD_SPENDEN + ".jsf" + "?bgColor="
				+ formConfig.getBgColor() + "&textColor=" + formConfig.getTextColor()
				+ "&titel=" + formConfig.getTitel() + "&aktionId=" + aktion.getId();
	}

	public Aktion getAktion() {
		return aktion;
	}

	public void setAktion(Aktion aktion) {
		this.aktion = aktion;
	}
	
	public FormConfig getFormConfig() {
		return formConfig;
	}
	
	public void setFormConfig(FormConfig formConfig) {
		this.formConfig = formConfig;
	}
}
