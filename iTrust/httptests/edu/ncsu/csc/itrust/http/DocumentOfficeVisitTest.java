package edu.ncsu.csc.itrust.http;

import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebForm;
import com.meterware.httpunit.WebResponse;

public class DocumentOfficeVisitTest extends iTrustHTTPTest {
	@Override
	protected void setUp() throws Exception {
		gen.clearAllTables();
		gen.uap1();
		gen.hcp0();
		gen.patient2();
		gen.patient1();
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
		WebConversation wc = login("8000000009", "uappass1");
		WebResponse wr = wc.getCurrentPage();
		assertEquals("iTrust - UAP Home", wr.getTitle());
		// click Document Office Visit
		wr = wr.getLinkWith("Document Office Visit").click();
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
		assertEquals("iTrust - Document Office Visit", wr.getTitle());
		// add a new office visit
		form = wr.getForms()[0];
		form.setParameter("visitDate", "11/21/2005");
		form.setParameter("notes", "I like diet-coke");
		form.getButtons()[1].click();
		wr = wc.getCurrentPage();
		assertTrue(wr.getText().contains("Information Updated Successfully"));
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
		WebConversation wc = login("9000000000", "pw");
		WebResponse wr = wc.getCurrentPage();
		assertEquals("iTrust - HCP Home", wr.getTitle());
		// click Document Office Visit
		wr = wr.getLinkWith("Document Office Visit").click();
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
		assertEquals("iTrust - Document Office Visit", wr.getTitle());
		// add a new office visit
		form = wr.getForms()[0];
		form.setParameter("visitDate", "11/02/2005");
		form.setParameter("notes", "Great Patient!");
		form.getButtons()[1].click();
		wr = wc.getCurrentPage();
		assertTrue(wr.getText().contains("Information Updated Successfully"));
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
		WebConversation wc = login("9000000000", "pw");
		WebResponse wr = wc.getCurrentPage();
		assertEquals("iTrust - HCP Home", wr.getTitle());
		// click Document Office Visit
		wr = wr.getLinkWith("Document Office Visit").click();
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
		assertEquals("iTrust - Document Office Visit", wr.getTitle());
		// add a new office visit
		form = wr.getForms()[0];
		form.setParameter("visitDate", "11/21/2005");
		form.setParameter("notes", "<script>alert('ha ha ha');</script>");
		form.getButtons()[1].click();
		wr = wc.getCurrentPage();
		assertTrue(wr.getText().contains("Notes: Up to 300 alphanumeric characters, with space, and other punctuation"));
	}
}
