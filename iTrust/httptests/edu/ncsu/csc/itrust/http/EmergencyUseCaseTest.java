package edu.ncsu.csc.itrust.http;

import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebForm;
import com.meterware.httpunit.WebResponse;

public class EmergencyUseCaseTest extends iTrustHTTPTest {
	
	protected void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.standardData();		
	}

	/*
	 */
	public void testERViewEmergencyReport1() throws Exception {
		// The HCP logs in and requests a comprehensive report for patient 2.
		WebConversation wc = login("9000000006", "pw");
		WebResponse wr = wc.getCurrentPage();
		assertEquals("iTrust - ER Home", wr.getTitle());
		wr = wr.getLinkWith("Emergency Patient Report").click();
        assertEquals("iTrust - Please Select a Patient", wr.getTitle());
      
		WebForm patientForm = wr.getForms()[0];
		patientForm.getScriptableObject().setParameterValue("UID_PATIENTID", "2");
		patientForm.getButtons()[1].click();
		wr = patientForm.submit();
        assertEquals("iTrust - ER Report", wr.getTitle());
        assertTrue(wr.getText().contains("Blood Type: O-"));
        assertTrue(wr.getText().contains("Pollen 06/05/2007"));
        assertTrue(wr.getText().contains("Penicillin 06/04/2007"));
        assertTrue(wr.getText().contains("250.10 Diabetes with ketoacidosis"));
        assertTrue(wr.getText().contains("79.30 Coxsackie"));
        assertTrue(wr.getText().contains("647641512 Prioglitazon"));
        assertTrue(wr.getText().contains("no immunizations on record"));
	}
	
	public void testHCPViewEmergencyReport1() throws Exception {	
		// The HCP logs in and requests a comprehensive report for patient 2.
		WebConversation wc = login("9000000000", "pw");
		WebResponse wr = wc.getCurrentPage();
		assertEquals("iTrust - HCP Home", wr.getTitle());
		wr = wr.getLinkWith("Emergency Patient Report").click();
        assertEquals("iTrust - Please Select a Patient", wr.getTitle());
      
		WebForm patientForm = wr.getForms()[0];
		patientForm.getScriptableObject().setParameterValue("UID_PATIENTID", "2");
		patientForm.getButtons()[1].click();
		wr = patientForm.submit();
        assertEquals("iTrust - ER Report", wr.getTitle());
        assertTrue(wr.getText().contains("Blood Type: O-"));
        assertTrue(wr.getText().contains("Pollen 06/05/2007"));
        assertTrue(wr.getText().contains("Penicillin 06/04/2007"));
        assertTrue(wr.getText().contains("250.10 Diabetes with ketoacidosis"));
        assertTrue(wr.getText().contains("79.30 Coxsackie"));
        assertTrue(wr.getText().contains("647641512 Prioglitazon"));
        assertTrue(wr.getText().contains("no immunizations on record"));
	}
	
	public void testHCPViewEmergencyReport2() throws Exception {	
		// The HCP logs in and requests a comprehensive report for patient 2.
		WebConversation wc = login("9000000000", "pw");
		WebResponse wr = wc.getCurrentPage();
		assertEquals("iTrust - HCP Home", wr.getTitle());
		wr = wr.getLinkWith("Emergency Patient Report").click();
        assertEquals("iTrust - Please Select a Patient", wr.getTitle());
      
		WebForm patientForm = wr.getForms()[0];
		patientForm.getScriptableObject().setParameterValue("UID_PATIENTID", "1");
		patientForm.getButtons()[1].click();
		wr = patientForm.submit();
        assertEquals("iTrust - ER Report", wr.getTitle());
        assertTrue(wr.getText().contains("Blood Type: AB+"));
        assertTrue(wr.getText().contains("No allergies on record"));
        assertTrue(wr.getText().contains("250.00 Acute Lycanthropy"));
        assertTrue(wr.getText().contains("No current prescriptions on record"));
        assertTrue(wr.getText().contains("no immunizations on record"));
	}
}
