//jabrambl

package edu.ncsu.csc.itrust.http;

import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebForm;
import com.meterware.httpunit.WebResponse;


public class NF46Test  extends iTrustHTTPTest {

	@Override
	protected void setUp() throws Exception {
		gen.clearAllTables();
		gen.uap1();
		gen.patient2();
		gen.patient1();
		gen.patient5();
		gen.hcp0();
	}


	public void testMIDShown1() throws Exception{
		// login uap
		WebConversation wc = login("8000000009", "uappass1");
		WebResponse wr = wc.getCurrentPage();
		assertEquals("iTrust - UAP Home", wr.getTitle());
		// choose Edit Patient
		wr = wr.getLinkWith("Edit Patient").click();
		// choose patient 2
		WebForm patientForm = wr.getForms()[0];
		patientForm.getScriptableObject().setParameterValue("UID_PATIENTID", "2");
		patientForm.getButtons()[1].click();
		//get the current page
		wr = wc.getCurrentPage();
		//confirm no MID in url
		assertEquals(ADDRESS + "auth/hcp-uap/editPatient.jsp", wr.getURL().toString());
	}

	public void testMIDShown2() throws Exception{
		// login hcp
		WebConversation wc = login("9000000000", "pw");
		WebResponse wr = wc.getCurrentPage();
		assertEquals("iTrust - HCP Home", wr.getTitle());
		// choose Edit Patient
		wr = wr.getLinkWith("Patient Information").click();
		// choose patient 2
		WebForm patientForm = wr.getForms()[0];
		patientForm.getScriptableObject().setParameterValue("UID_PATIENTID", "2");
		patientForm.getButtons()[1].click();
		//get the current page
		wr = wc.getCurrentPage();
		//confirm no MID in url
		assertEquals(ADDRESS + "auth/hcp-uap/editPatient.jsp", wr.getURL().toString());
	}
	
	public void testMIDShown3() throws Exception {
		//log in as hcp
		WebConversation wc = login("9000000000", "pw");
		WebResponse wr = wc.getCurrentPage();
		//go to edit phr
		wr = wr.getLinkWith("PHR Information").click();
		//select patient 2
		WebForm patientForm = wr.getForms()[0];
		patientForm.getScriptableObject().setParameterValue("UID_PATIENTID", "2");
		patientForm.getButtons()[1].click();
		wr = patientForm.submit();
		//find the baby programmer link
		wr = wr.getLinkWith("Baby Programmer").click();
		//make sure there's no MID in the url
		assertEquals(ADDRESS + "auth/hcp-uap/editPHR.jsp?relative=1", wr.getURL().toString());
	}
	
	}





