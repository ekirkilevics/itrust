package edu.ncsu.csc.itrust.beans;

/*
 * This class sets up a bean for LOINC
 */

public class LOINCbean {
	private String labProcedureCode;
	private String component;
	private String kindOfProperty;
	private String timeAspect;
	private String system;
	private String scaleType;
	private String methodType;
	
	public LOINCbean(){
		
	}

	public String getLabProcedureCode(){
		return labProcedureCode;
	}
	
	public void setLabProcedureCode(String aLabProcedureCode){
		labProcedureCode=aLabProcedureCode;
	}
	
	public String getComponent(){
		return component;
	}
	
	public void setComponent(String aComponent){
		component=aComponent;
	}

	public String getKindOfProperty(){
		return kindOfProperty;
	}
	
	public void setKindOfProperty(String aKindOfProperty){
		kindOfProperty=aKindOfProperty;
	}
	
	public String getTimeAspect(){
		return timeAspect;
	}
	
	public void setTimeAspect(String aTimeAspect){
		timeAspect=aTimeAspect;
	}

	public String getSystem(){
		return system;
	}
	
	public void setSystem(String aSystem){
		system=aSystem;
	}
	public String getScaleType(){
		return scaleType;
	}
	
	public void setScaleType(String aScaleType){
		scaleType=aScaleType;
	}

	public String getMethodType(){
		return methodType;
	}
	
	public void setMethodType(String aMethodType){
		methodType=aMethodType;
	}

}

