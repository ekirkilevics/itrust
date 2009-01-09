package edu.ncsu.csc.itrust.http;

import java.util.List;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebForm;
import com.meterware.httpunit.WebResponse;
import edu.ncsu.csc.itrust.beans.Email;
import edu.ncsu.csc.itrust.dao.mysql.FakeEmailDAO;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;

/**
 * Use Case 30
 */
public class MessagingUseCaseTest extends iTrustHTTPTest {

	protected void setUp() throws Exception {
		super.setUp(); // clear tables is called in super
		gen.standardData();
	}

	public void testSendMessage() throws Exception {

		// Login
		WebConversation wc = login("9000000000", "pw");
		WebResponse wr = wc.getCurrentPage();
		wr = wr.getLinkWith("Send a Message").click();
		
		// Select Patient
		WebForm wf = wr.getFormWithID("mainForm");

		wf.getScriptableObject().setParameterValue("UID_PATIENTID", "2");
		wr = wf.submit();

		// Submit message
		wf = wr.getFormWithID("mainForm");
		wf.getScriptableObject().setParameterValue("messageBody", "We really need to have a visit.");
		wr = wf.submit();

		assertTrue(wr.getText().contains("Announcements"));

		// Check email
		FakeEmailDAO feDAO = new FakeEmailDAO(TestDAOFactory.getTestInstance());
		List<Email> eList = feDAO.getFakeEmailsByPerson("andy.programmer@gmail.com");
		
		boolean foundEmail = false;
		for(Email e:eList) {
			if ("A new message from Kelly Doctor".equals(e.getSubject())) {
				foundEmail = true;
				break;
			}
		}
		assertTrue(foundEmail);
		
		// Check log
		
		wr = wr.getLinkWith("Logout").click();
		wr = wr.getLinkWith("Log into iTrust").click();
	}

	public void testViewMyMessages() throws Exception {
		this.testSendMessage();
		
		// Login
		WebConversation wc = login("2", "pw");
		WebResponse wr = wc.getCurrentPage();
		wr = wr.getLinkWith("View My Messages").click();
		
		// Message List 
		wr = wr.getLinkWith("Reply").click();
		
		// Submit reply
		WebForm wf = wr.getFormWithID("mainForm");
		wf.getScriptableObject().setParameterValue("messageBody", "Indeed");
		wr = wf.submit();

		assertTrue(wr.getText().contains("Announcements"));

		wr = wr.getLinkWith("Logout").click();
		wr = wr.getLinkWith("Log into iTrust").click();

		// Check email
		FakeEmailDAO feDAO = new FakeEmailDAO(TestDAOFactory.getTestInstance());
		List<Email> eList = feDAO.getFakeEmailsByPerson("kdoctor@iTrust.org");
		
		boolean foundEmail = false;
		for(Email e:eList) {
			if ("A new message from Andy Programmer".equals(e.getSubject())) {
				foundEmail = true;
				break;
			}
		}
		assertTrue(foundEmail);
	}
}
