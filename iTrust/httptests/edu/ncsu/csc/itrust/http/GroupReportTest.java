/**
 * 
 */
package edu.ncsu.csc.itrust.http;

import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebForm;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;
import edu.ncsu.csc.itrust.enums.TransactionType;

/**
 * @author Austin
 *
 */
public class GroupReportTest extends iTrustHTTPTest {

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
//		gen.icd9cmCodes();
//		gen.ndCodes();
//		gen.hospitals();
//		gen.hcp0();
//		gen.hcp1();
		gen.standardData();
		
		gen.clearLoginFailures();
	}

/*
 * matches acceptance test scenario
 */
	public void testViewGroupReportAcceptScenario() throws Exception {
		
		WebConversation wc = login("9000000000", "pw");
		WebResponse wr = wc.getCurrentPage();
		assertEquals("iTrust - HCP Home", wr.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
		
		wr = wr.getLinkWith("Group Report").click();
		assertEquals("iTrust - Generate Group Report", wr.getTitle());
		
		WebForm form = wr.getFormWithID("mainForm");
		form.setCheckbox("demographics", "GENDER", true);
		form.setCheckbox("demographics", "LOWER_AGE_LIMIT", true);
		form.setCheckbox("medical", "DIAGNOSIS_ICD_CODE", true);
		wr = form.submit();
		assertEquals("iTrust - Generate Group Report", wr.getTitle());
		
		WebForm form2 = wr.getFormWithID("mainForm2");
		form2.getScriptableObject().setParameterValue("GENDER", "Female");
		form2.getScriptableObject().setParameterValue("LOWER_AGE_LIMIT", "60");
		form2.getScriptableObject().setParameterValue("DIAGNOSIS_ICD_CODE", "715.09");
		wr = form2.submit();
		
		wr = wc.getCurrentPage();
		assertEquals("iTrust - View Group Report", wr.getTitle());
		assertLogged(TransactionType.GROUP_REPORT_VIEW, 9000000000L, 0L, "");
		
		assertTrue(wr.getText().contains("Random</div></td>"));
		assertTrue(wr.getText().contains("Person</div></td>"));
	}
	
	/*
	 * filters by demographic filters
	 */
	public void testViewGroupReportDemographic() throws Exception {
		
		WebConversation wc = login("9000000000", "pw");
		WebResponse wr = wc.getCurrentPage();
		assertEquals("iTrust - HCP Home", wr.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
		
		wr = wr.getLinkWith("Group Report").click();
		assertEquals("iTrust - Generate Group Report", wr.getTitle());
		
		WebForm form = wr.getFormWithID("mainForm");
		form.setCheckbox("demographics", "GENDER", true);
		form.setCheckbox("demographics", "FIRST_NAME", true);
		form.setCheckbox("demographics", "CONTACT_EMAIL", true);
		form.setCheckbox("demographics", "CITY", true);
		form.setCheckbox("demographics", "STATE", true);
		form.setCheckbox("demographics", "ZIP", true);
		form.setCheckbox("demographics", "INSURE_NAME", true);
		form.setCheckbox("demographics", "INSURE_ID", true);
		form.setCheckbox("demographics", "LOWER_AGE_LIMIT", true);
		form.setCheckbox("demographics", "UPPER_AGE_LIMIT", true);
		wr = form.submit();
		assertEquals("iTrust - Generate Group Report", wr.getTitle());
		
		WebForm form2 = wr.getFormWithID("mainForm2");
		form2.getScriptableObject().setParameterValue("GENDER", "Male");
		form2.getScriptableObject().setParameterValue("FIRST_NAME", "Baby");
		form2.getScriptableObject().setParameterValue("CONTACT_EMAIL", "fake@email.com");
		form2.getScriptableObject().setParameterValue("CITY", "Raleigh");
		form2.getScriptableObject().setParameterValue("STATE", "NC");
		form2.getScriptableObject().setParameterValue("ZIP", "27606");
		form2.getScriptableObject().setParameterValue("INSURE_NAME", "Aetna");
		form2.getScriptableObject().setParameterValue("INSURE_ID", "ChetumNHowe");
		form2.getScriptableObject().setParameterValue("LOWER_AGE_LIMIT", "10");
		form2.getScriptableObject().setParameterValue("UPPER_AGE_LIMIT", "30");
		wr = form2.submit();
		
		assertEquals("iTrust - View Group Report", wr.getTitle());
		assertLogged(TransactionType.GROUP_REPORT_VIEW, 9000000000L, 0L, "");
		
		assertTrue(wr.getText().contains("Baby</div></td>"));
		assertTrue(wr.getText().contains("B</div></td>"));
		assertTrue(wr.getText().contains("C</div></td>"));
	}

	/*
	 * filters by medical filters
	 */
	public void testViewGroupReportMedical() throws Exception {
		
		WebConversation wc = login("9000000000", "pw");
		WebResponse wr = wc.getCurrentPage();
		assertEquals("iTrust - HCP Home", wr.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
		
		wr = wr.getLinkWith("Group Report").click();
		assertEquals("iTrust - Generate Group Report", wr.getTitle());
		
		WebForm form = wr.getFormWithID("mainForm");
		form.setCheckbox("medical", "PROCEDURE", true);
		form.setCheckbox("medical", "ALLERGY", true);
		form.setCheckbox("medical", "CURRENT_PRESCRIPTIONS", true);
		form.setCheckbox("medical", "PASTCURRENT_PRESCRIPTIONS", true);
		form.setCheckbox("medical", "DIAGNOSIS_ICD_CODE", true);
		form.setCheckbox("medical", "LOWER_OFFICE_VISIT_DATE", true);
		form.setCheckbox("medical", "UPPER_OFFICE_VISIT_DATE", true);
		wr = form.submit();
		assertEquals("iTrust - Generate Group Report", wr.getTitle());
		
		WebForm form2 = wr.getFormWithID("mainForm2");
		form2.getScriptableObject().setParameterValue("PROCEDURE", "1270F");
		form2.getScriptableObject().setParameterValue("ALLERGY", "664662530");
		form2.getScriptableObject().setParameterValue("CURRENT_PRESCRIPTIONS", "647641512");
		form2.getScriptableObject().setParameterValue("PASTCURRENT_PRESCRIPTIONS", "009042407");
		form2.getScriptableObject().setParameterValue("DIAGNOSIS_ICD_CODE", "250.10");
		form2.getScriptableObject().setParameterValue("DIAGNOSIS_ICD_CODE", "15.00");
		form2.getScriptableObject().setParameterValue("LOWER_OFFICE_VISIT_DATE", "01/01/1990");
		form2.getScriptableObject().setParameterValue("UPPER_OFFICE_VISIT_DATE", "01/01/2012");
		wr = form2.submit();

		assertEquals("iTrust - View Group Report", wr.getTitle());
		assertLogged(TransactionType.GROUP_REPORT_VIEW, 9000000000L, 0L, "");
		
		assertTrue(wr.getText().contains("Andy</div></td>"));
		assertTrue(wr.getText().contains("Programmer</div></td>"));
	}

	/*
	 * filters by personnel filters
	 */
	public void testViewGroupReportPersonnel() throws Exception {
		
		WebConversation wc = login("9000000000", "pw");
		WebResponse wr = wc.getCurrentPage();
		assertEquals("iTrust - HCP Home", wr.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
		
		wr = wr.getLinkWith("Group Report").click();
		assertEquals("iTrust - Generate Group Report", wr.getTitle());
		
		WebForm form = wr.getFormWithID("mainForm");
		form.setCheckbox("personnel", "DLHCP", true);
		wr = form.submit();
		assertEquals("iTrust - Generate Group Report", wr.getTitle());
		
		WebForm form2 = wr.getFormWithID("mainForm2");
		form2.getScriptableObject().setParameterValue("DLHCP", "Gandalf Stormcrow");
		wr = form2.submit();

		assertEquals("iTrust - View Group Report", wr.getTitle());
		assertLogged(TransactionType.GROUP_REPORT_VIEW, 9000000000L, 0L, "");
		
		assertTrue(wr.getText().contains("Random</div></td>"));
		assertTrue(wr.getText().contains("Person</div></td>"));
		assertTrue(wr.getText().contains("Andy</div></td>"));
		assertTrue(wr.getText().contains("Programmer</div></td>"));
		assertTrue(wr.getText().contains("Care</div></td>"));
		assertTrue(wr.getText().contains("Needs</div></td>"));
		assertTrue(wr.getText().contains("Bowser</div></td>"));
		assertTrue(wr.getText().contains("Koopa</div></td>"));
		assertTrue(wr.getText().contains("Princess</div></td>"));
		assertTrue(wr.getText().contains("Peach</div></td>"));
	}
	
	public void testMID() throws Exception {
		
		WebConversation wc = login("9000000000", "pw");
		WebResponse wr = wc.getCurrentPage();
		assertEquals("iTrust - HCP Home", wr.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
		
		wr = wr.getLinkWith("Group Report").click();
		assertEquals("iTrust - Generate Group Report", wr.getTitle());
		
		WebForm form = wr.getFormWithID("mainForm");
		wr = form.submit();
		assertEquals("iTrust - Generate Group Report", wr.getTitle());
		
		WebForm form2 = wr.getFormWithID("mainForm2");
		wr = form2.submit();
		
		assertTrue(wr.getText().contains("<th>MID</th>"));
	}
	
	public void testGroupReportInvalidAge() throws Exception {
		
		WebConversation wc = login("9000000000", "pw");
		WebResponse wr = wc.getCurrentPage();
		assertEquals("iTrust - HCP Home", wr.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
		
		wr = wr.getLinkWith("Group Report").click();
		assertEquals("iTrust - Generate Group Report", wr.getTitle());
		
		WebForm form = wr.getFormWithID("mainForm");
		form.setCheckbox("demographics", "LOWER_AGE_LIMIT", true);
		form.setCheckbox("demographics", "UPPER_AGE_LIMIT", true);
		wr = form.submit();
		assertEquals("iTrust - Generate Group Report", wr.getTitle());
		
		WebForm form2 = wr.getFormWithID("mainForm2");
		form2.getScriptableObject().setParameterValue("LOWER_AGE_LIMIT", "-1");
		form2.getScriptableObject().setParameterValue("UPPER_AGE_LIMIT", "asdf");
		wr = form2.submit();
		
		assertTrue(wr.getText().contains("Invalid age"));
	}
	
public void testXMLCheckboxFalse() throws Exception {
		
		WebConversation wc = login("9000000000", "pw");
		WebResponse wr = wc.getCurrentPage();
		assertEquals("iTrust - HCP Home", wr.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
		
		wr = wr.getLinkWith("Group Report").click();
		assertEquals("iTrust - Generate Group Report", wr.getTitle());
		
		WebForm form = wr.getFormWithID("mainForm");
		wr = form.submit();
		assertEquals("iTrust - Generate Group Report", wr.getTitle());
		
		WebForm form2 = wr.getFormWithID("mainForm2");
		wr = form2.submit();
		
		try{
			wr = wr.getLinkWith("Download XML Report").click();
		}catch(NullPointerException e){
			//Exception is good
			return;
		}
		fail("Should have thrown NullPointerException.");
	}
}
