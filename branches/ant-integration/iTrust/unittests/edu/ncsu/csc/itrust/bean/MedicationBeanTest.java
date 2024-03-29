package edu.ncsu.csc.itrust.bean;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.MedicationBean;

public class MedicationBeanTest extends TestCase {

	private MedicationBean med;
	@Override
	protected void setUp() throws Exception {
		med = new MedicationBean();
		med.setDescription("blah");
		med.setNDCode("blah blah");
	}

	public void testPrescriptionEquals() throws Exception {
		med = new MedicationBean();
		med.setDescription("blah");
		med.setNDCode("blah blah");
		MedicationBean med2 = new MedicationBean();
		med2.setDescription("blah");
		med2.setNDCode("blah blah");
		assertEquals(med2, med);
	}
	
	public void testGetFormatted()
	{
		med = new MedicationBean();
		med.setNDCode("9283777777");
		assertEquals("92837-77777", med.getNDCodeFormatted());
		assertEquals(42, med.hashCode());
	}
	
	public void testShortCode() {
		med = new MedicationBean();
		med.setNDCode("1");
		assertEquals("1", med.getNDCodeFormatted());
	}
}
