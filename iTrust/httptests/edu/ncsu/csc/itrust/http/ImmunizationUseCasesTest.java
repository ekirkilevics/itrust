package edu.ncsu.csc.itrust.http;

import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebForm;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;

/**
 * Use Cases 9, 11 & 17
 */
public class ImmunizationUseCasesTest extends iTrustHTTPTest {

	protected void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.standardData();
	}
	
	public void testDocumentAndViewImmunizations() throws Exception {

		boolean check = false;
		
		WebConversation wc = login("9000000003", "pw");
		WebResponse wr = wc.getCurrentPage();
		wr = wr.getLinkWith("Office Visit Reminders").click();
		assertEquals("iTrust - Visit Reminders", wr.getTitle());
		// Select "Immunization Needers"
		WebForm wf = wr.getFormWithID("reminderForm");
		wf.setParameter("ReminderType", "Immunization Needers");
		// Select "Get Reminders"
		wr = wf.submit();
		// Check for content
		WebTable[] tables = wr.getTables();
		
		for (WebTable t: tables) {
			if ("Patient Information".equals(t.getCellAsText(0, 0))) {
				if ("Koopa".equals(t.getCellAsText(1, 1))) {
					assertEquals("Bowser", t.getCellAsText(2, 1));
					assertEquals("Needs Immunization:    90371 Hepatitis B (birth) 90681 Rotavirus (6 weeks) 90696 Diphtheria, Tetanus, Pertussis (6 weeks) 90645 Haemophilus influenzae (6 weeks) 90669 Pneumococcal (6 weeks) 90712 Poliovirus (6 weeks)",
								t.getCellAsText(4, 1));
					check = true;
				}
				else if ("Peach".equals(t.getCellAsText(1, 1))) {
					assertEquals("Princess", t.getCellAsText(2,1));
					assertEquals("Needs Immunization:    90371 Hepatitis B (birth) 90681 Rotavirus (6 weeks) 90696 Diphtheria, Tetanus, Pertussis (6 weeks) 90645 Haemophilus influenzae (6 weeks) 90669 Pneumococcal (6 weeks) 90712 Poliovirus (6 weeks)",
								t.getCellAsText(4, 1));
					check = true;
				}
			}
		}
		assertTrue(check);
	}

	
	public void testViewImmunizationNeeders() throws Exception {
		
		boolean check = false;
		WebConversation wc = login("9000000000", "pw");
		WebResponse wr = wc.getCurrentPage();
		wr = wr.getLinkWith("Office Visit Reminders").click();
		assertEquals("iTrust - Visit Reminders", wr.getTitle());
		// Select "Immunization Needers"
		WebForm wf = wr.getFormWithID("reminderForm");
		wf.setParameter("ReminderType", "Immunization Needers");
		// Select "Get Reminders"
		wr = wf.submit();
		// Check for content
		WebTable[] tables = wr.getTables();
		
		for (WebTable t: tables) {
			if ("Patient Information".equals(t.getCellAsText(0, 0))) {
				if ("A".equals(t.getCellAsText(1, 1))) {
					assertEquals("Baby", t.getCellAsText(2, 1));
					assertEquals("Needs Immunization:    90371 Hepatitis B (6 months) 90681 Rotavirus (6 months) 90696 Diphtheria, Tetanus, Pertussis (15 weeks) 90669 Pneumococcal (12 months) 90649 Human Papillomavirus (9 years, 6 months)",
								t.getCellAsText(4, 1));
					check = true;
				}
				else if ("C".equals(t.getCellAsText(1, 1))) {
					assertEquals("Baby", t.getCellAsText(2, 1));
					assertEquals("Needs Immunization:    90371 Hepatitis B (1 month) 90696 Diphtheria, Tetanus, Pertussis (6 weeks) 90396 Varicella (12 months) 90633 Hepatits A (18 months)",
								t.getCellAsText(4, 1));
					check = true;
				}
			}
		}
		assertTrue(check);
	}
	
	public void testUpdateImmunizations() throws Exception {
		
		boolean check = false;
		WebConversation wc = login("9000000000", "pw");
		WebResponse wr = wc.getCurrentPage();
		wr = wr.getLinkWith("Document Office Visit").click();
		assertEquals("iTrust - Please Select a Patient", wr.getTitle());
		// Select patient 6
		WebForm wf = wr.getFormWithID("mainForm");
		wf.setParameter("UID_PATIENTID", "6");
		wr = wf.submit();
		assertEquals("iTrust - Document Office Visit", wr.getTitle());
		wr = wr.getLinkWith("07/10/2004").click();
		wf = wr.getFormWithID("mainForm");
		wf.setParameter("addImmunizationID", "90371");
		wr = wf.submit();
		wr = wr.getLinkWith("Office Visit Reminders").click();
		assertEquals("iTrust - Visit Reminders", wr.getTitle());
		// Select "Immunization Needers"
		wf = wr.getFormWithID("reminderForm");
		wf.setParameter("ReminderType", "Immunization Needers");
		// Select "Get Reminders"
		wr = wf.submit();
		// Check for content
		WebTable[] tables = wr.getTables();
		
		for (WebTable t: tables) {
			if ("Patient Information".equals(t.getCellAsText(0, 0))) {
				if ("A".equals(t.getCellAsText(1, 1))) {
					assertEquals("Baby", t.getCellAsText(2, 1));
					assertEquals("Needs Immunization:    90681 Rotavirus (6 months) 90696 Diphtheria, Tetanus, Pertussis (15 weeks) 90669 Pneumococcal (12 months) 90649 Human Papillomavirus (9 years, 6 months)",
								t.getCellAsText(4, 1));
					check = true;
				}
				else if ("C".equals(t.getCellAsText(1, 1))) {
					assertEquals("Baby", t.getCellAsText(2, 1));
					assertEquals("Needs Immunization:    90371 Hepatitis B (1 month) 90696 Diphtheria, Tetanus, Pertussis (6 weeks) 90396 Varicella (12 months) 90633 Hepatits A (18 months)",
								t.getCellAsText(4, 1));
					check = true;
				}
			}
		}
		assertTrue(check);
	}
	
	public void testViewImmunizationRecord() throws Exception {
		
		WebConversation wc = login("6", "pw");
		WebResponse wr = wc.getCurrentPage();
		wr = wr.getLinkWith("View My Records").click();
		assertEquals("iTrust - View My Records", wr.getTitle());
		WebTable table = wr.getTableStartingWith("Immunizations");
		assertEquals("90649", table.getCellAsText(2, 0));
		assertEquals("90649", table.getCellAsText(3, 0));
		assertEquals("90707", table.getCellAsText(4, 0));
		assertEquals("90396", table.getCellAsText(5, 0));
		assertEquals("90633", table.getCellAsText(6, 0));
		assertEquals("90645", table.getCellAsText(7, 0));
		assertEquals("90707", table.getCellAsText(8, 0));
		assertEquals("90396", table.getCellAsText(9, 0));
		assertEquals("90633", table.getCellAsText(10, 0));
		assertEquals("90696", table.getCellAsText(11, 0));
		assertEquals("90669", table.getCellAsText(12, 0));
		assertEquals("90712", table.getCellAsText(13, 0));
		assertEquals("90681", table.getCellAsText(14, 0));
		assertEquals("90696", table.getCellAsText(15, 0));
		assertEquals("90645", table.getCellAsText(16, 0));
		assertEquals("90669", table.getCellAsText(17, 0));
		assertEquals("90712", table.getCellAsText(18, 0));
		assertEquals("90681", table.getCellAsText(19, 0));
		assertEquals("90696", table.getCellAsText(20, 0));
		assertEquals("90645", table.getCellAsText(21, 0));
		assertEquals("90669", table.getCellAsText(22, 0));
		assertEquals("90712", table.getCellAsText(23, 0));
		assertEquals("90371", table.getCellAsText(24, 0));
		assertEquals("90371", table.getCellAsText(25, 0));
	}
	
	public void testViewImmunizationRecord2() throws Exception {
		
		WebConversation wc = login("2", "pw");
		WebResponse wr = wc.getCurrentPage();
		wr = wr.getLinkWith("View My Records").click();
		assertEquals("iTrust - View My Records", wr.getTitle());
		WebTable table = wr.getTableStartingWith("Immunizations");
		assertEquals("No Data", table.getCellAsText(2, 0));
		
	}
	
	public void testDocumentImmunization() throws Exception {
		
		WebConversation wc = login("9000000000", "pw");
		WebResponse wr = wc.getCurrentPage();
		wr = wr.getLinkWith("Document Office Visit").click();
		assertEquals("iTrust - Please Select a Patient", wr.getTitle());
		// Select patient 6
		WebForm wf = wr.getFormWithID("mainForm");
		wf.setParameter("UID_PATIENTID", "6");
		wr = wf.submit();
		assertEquals("iTrust - Document Office Visit", wr.getTitle());
		wr = wr.getLinkWith("07/10/2004").click();
		WebTable table = wr.getTableStartingWith("Immunizations");
		assertEquals("90649", table.getCellAsText(2, 0));
		
	}
	
	public void testDocumentImmunization2() throws Exception {
		
		WebConversation wc = login("9000000000", "pw");
		WebResponse wr = wc.getCurrentPage();
		wr = wr.getLinkWith("Document Office Visit").click();
		assertEquals("iTrust - Please Select a Patient", wr.getTitle());
		// Select patient 7
		WebForm wf = wr.getFormWithID("mainForm");
		wf.setParameter("UID_PATIENTID", "7");
		wr = wf.submit();
		assertEquals("iTrust - Document Office Visit", wr.getTitle());
		wr = wr.getLinkWith("05/10/2006").click();
		WebTable table = wr.getTableStartingWith("Immunizations");
		assertEquals("90696", table.getCellAsText(2, 0));
		wr = wr.getLinkWith("Remove").click();
		table = wr.getTableStartingWith("Immunizations");
		assertEquals("No immunizations on record", table.getCellAsText(2, 0));
		
	}
}
