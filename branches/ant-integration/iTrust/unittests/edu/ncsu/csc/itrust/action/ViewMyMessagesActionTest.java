package edu.ncsu.csc.itrust.action;

import java.sql.SQLException;
import java.util.List;
import edu.ncsu.csc.itrust.beans.MessageBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.exception.iTrustException;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;
import junit.framework.TestCase;

public class ViewMyMessagesActionTest extends TestCase {

	private ViewMyMessagesAction action;
	private DAOFactory factory;
	private long mid = 2L;
	private long hcpId = 9000000000L;
	
	protected void setUp() throws Exception {
		super.setUp();
		
		TestDataGenerator gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.standardData();

		this.factory = TestDAOFactory.getTestInstance();
		this.action = new ViewMyMessagesAction(this.factory, this.mid);
	}
	
	public void testGetAllMyMessages() throws SQLException {
		List<MessageBean> mbList = action.getAllMyMessages();
		
		assertEquals(0, mbList.size());
		
		// Should send a message and recheck later.
	}

	public void testGetPatientName() throws iTrustException {
		assertEquals("Andy Programmer", action.getPatientName(this.mid));
	}
	
	public void testGetPersonnelName() throws iTrustException {
		assertEquals("Kelly Doctor", action.getPersonnelName(this.hcpId));
	}
	
}
