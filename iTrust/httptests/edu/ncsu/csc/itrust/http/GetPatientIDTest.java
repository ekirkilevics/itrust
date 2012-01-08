package edu.ncsu.csc.itrust.http;

import java.text.SimpleDateFormat;
import java.util.Date;
import com.meterware.httpunit.Button;
import com.meterware.httpunit.TableCell;
import com.meterware.httpunit.TableRow;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebForm;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;
import edu.ncsu.csc.itrust.beans.ReferralBean;
import edu.ncsu.csc.itrust.enums.TransactionType;

public class GetPatientIDTest extends iTrustHTTPTest {

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.standardData();
	}
	
	public void testSelectPatientButton() throws Exception
	{
		gen.hcp4();
		gen.hcp5();
		gen.referral_sort_testdata();

		WebConversation wc = login("9000000003", "pw");
		WebResponse wr = wc.getCurrentPage();
		assertEquals("iTrust - HCP Home", wr.getTitle());
		
		wr = wr.getLinkWith("Patient Information").click();
		assertEquals("iTrust - Please Select a Patient", wr.getTitle());
		
		//click on the "Select Patient" button
		wr.getFormWithID("mainForm").getButtonWithID("selectPatientButton").click();
		wr = wc.getCurrentPage();

		assertEquals("iTrust - Please Select a Patient", wr.getTitle());
		
		assertFalse(wr.getText().contains("HTTP Status 500"));   
		assertFalse(wr.getText().contains("java.lang.NumberFormatException"));

		//click on the "Select Patient" button again
		wr.getFormWithID("mainForm").getButtonWithID("selectPatientButton").click();
		wr = wc.getCurrentPage();

		assertEquals("iTrust - Please Select a Patient", wr.getTitle());
		
		assertFalse(wr.getText().contains("HTTP Status 500"));   
		assertFalse(wr.getText().contains("java.lang.NumberFormatException"));
		assertFalse(wr.getText().contains("Viewing information for <b>null</b>"));
	}
	
}
