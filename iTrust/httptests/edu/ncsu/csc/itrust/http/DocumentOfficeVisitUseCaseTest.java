package edu.ncsu.csc.itrust.http;

import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebForm;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;

/**
 * Use Case 11
 */
public class DocumentOfficeVisitUseCaseTest extends iTrustHTTPTest {

	public void testAddLabProcedure() throws Exception {
		gen.clearAllTables();
		gen.standardData();
		// login UAP
		WebConversation wc = login("8000000009", "uappass1");
		WebResponse wr = wc.getCurrentPage();
		assertEquals("iTrust - UAP Home", wr.getTitle());
		// click Document Office Visit
		wr = wr.getLinkWith("Document Office Visit").click();
		// choose patient 1
		WebForm patientForm = wr.getForms()[0];
		patientForm.getScriptableObject().setParameterValue("UID_PATIENTID", "2");
		patientForm.getButtons()[1].click();
		wr = wc.getCurrentPage();
		assertEquals(ADDRESS + "auth/hcp-uap/documentOfficeVisit.jsp", wr.getURL().toString());
		// click 06/10/2007
		wr.getLinkWith("06/10/2007").click();
		wr = wc.getCurrentPage();
		assertEquals(ADDRESS + "auth/hcp-uap/editOfficeVisit.jsp?ovID=955", wr.getURL().toString());
		assertEquals("iTrust - Document Office Visit", wr.getTitle());
		//add new lab procedure
		WebForm form = wr.getForms()[0];
		form.setParameter("addLabProcID", "10666-6");
		form.getSubmitButton("addLP").click();
		wr = wc.getCurrentPage();
		assertEquals("iTrust - Document Office Visit", wr.getTitle());
		assertTrue(wr.getText().contains("Information Updated Successfully"));
	}
	
	
	public void testRemoveLabProcedure() throws Exception {
		gen.clearAllTables();
		gen.standardData();
		// login UAP
		WebConversation wc = login("8000000009", "uappass1");
		WebResponse wr = wc.getCurrentPage();
		assertEquals("iTrust - UAP Home", wr.getTitle());
		// click Document Office Visit
		wr = wr.getLinkWith("Document Office Visit").click();
		// choose patient 1
		WebForm patientForm = wr.getForms()[0];
		patientForm.getScriptableObject().setParameterValue("UID_PATIENTID", "2");
		patientForm.getButtons()[1].click();
		wr = wc.getCurrentPage();
		assertEquals(ADDRESS + "auth/hcp-uap/documentOfficeVisit.jsp", wr.getURL().toString());
		// click 10/10/2005
		wr.getLinkWith("06/10/2007").click();
		wr = wc.getCurrentPage();
		assertEquals(ADDRESS + "auth/hcp-uap/editOfficeVisit.jsp?ovID=955", wr.getURL().toString());
		assertEquals("iTrust - Document Office Visit", wr.getTitle());
		//remove lab procedure
		WebTable wt = wr.getTableStartingWith("Laboratory Procedures");
		assertFalse(wt.getText().contains("No Laboratory Procedures on record"));
		//click the remove link
		wt = wr.getTableStartingWith("Laboratory Procedures");
		wr = wt.getTableCell(2, 5).getLinkWith("Remove").click();
		assertEquals("iTrust - Document Office Visit", wr.getTitle());
		assertTrue(wr.getText().contains("Information Updated Successfully"));
		wt = wr.getTableStartingWith("Laboratory Procedures");
		System.out.println(wt.getText());
		assertTrue(wt.getText().contains("No Laboratory Procedures on record"));
	}




}
