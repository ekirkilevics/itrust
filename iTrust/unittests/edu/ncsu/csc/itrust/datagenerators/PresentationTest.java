package edu.ncsu.csc.itrust.datagenerators;


import junit.framework.TestCase;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;

/**
 * Currently only tests missed-last-year's-flu-shot half of flu shot methods (only includes static data)
 * 
 * Testing for month decision is in DateUtil tests
 */
public class PresentationTest extends TestCase {
	private TestDataGenerator gen = new TestDataGenerator();
	
	public void testScenario1() throws Exception{
		gen.clearAllTables();
		gen.standardData();
		gen.remoteMonitoring2();
		gen.remoteMonitoringPresentation();
		//gen.patient1();
		//gen.patient2();
		//gen.hcp0();
		//gen.uap1();
		assertTrue(1==1);
	}
	
	public void testScenario2() throws Exception{
		gen.clearAllTables();
		gen.patient1();
		gen.patient10();
		gen.patient13();
		gen.patient4();
		gen.patient2();
		gen.patient3();
		gen.hcp0();
		gen.pha1();
		gen.adverseEventPresentation();
	}
	
	public void testScenario3() throws Exception {

		gen.clearAllTables();
		gen.standardData();
		gen.admin1();
		gen.ndCodes();
		gen.ndCodes1();
		gen.ndCodes2();
		gen.drugInteractions();
		gen.drugInteractions3();
		gen.officeVisit3();
	}

}
