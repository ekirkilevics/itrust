package edu.ncsu.csc.itrust.utilities;

import java.text.SimpleDateFormat;
import junit.framework.TestCase;

public class TimeSpanTest extends TestCase {
	
	public void testTimeSpan1() throws Exception {
		TimeSpan ts = new TimeSpan(new SimpleDateFormat(TimeSpan.dateFormat).parse("06/14/1967 12:05"), 25);
		assertEquals("06/14/1967 12:05", ts.getStartString());
		assertEquals("06/14/1967 12:30", ts.getEndString());
		ts = new TimeSpan(new SimpleDateFormat(TimeSpan.dateFormat).parse("06/14/1967 23:55"), 10);
		assertEquals("06/14/1967 23:55", ts.getStartString());
		assertEquals("06/15/1967 00:05", ts.getEndString());
	}

	public void testTimeSpan2() throws Exception {
		TimeSpan ts1 = new TimeSpan(new SimpleDateFormat(TimeSpan.dateFormat).parse("01/01/2008 12:00"), 30);
		TimeSpan ts2 = new TimeSpan(new SimpleDateFormat(TimeSpan.dateFormat).parse("01/01/2008 12:00"), 30);
		assertTrue(ts1.intersects(ts2));
		assertTrue(ts2.intersects(ts1));
		ts1 = new TimeSpan(new SimpleDateFormat(TimeSpan.dateFormat).parse("01/01/2008 12:00"), 30);
		ts2 = new TimeSpan(new SimpleDateFormat(TimeSpan.dateFormat).parse("01/01/2008 12:30"), 30);
		assertFalse(ts1.intersects(ts2));
		assertFalse(ts2.intersects(ts1));
		ts1 = new TimeSpan(new SimpleDateFormat(TimeSpan.dateFormat).parse("01/01/2008 12:00"), 30);
		ts2 = new TimeSpan(new SimpleDateFormat(TimeSpan.dateFormat).parse("01/02/2008 12:00"), 30);
		assertFalse(ts1.intersects(ts2));
		assertFalse(ts2.intersects(ts1));
		ts1 = new TimeSpan(new SimpleDateFormat(TimeSpan.dateFormat).parse("01/01/2008 12:00"), 60);
		ts2 = new TimeSpan(new SimpleDateFormat(TimeSpan.dateFormat).parse("01/01/2008 12:15"), 30);
		assertTrue(ts1.intersects(ts2));
		assertTrue(ts2.intersects(ts1));
		ts1 = new TimeSpan(new SimpleDateFormat(TimeSpan.dateFormat).parse("01/01/2008 12:00"), 16);
		ts2 = new TimeSpan(new SimpleDateFormat(TimeSpan.dateFormat).parse("01/01/2008 12:15"), 30);
		assertTrue(ts1.intersects(ts2));
		assertTrue(ts2.intersects(ts1));
	}
}
