package edu.ncsu.csc.itrust.http;

import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebForm;
import com.meterware.httpunit.WebResponse;
import edu.ncsu.csc.itrust.enums.TransactionType;

/**
 * Test all office visit document
 * @author student
 * @ author student
 *
 */
public class DocumentERVisitTest extends iTrustHTTPTest {
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.standardData();
	}
	
	
public void testAddLabProcedure() throws Exception {
		
		// login UAP
		WebConversation wc = login("9000000006", "pw");
		WebResponse wr = wc.getCurrentPage();
		assertEquals("iTrust - ER Home", wr.getTitle());
		// click Document Office Visit
		wr = wr.getLinkWith("Document Incident Report").click();
		// choose patient 2
		WebForm patientForm = wr.getForms()[0];
		patientForm.getScriptableObject().setParameterValue("UID_PATIENTID", "2");
		patientForm.getButtons()[1].click();
		wr = wc.getCurrentPage();
		assertEquals(ADDRESS + "auth/hcp-uap/documentOfficeVisit.jsp", wr.getURL().toString());
		// click 06/10/2007
		wr.getLinkWith("06/10/2007").click();
		wr = wc.getCurrentPage();
		assertEquals(ADDRESS + "auth/hcp-uap/editOfficeVisit.jsp?ovID=955", wr.getURL().toString());
		assertEquals("iTrust - Document ER Visit", wr.getTitle());
		//add new lab procedure
		WebForm form = wr.getFormWithID("labProcedureForm");
		form.setParameter("loinc", "10666-6");
		form.setParameter("labTech", "5000000001");
		form.getSubmitButton("addLP").click();
		wr = wc.getCurrentPage();
		
		assertEquals("iTrust - Document ER Visit", wr.getTitle());
		assertTrue(wr.getText().contains("information successfully updated"));
		assertLogged(TransactionType.LAB_PROCEDURE_ADD_ER, 9000000006L, 2L, "Incident Report");
		assertLogged(TransactionType.ER_VISIT_EDIT, 9000000006L, 2L, "Incident Report");
	}
	

	/*
	 * Authenticate UAP
	 * MID 8000000009
	 * Password: uappass1
	 * Choose "Document Office Visit"
	 * Enter Patient MID 1
	 * Enter Fields:
	 * Date: 2005-11-21
	 * Notes: "I like diet-coke"
	 */
	public void testDocumentOfficeVisit6() throws Exception {
		// login UAP
		WebConversation wc = login("9000000006", "pw");
		WebResponse wr = wc.getCurrentPage();
		assertEquals("iTrust - ER Home", wr.getTitle());
		// click Document Office Visit
		wr = wr.getLinkWith("Document Incident Report").click();
		// choose patient 1
		WebForm patientForm = wr.getForms()[0];
		patientForm.getScriptableObject().setParameterValue("UID_PATIENTID", "1");
		patientForm.getButtons()[1].click();
		wr = wc.getCurrentPage();
		assertEquals(ADDRESS + "auth/hcp-uap/documentOfficeVisit.jsp", wr.getURL().toString());
		// click Yes, Document Office Visit
		WebForm form = wr.getForms()[0];
		form.getButtons()[0].click();
		wr = wc.getCurrentPage();
		assertEquals("iTrust - Document ER Visit", wr.getTitle());
		// add a new office visit
		form = wr.getFormWithID("mainForm");
		form.setParameter("visitDate", "11/21/2005");
		form.setParameter("notes", "I like diet-coke");
		form.getButtonWithID("update").click();
		wr = wc.getCurrentPage();
		assertTrue(wr.getText().contains("Information Successfully Updated"));
		assertLogged(TransactionType.ER_VISIT_CREATE, 9000000006L, 1L, "Incident Report");
	}

	/*
	 * Authenticate HCP
	 * MID 9000000000
	 * Password: pw
	 * Choose Document Office Visit
	 * Enter Patient MID 2 and confirm
	 * Choose to document new office vist.
	 * Enter Fields:
	 * Date: 2005-11-2
	 * Notes: Great patient!
	 */
	public void testDocumentOfficeVisit1() throws Exception {
		// login HCP
		WebConversation wc = login("9000000006", "pw");
		WebResponse wr = wc.getCurrentPage();
		assertEquals("iTrust - ER Home", wr.getTitle());
		// click Document Office Visit
		wr = wr.getLinkWith("Document Incident Report").click();
		// choose patient 2
		WebForm patientForm = wr.getForms()[0];
		patientForm.getScriptableObject().setParameterValue("UID_PATIENTID", "2");
		patientForm.getButtons()[1].click();
		wr = wc.getCurrentPage();
		
		assertEquals(ADDRESS + "auth/hcp-uap/documentOfficeVisit.jsp", wr.getURL().toString());
		// click Yes, Document Office Visit
		WebForm form = wr.getForms()[0];
		form.getButtons()[0].click();
		wr = wc.getCurrentPage();
		
		assertEquals("iTrust - Document ER Visit", wr.getTitle());
		// add a new office visit
		form = wr.getFormWithID("mainForm");
		form.setParameter("visitDate", "11/02/2005");
		form.setParameter("notes", "Great Patient!");
		form.getButtons()[1].click();
		wr = wc.getCurrentPage();
		assertTrue(wr.getText().contains("Information Successfully Updated"));
		assertLogged(TransactionType.ER_VISIT_CREATE, 9000000006L, 2L, "Incident Report");
	}

	/*
	 * Authenticate HCP
	 * MID 9000000000
	 * Password: pw
	 * Choose Document Office Visit
	 * Enter Patient MID 2 and confirm
	 * Choose to document new office vist.
	 * Enter Fields:
	 * Date: 2005-11-21
	 * Notes: <script>alert('ha ha ha');</script>
	 */
	public void testDocumentOfficeVisit2() throws Exception {
		// login HCP
		WebConversation wc = login("9000000006", "pw");
		WebResponse wr = wc.getCurrentPage();
		assertEquals("iTrust - ER Home", wr.getTitle());
		// click Document Office Visit
		wr = wr.getLinkWith("Document Incident Report").click();
		// choose patient 2
		WebForm patientForm = wr.getForms()[0];
		patientForm.getScriptableObject().setParameterValue("UID_PATIENTID", "2");
		patientForm.getButtons()[1].click();
		wr = wc.getCurrentPage();
		assertEquals(ADDRESS + "auth/hcp-uap/documentOfficeVisit.jsp", wr.getURL().toString());
		// click Yes, Document Office Visit
		WebForm form = wr.getForms()[0];
		form.getButtons()[0].click();
		wr = wc.getCurrentPage();
		assertEquals("iTrust - Document ER Visit", wr.getTitle());
		// add a new office visit
		form = wr.getFormWithID("mainForm");
		form.setParameter("visitDate", "11/21/2005");
		form.setParameter("notes", "<script>alert('ha ha ha');</script>");
		form.getButtons()[1].click();
		wr = wc.getCurrentPage();
		assertTrue(wr.getText().contains("Notes: Up to 300 alphanumeric characters, with space, and other punctuation"));
		assertNotLogged(TransactionType.ER_VISIT_CREATE, 9000000006L, 2L, "Incident Report");
	}
	
	public void testUpdateOfficeVisitSemicolon() throws Exception {
		// login UAP
		WebConversation wc = login("9000000006", "pw");
		WebResponse wr = wc.getCurrentPage();
		assertEquals("iTrust - ER Home", wr.getTitle());
		// click Document Office Visit
		wr = wr.getLinkWith("Document Incident Report").click();
		// choose patient 1
		WebForm patientForm = wr.getForms()[0];
		patientForm.getScriptableObject().setParameterValue("UID_PATIENTID", "1");
		patientForm.getButtons()[1].click();
		wr = wc.getCurrentPage();
		assertEquals(ADDRESS + "auth/hcp-uap/documentOfficeVisit.jsp", wr.getURL().toString());
		// click Yes, Document Office Visit
		WebForm form = wr.getForms()[0];
		form.getButtons()[0].click();
		wr = wc.getCurrentPage();
		assertEquals("iTrust - Document ER Visit", wr.getTitle());
		// add a new office visit
		form = wr.getFormWithID("mainForm");
		form.setParameter("visitDate", "11/21/2005");
		form.setParameter("notes", "I like diet-coke ;");
		form.getButtonWithID("update").click();
		wr = wc.getCurrentPage();
		assertTrue(wr.getText().contains("Information Successfully Updated"));
		assertLogged(TransactionType.ER_VISIT_CREATE, 9000000006L, 1L, "Incident Report");
	}
	
	/*
	 * Authenticate HCP
	 * MID 8000000009
	 * Password: uappass1
	 * Choose Document Office Visit
	 * Enter Patient MID 1 and confirm
	 * Choose to document new office vist.
	 * Enter Fields:
	 * Date: 2005-11-21
	 */
	public void testUpdateOfficeVisitOctothorpe() throws Exception {
		// login UAP
		WebConversation wc = login("9000000006", "pw");
		WebResponse wr = wc.getCurrentPage();
		assertEquals("iTrust - ER Home", wr.getTitle());
		// click Document Office Visit
		wr = wr.getLinkWith("Document Incident Report").click();
		// choose patient 1
		WebForm patientForm = wr.getForms()[0];
		patientForm.getScriptableObject().setParameterValue("UID_PATIENTID", "1");
		patientForm.getButtons()[1].click();
		wr = wc.getCurrentPage();
		assertEquals(ADDRESS + "auth/hcp-uap/documentOfficeVisit.jsp", wr.getURL().toString());
		// click Yes, Document Office Visit
		WebForm form = wr.getForms()[0];
		form.getButtons()[0].click();
		wr = wc.getCurrentPage();
		assertEquals("iTrust - Document ER Visit", wr.getTitle());
		// add a new office visit
		form = wr.getFormWithID("mainForm");
		form.setParameter("visitDate", "11/21/2005");
		form.setParameter("notes", "I like diet-coke #");
		form.getButtonWithID("update").click();
		wr = wc.getCurrentPage();
		assertTrue(wr.getText().contains("Information Successfully Updated"));
		assertLogged(TransactionType.ER_VISIT_CREATE, 9000000006L, 1L, "Incident Report");
	}
}
