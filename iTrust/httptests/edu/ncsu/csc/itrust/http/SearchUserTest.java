package edu.ncsu.csc.itrust.http;


import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;


public class SearchUserTest extends iTrustHTTPTest{
	
	protected void setUp() throws Exception {
		gen.clearAllTables();
		gen.hcp0();
		gen.patient1();
		gen.patient2();
	}
	
	public void testGetPatient() throws Exception {
		gen.clearAllTables();
		gen.standardData();
		WebConversation wc = login("9000000000", "pw");
		WebResponse wr = wc.getCurrentPage();
		assertEquals("iTrust - HCP Home", wr.getTitle());
		wr = wr.getLinkWith("Basic Health Information").click();
		assertEquals("iTrust - Please Select a Patient", wr.getTitle());
		wr.getForms()[1].setParameter("FIRST_NAME","Random");
		wr.getForms()[1].setParameter("LAST_NAME", "Person");
		wr.getForms()[1].getButtons()[0].click();
		wr = wc.getCurrentPage();
		WebTable wt = wr.getTableStartingWith("MID");
		assertEquals("MID", wt.getCellAsText(0, 0));
		assertEquals("", wt.getCellAsText(1, 0));
		assertEquals("Random", wt.getCellAsText(1, 1));
		assertEquals("Person", wt.getCellAsText(1, 2));
	}
	public void testGetPatient2() throws Exception {
		gen.clearAllTables();
		gen.standardData();
		WebConversation wc = login("9000000000", "pw");
		WebResponse wr = wc.getCurrentPage();
		assertEquals("iTrust - HCP Home", wr.getTitle());
		wr = wr.getLinkWith("Basic Health Information").click();
		assertEquals("iTrust - Please Select a Patient", wr.getTitle());
		wr.getForms()[1].setParameter("FIRST_NAME","Andy");
		wr.getForms()[1].getButtons()[0].click();
		wr = wc.getCurrentPage();
		WebTable wt = wr.getTableStartingWith("MID");
		assertEquals("MID", wt.getCellAsText(0, 0));
		assertEquals("", wt.getCellAsText(1, 0));
		assertEquals("Andy", wt.getCellAsText(1, 1));
		assertEquals("Programmer", wt.getCellAsText(1, 2));
	}
	public void testGetPatient3() throws Exception {
		gen.clearAllTables();
		gen.standardData();
		WebConversation wc = login("9000000000", "pw");
		WebResponse wr = wc.getCurrentPage();
		assertEquals("iTrust - HCP Home", wr.getTitle());
		wr = wr.getLinkWith("UAPs").click();
		assertEquals("iTrust - Please Select a Personnel", wr.getTitle());
		wr.getForms()[1].setParameter("FIRST_NAME","Kelly");
		wr.getForms()[1].setParameter("LAST_NAME", "Doctor");
		wr.getForms()[1].getButtons()[1].click();
		wr = wc.getCurrentPage();
		WebTable wt = wr.getTableStartingWith("MID");
		assertEquals("MID", wt.getCellAsText(0, 0));
		assertEquals("", wt.getCellAsText(1, 0));
		assertEquals("Kelly", wt.getCellAsText(1, 1));
		assertEquals("Doctor", wt.getCellAsText(1, 2));
	}
	



}
