package edu.ncsu.csc.itrust.utilities;

import junit.framework.TestCase;

public class ICDCodePropertiesTest extends TestCase {
	
	public void testICDCodePropertiesFromFile() throws Exception {
		ICDCodeProperties properties = new ICDCodeProperties("unittests/edu/ncsu/csc/itrust/utilities/test_properties.txt");
		assertEquals(10,(int)properties.getICDForDiabetesMellitus());
		assertEquals(15,(int)properties.getICDForAsthma());
		assertEquals(20,(int)properties.getStartICDForCirculatorySystemDisease());
		assertEquals(29,(int)properties.getEndICDForCirculatorySystemDisease());
	}
	
	
	public void testICDCodePropertiesDefaults() throws Exception {
		ICDCodeProperties properties = new ICDCodeProperties(null);
		assertEquals(250,(int)properties.getICDForDiabetesMellitus());
		assertEquals(493,(int)properties.getICDForAsthma());
		assertEquals(390,(int)properties.getStartICDForCirculatorySystemDisease());
		assertEquals(459,(int)properties.getEndICDForCirculatorySystemDisease());
	}

}
