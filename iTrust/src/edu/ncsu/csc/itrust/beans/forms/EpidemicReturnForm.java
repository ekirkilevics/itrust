package edu.ncsu.csc.itrust.beans.forms;

public class EpidemicReturnForm {
	private String imageQuery = "";
	private boolean usedEpidemic = true;
	private String epidemicMessage = "";

	public String getImageQuery() {
		return imageQuery;
	}

	public void setImageQuery(String imageQuery) {
		this.imageQuery = imageQuery;
	}

	public boolean isUsedEpidemic() {
		return usedEpidemic;
	}

	public void setUsedEpidemic(boolean usedEpidemic) {
		this.usedEpidemic = usedEpidemic;
	}

	public String getEpidemicMessage() {
		return epidemicMessage;
	}

	public void setEpidemicMessage(String epidemicMessage) {
		this.epidemicMessage = epidemicMessage;
	}
}
