package de.dpunkt.myaktion.model;

public class FormConfig {
	private String textColor;
	private String bgColor;
	private String titel;

	public FormConfig() {
		this("000000", "ffffff", "Geld spenden");
	}

	public FormConfig(String textColor, String bgColor, String titel) {
		this.textColor = textColor;
		this.bgColor = bgColor;
		this.titel = titel;
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
}
