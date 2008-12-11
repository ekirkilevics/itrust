package edu.ncsu.csc.itrust.dao.fakeemail;

import java.util.Arrays;
import java.util.List;
import junit.framework.TestCase;
import edu.ncsu.csc.itrust.EmailUtil;
import edu.ncsu.csc.itrust.beans.Email;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.testutils.EvilDAOFactory;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;

public class FakeEmailTest extends TestCase {
	DAOFactory factory = TestDAOFactory.getTestInstance();

	@Override
	protected void setUp() throws Exception {
		TestDataGenerator gen = new TestDataGenerator();
		
		gen.clearFakeEmail();
		gen.fakeEmail();
	}

	public void testListAllEmails() throws Exception {
		List<Email> emails = factory.getFakeEmailDAO().getAllFakeEmails();
		assertEquals(4, emails.size());
		Email email = getTestEmail();
		new EmailUtil(factory).sendEmail(email);
		emails = factory.getFakeEmailDAO().getAllFakeEmails();
		assertEquals(5, emails.size());
		assertEquals(getTestEmail(), emails.get(0));
	}
	public void testListEmailsByPerson() throws Exception {
		List<Email> emails = factory.getFakeEmailDAO().getFakeEmailsByPerson("gstormcrow@iTrust.org");
		assertEquals(2, emails.size());
		assertEquals("this is an email", emails.get(0).getSubject());
		assertEquals("this is another email", emails.get(1).getSubject());
	}

	public void testFindWithString() throws Exception {
		factory.getFakeEmailDAO().sendFakeEmail(getTestEmail());
		factory.getFakeEmailDAO().sendFakeEmail(getTestEmail());
		Email other = getTestEmail();
		other.setBody("");
		factory.getFakeEmailDAO().sendFakeEmail(other  );
		List<Email> emails = factory.getFakeEmailDAO().getEmailWithBody("is the");
		assertEquals(2,emails.size());
		assertEquals(getTestEmail(), emails.get(0));
		assertEquals(getTestEmail(), emails.get(1));
	}

	public void testGetAllException() throws Exception {
		factory = EvilDAOFactory.getEvilInstance();
		try {
			factory.getFakeEmailDAO().getAllFakeEmails();
			fail("exception should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}
	public void testGetPersonException() throws Exception {
		factory = EvilDAOFactory.getEvilInstance();
		try {
			factory.getFakeEmailDAO().getFakeEmailsByPerson("gstormcrow@iTrust.org");
			fail("exception should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}

	public void testSendException() throws Exception {
		factory = EvilDAOFactory.getEvilInstance();
		try {
			factory.getFakeEmailDAO().sendFakeEmail(new Email());
			fail("exception should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}

	private Email getTestEmail() {
		Email email = new Email();
		email.setBody("this is the body");
		email.setFrom("from address");
		email.setSubject("this is the subject");
		email.setToList(Arrays.asList("first", "second", "third"));
		return email;
	}

}
