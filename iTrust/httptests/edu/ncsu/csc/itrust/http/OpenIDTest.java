package edu.ncsu.csc.itrust.http;

import com.meterware.httpunit.HttpUnitOptions;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebResponse;
import edu.ncsu.csc.itrust.enums.TransactionType;

public class OpenIDTest extends iTrustHTTPTest {

	private static final String GOOGLE_EMAIL = "iTrustKellyDoctor@gmail.com";
	private static final String GOOGLE_PW = "andyprogrammer";
	
	protected void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.standardData();
		HttpUnitOptions.setScriptingEnabled(false);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testOpenIDLoginFail()throws Exception{
		// begin at the iTrust home page
		WebConversation wc = new WebConversation();
		WebResponse wr = wc.getResponse(ADDRESS);
		wr = wr.getLinkWithImageText("Login with Google").click();
		
		//iTrustKellyDoctor@gmail.com
		//andyprogrammer
		wr.getForms()[0].setParameter("Email", GOOGLE_EMAIL);
		wr.getForms()[0].setParameter("Passwd", GOOGLE_PW);
		wr = wr.getForms()[0].submit();
		
		assertTrue(wr.getText().contains("invalid"));
		assertNotLogged(TransactionType.OPENID_LOGIN, 9000000000L, 0, "");
	}
	
	public void testOpenIDLinkAccount()throws Exception{
		WebConversation wc = login("9000000000", "pw");
		WebResponse wr = wc.getCurrentPage();
		
		wr = wr.getLinkWith("OpenID").click();
		wr = wr.getLinkWithImageText("Google").click();
		
		wr.getForms()[0].setParameter("Email", GOOGLE_EMAIL);
		wr.getForms()[0].setParameter("Passwd", GOOGLE_PW);
		wr = wr.getForms()[0].submit();
		
		assertLogged(TransactionType.OPENID_LINK, 9000000000L, 0,"");
		assertTrue(wr.getText().contains("has been linked"));
	}
	
	public void testLoginWithOpenID()throws Exception{
		WebConversation wc = login("9000000000", "pw");
		WebResponse wr = wc.getCurrentPage();
		
		wr = wr.getLinkWith("OpenID").click();
		wr = wr.getLinkWithImageText("Google").click();
		
		wr.getForms()[0].setParameter("Email", GOOGLE_EMAIL);
		wr.getForms()[0].setParameter("Passwd", GOOGLE_PW);
		wr = wr.getForms()[0].submit();
		
		assertLogged(TransactionType.OPENID_LINK, 9000000000L, 0,"");
		assertTrue(wr.getText().contains("has been linked"));
		
		wr = wr.getLinkWith("Logout").click();
			
		wr = wr.getLinkWithImageText("Login with Google").click();
		
		assertFalse(wr.getText().contains("invalid"));
		assertLogged(TransactionType.OPENID_LOGIN, 9000000000L, 0, "");
		assertLogged(TransactionType.LOGIN_SUCCESS, 9000000000L, 9000000000L,"");
	}
	
	public void testLinkWithUsedOpenID()throws Exception{
		WebConversation wc = login("9000000000", "pw");
		WebResponse wr = wc.getCurrentPage();
		
		wr = wr.getLinkWith("OpenID").click();
		wr = wr.getLinkWithImageText("Google").click();
		
		wr.getForms()[0].setParameter("Email", GOOGLE_EMAIL);
		wr.getForms()[0].setParameter("Passwd", GOOGLE_PW);
		wr = wr.getForms()[0].submit();
		
		assertLogged(TransactionType.OPENID_LINK, 9000000000L, 0,"");
		assertTrue(wr.getText().contains("has been linked"));
		
		wr = wr.getLinkWith("Logout").click();
			
		
		wc = login("9000000001", "pw");
		wr = wc.getCurrentPage();
		
		wr = wr.getLinkWith("OpenID").click();
		wr = wr.getLinkWithImageText("Google").click();
		
		wr.getForms()[0].setParameter("Email", GOOGLE_EMAIL);
		wr.getForms()[0].setParameter("Passwd", GOOGLE_PW);
		wr = wr.getForms()[0].submit();
		
		assertNotLogged(TransactionType.OPENID_LINK, 9000000001L, 0,"");
		assertTrue(wr.getText().contains("Error: This openID is already associated with an iTrust account."));
		
	}
}
