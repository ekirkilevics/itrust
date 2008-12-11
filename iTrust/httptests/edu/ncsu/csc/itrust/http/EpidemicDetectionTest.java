package edu.ncsu.csc.itrust.http;

import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebForm;
import com.meterware.httpunit.WebResponse;

public class EpidemicDetectionTest extends iTrustHTTPTest {
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.admin1();
		gen.hcp0();
		gen.cptCodes();
	}
	
	public void testEpidemicDetectionAction() throws Exception {
		WebConversation wc = login("9000000000", "pw");
		WebResponse wr = wc.getCurrentPage();
		assertEquals("iTrust - HCP Home", wr.getTitle());
		wr = wr.getLinkWith("Epidemic Detection").click();
		assertEquals("iTrust - Diagnostic Trends", wr.getTitle());
		WebForm form = wr.getForms()[0];
		form.getScriptableObject().setParameterValue("useEpidemic", "false");
		wr = wr.getForms()[0].submit();
		assertEquals("iTrust - Diagnostic Trends", wr.getTitle());
	}
}
