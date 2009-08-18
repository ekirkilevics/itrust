package edu.ncsu.csc.itrust;

import junit.framework.TestCase;

public class MessagesTest extends TestCase {

	public void testGetString() {
		assertEquals("Requested", Messages.getString("ReportRequestBean.requested"));
	}

}
