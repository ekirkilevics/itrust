package edu.ncsu.csc.itrust.http;

import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;

public class ConsultationTest extends iTrustHTTPTest {
	@Override
	protected void setUp() throws Exception {
		gen.clearAllTables();
		gen.icd9cmCodes();
		gen.ndCodes();
		gen.hospitals();
		gen.hcp0();
		gen.hcp1();
		//gen.patient1();
		gen.patient2();
		gen.patient5();
		
		gen.clearLoginFailures();
	}

	/*
	 * 
	 */
	public void testSubmitAndReceiveConsultation() throws Exception {
		
		WebConversation wc = login("9000000000", "pw");
		WebResponse wr = wc.getCurrentPage();
		assertEquals("iTrust - HCP Home", wr.getTitle());
		
		wr = wr.getLinkWith("Consultations").click();
		assertTrue(wr.getText().contains("HCP Consultations"));
		
		wr.getForms()[0].getButtons()[0].click();
		
		wr = wc.getCurrentPage();
		
		assertTrue(wr.getText().contains("Send a Consultation"));
		
		
		wr.getForms()[0].setParameter("patient", "5");
		wr.getForms()[0].setParameter("hcp", "9000000003");
		wr = wr.getForms()[0].submit();
		
		assertTrue(wr.getText().contains("Consultation Form"));
		
		wr.getForms()[0].setParameter("msg", "Test1");
		wr = wr.getForms()[0].submit();
		
		assertTrue(wr.getText().contains("Thank you, your Consultation Request was sent."));

		assertTrue(wr.getText().contains("Test1"));
		
		
		/*
		 * Time to receive the consultation.
		 */
		
		wc = login("9000000003", "pw");
		
		wr = wc.getCurrentPage();
		assertEquals("iTrust - HCP Home", wr.getTitle());
		
		wr = wr.getLinkWith("Consultations").click();
		assertTrue(wr.getText().contains("HCP Consultations"));
		
		wr.getForms()[0].getButtons()[1].click();
		wr = wc.getCurrentPage();
		
		assertTrue(wr.getText().contains("View Pending Consultations"));
		
		//System.out.println(wr.getText());
		
		assertTrue(wr.getText().contains("Kelly Doctor"));
		
		wr = wr.getLinkWith("edit").click();
		
		assertTrue(wr.getText().contains("Referral Details:"));
		
		WebTable wt = wr.getTableStartingWith("Patient:");
		
		assertEquals("Baby Programmer", wt.getCellAsText(0, 1));
		assertEquals("Kelly Doctor", wt.getCellAsText(1, 1));
		assertEquals("Gandalf Stormcrow", wt.getCellAsText(2, 1));
		
		
		assertTrue(wr.getText().contains("Test1"));
		
		
	}
	
	
	
	
public void testSubmitAndEditConsultation() throws Exception {
		
		WebConversation wc = login("9000000000", "pw");
		WebResponse wr = wc.getCurrentPage();
		assertEquals("iTrust - HCP Home", wr.getTitle());
		
		wr = wr.getLinkWith("Consultations").click();
		assertTrue(wr.getText().contains("HCP Consultations"));
		
		wr.getForms()[0].getButtons()[0].click();
		
		wr = wc.getCurrentPage();
		
		assertTrue(wr.getText().contains("Send a Consultation"));
		
		
		wr.getForms()[0].setParameter("patient", "5");
		wr.getForms()[0].setParameter("hcp", "9000000003");
		wr = wr.getForms()[0].submit();
		
		assertTrue(wr.getText().contains("Consultation Form"));
		
		wr.getForms()[0].setParameter("msg", "Test1");
		wr = wr.getForms()[0].submit();
		
		assertTrue(wr.getText().contains("Thank you, your Consultation Request was sent."));

		assertTrue(wr.getText().contains("Test1"));
		
		wr = wr.getLinkWith("Consultations").click();
		assertTrue(wr.getText().contains("HCP Consultations"));
		
		wr.getForms()[0].getButtons()[1].click();
		
		wr = wc.getCurrentPage();
		
		assertTrue(wr.getText().contains("View Pending Consultations"));
		
		assertTrue(wr.getText().contains("Baby Programmer (5)"));
		
		wr = wr.getLinkWith("edit").click();
		
		WebTable wt = wr.getTableStartingWith("Patient:");
		
		assertEquals("Baby Programmer", wt.getCellAsText(0, 1));
		assertEquals("Kelly Doctor", wt.getCellAsText(1, 1));
		assertEquals("Gandalf Stormcrow", wt.getCellAsText(2, 1));
		
		
		wr.getForms()[0].setParameter("refDetails", "Test2");
		wr = wr.getForms()[0].submit();
		
		assertTrue(wr.getText().contains("Consultation updated"));

		
		
		
		
		wr = wr.getLinkWith("Consultations").click();
		wr.getForms()[0].getButtons()[1].click();
		wr = wc.getCurrentPage();
		wr = wr.getLinkWith("edit").click();
		
		assertTrue(wr.getText().contains("Test2"));
		
		
		
	}


