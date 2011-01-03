package edu.ncsu.csc.itrust.action;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import edu.ncsu.csc.itrust.beans.UserPrefsBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.enums.TransactionType;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.iTrustException;
import edu.ncsu.csc.itrust.testutils.EvilDAOFactory;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;
import junit.framework.TestCase;
import java.util.Date;
import java.util.List;

public class UserPrefsActionTest extends TestCase {
	private UserPrefsAction action;
	private DAOFactory factory;
	private long mid = 1L;
	
	protected void setUp() throws Exception {
		TestDataGenerator gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.standardData();
		
		this.factory = TestDAOFactory.getTestInstance();
		this.action = new UserPrefsAction(factory, mid);
	}
	
	public void testUpdateUserPrefs() throws FormValidationException, SQLException, iTrustException {
		UserPrefsBean bean = new UserPrefsBean();
		bean.setThemeColor("501337");
		bean.setSecondaryColor("FFFFFF");
		bean.setMID(mid);
		action.updateUserPrefs(bean);
		assertTrue(action.getUserPrefs().getThemeColor().equals("501337"));
	}
	
	public void testUserPrefsExceptions() throws FormValidationException, SQLException, iTrustException {
		action = new UserPrefsAction(new EvilDAOFactory(), mid);
		UserPrefsBean bean = new UserPrefsBean();
		bean.setThemeColor("501337");
		bean.setSecondaryColor("FFFFFF");
		bean.setMID(mid);
		
		try {
			action.updateUserPrefs(bean);
			fail();
		} catch(DBException e) {
		}
	}

	public void testUpdateGlobalPrefs() throws FormValidationException, SQLException, iTrustException {
		UserPrefsBean bean = new UserPrefsBean();
		bean.setThemeColor("501337");
		bean.setSecondaryColor("FFFFFF");
		bean.setMID(mid);
		action.updateGlobalPrefs(bean);
		assertEquals(action.getDefaultColor().getThemeColor(),bean.getThemeColor());
	}
}
