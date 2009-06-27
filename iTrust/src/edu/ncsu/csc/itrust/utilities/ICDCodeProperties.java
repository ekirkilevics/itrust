package edu.ncsu.csc.itrust.utilities;

import java.util.List;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

public class ICDCodeProperties {
	
	float dbm,asthma,csd_start,csd_end;

	/**
	 * Loads a property file for setting the ICD codes for
	 * diabetes mellitus, asthma, and circulatory system disease
	 * @param filename The filename and path of the property filed
	 */
	public ICDCodeProperties(String filename){
		PropertiesConfiguration pc;
		try {
			pc = new PropertiesConfiguration(filename); //"../../META-INF/properties.txt");
			dbm = pc.getInt("diabetes_mellitus");
			asthma = pc.getInt("asthma");
			List<String> csd = pc.getList("circulatory_system_disease");
			csd_start = Float.parseFloat(csd.get(0));
			csd_end = Float.parseFloat(csd.get(1))-0.01f;

		} catch (ConfigurationException e) {
			//Couldn't find property file, resort to default values
			dbm = 250.0f;
			csd_start = 390;
			csd_end = 460-0.01f;
			asthma = 493;
			e.printStackTrace();
			return;
		}
		
	}
	
	public float getICDForDiabetesMellitus(){
		return dbm;
	}
	
	public float getICDForAsthma(){
		return asthma;
	}
	
	public float getStartICDForCirculatorySystemDisease(){
		return csd_start;
	}
	
	public float getEndICDForCirculatorySystemDisease(){
		return csd_end;
	}
	
	
	
}