	public void testReceiveAndEditConsultation() throws Exception {
		
		WebConversation wc = login("9000000000", "pw");
		WebResponse wr = wc.getCurrentPage();
		assertEquals("iTrust - HCP Home", wr.getTitle());
		
		wr = wr.getLinkWith("Consultations").click();
		assertTrue(wr.getText().contains("HCP Consultations"));
		
		wr.getForms()[0].getButtons()[0].click();
		
		wr = wc.getCurrentPage();
		
		assertTrue(wr.getText().contains("Send a Consultation"));
		
		
		wr.getForms()[0].setParameter("patient", "5");
		wr.getForms()[0].setParameter("hcp", "9000000003");
		wr = wr.getForms()[0].submit();
		
		assertTrue(wr.getText().contains("Consultation Form"));
		
		wr.getForms()[0].setParameter("msg", "Test3");
		wr = wr.getForms()[0].submit();
		
		assertTrue(wr.getText().contains("Thank you, your Consultation Request was sent."));
	
		assertTrue(wr.getText().contains("Test3"));
		
		
		
		/*
		 * Time to receive the consultation.
		 */
		
		wc = login("9000000003", "pw");
		
		wr = wc.getCurrentPage();
		assertEquals("iTrust - HCP Home", wr.getTitle());
		
		wr = wr.getLinkWith("Consultations").click();
		

		assertTrue(wr.getText().contains("HCP Consultations"));
		
		wr.getForms()[0].getButtons()[1].click();
		
		wr = wc.getCurrentPage();
		
		assertTrue(wr.getText().contains("View Pending Consultations"));
		
		assertTrue(wr.getText().contains("Baby Programmer (5)"));
		
		wr = wr.getLinkWith("edit").click();
		
		WebTable wt = wr.getTableStartingWith("Patient:");
		
		assertEquals("Baby Programmer", wt.getCellAsText(0, 1));
		assertEquals("Kelly Doctor", wt.getCellAsText(1, 1));
		assertEquals("Gandalf Stormcrow", wt.getCellAsText(2, 1));
		
		
		wr.getForms()[0].setParameter("consDetails", "TestConsultation");
		wr.getForms()[0].setParameter("status", "Finished");
		wr = wr.getForms()[0].submit();
		
		assertTrue(wr.getText().contains("Consultation updated"));
	
		
		
		/*
		 * Time to review the consultation.
		 */
		
		wc = login("9000000000", "pw");
		
		wr = wc.getCurrentPage();
		assertEquals("iTrust - HCP Home", wr.getTitle());
		
		wr = wr.getLinkWith("Consultations").click();
		

		assertTrue(wr.getText().contains("HCP Consultations"));
		
		wr.getForms()[0].getButtons()[1].click();
		
		wr = wc.getCurrentPage();
		
		assertTrue(wr.getText().contains("Finished"));
		
		wr = wr.getLinkWith("edit").click();
		
		assertTrue(wr.getText().contains("TestConsultation"));
		
		
		
	}
	
	


}
