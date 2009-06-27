package edu.ncsu.csc.itrust.action;

import java.sql.Timestamp;
import java.util.GregorianCalendar;
import edu.ncsu.csc.itrust.beans.MessageBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;
import junit.framework.TestCase;

public class MessageBeanTest extends TestCase {

	private MessageBean bean;
	@SuppressWarnings("unused")
	private DAOFactory factory;
//	private long mid = 2L;
//	private long hcpId = 9000000000L;

	protected void setUp() throws Exception {

		super.setUp();
		TestDataGenerator gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.standardData();

		this.factory = TestDAOFactory.getTestInstance();
		this.bean = new MessageBean();
	}
	
	public void testTo() {
		this.bean.setTo(2L);
		assertEquals(2L, this.bean.getTo());
	}
	
	public void testMessageId() {
		this.bean.setMessageId(2L);
		assertEquals(2L, this.bean.getMessageId());
	}

	public void testParentMessageId() {
		this.bean.setParentMessageId(101L);
		assertEquals(101L, this.bean.getParentMessageId());
	}
	
	public void testFrom() {
		this.bean.setFrom(2L);
		assertEquals(2L, this.bean.getFrom());
	}

	public void testBody() {
		this.bean.setBody("BOOTY CALL");
		assertEquals("BOOTY CALL", this.bean.getBody());
	}
	
	public void testSentDate() {
		GregorianCalendar gc = new GregorianCalendar();
		Timestamp timestamp = new Timestamp(gc.getTimeInMillis());
		this.bean.setSentDate(timestamp);
		assertEquals(gc.getTimeInMillis(), this.bean.getSentDate().getTime());
	}
	
/*
	public boolean equals(Object obj) {
		return obj != null && obj.getClass().equals(this.getClass()) && this.equals((MessageBean) obj);
	}

	@Override
	public int hashCode() {
		assert false : "hashCode not designed";
		return 42; // any arbitrary constant will do
	}

	private boolean equals(MessageBean other) {
		return (from == other.from) && body.equals(other.body) && (to == other.to);
	}

	@Override
	public String toString() {
		return "FROM: " + from + " TO: " + to + " BODY: " + body;
	}


 */
	
}
