package edu.ncsu.csc.itrust.http;

import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;

public class PersonnelHTTPTest extends iTrustHTTPTest {
	@Override
	protected void setUp() throws Exception {
		gen.clearAllTables();
		gen.standardData();
	}
	
	public void testViewPrescriptionRecords() throws Exception {
		WebConversation wc = login("9000000000", "pw");
		WebResponse wr = wc.getCurrentPage();
		wr = wr.getLinkWith("All Patients").click();
		assertEquals("iTrust - View All Patients", wr.getTitle());
		wr = wr.getLinkWith("Andy Programmer").click();
		assertEquals("iTrust - Edit Personal Health Record", wr.getTitle());
		wr = wc.getResponse("http://localhost:8080/iTrust/auth/hcp-uap/getPrescriptionReport.jsp");
		assertEquals("iTrust - Get Prescription Report", wr.getTitle());
		WebTable wt = wr.getTableStartingWith("ND Code");
		assertEquals("00904-2407", wt.getCellAsText(1, 0));
		assertEquals("Tetracycline", wt.getCellAsText(1, 1));
		assertEquals("10/10/2006 to 10/11/2006", wt.getCellAsText(1, 2));
		assertEquals("Kelly Doctor", wt.getCellAsText(1, 3));
		assertEquals("00904-2407", wt.getCellAsText(2, 0));
		assertEquals("Tetracycline", wt.getCellAsText(2, 1));
		assertEquals("10/10/2006 to 10/11/2006", wt.getCellAsText(2, 2));
		assertEquals("Kelly Doctor", wt.getCellAsText(2, 3));
		assertEquals("64764-1512", wt.getCellAsText(3, 0));
		assertEquals("Prioglitazone", wt.getCellAsText(3, 1));
		assertEquals("10/10/2006 to 10/11/2020", wt.getCellAsText(3, 2));
		assertEquals("Kelly Doctor", wt.getCellAsText(3, 3));
	}
}
