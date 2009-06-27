package edu.ncsu.csc.itrust;

import static org.junit.Assert.*;
import org.junit.Test;

public class MessagesTest {

	@Test
	public void testGetString() {
		assertEquals("Requested",Messages.getString("ReportRequestBean.requested"));
	}

}
