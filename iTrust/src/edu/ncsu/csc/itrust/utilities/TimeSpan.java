package edu.ncsu.csc.itrust.utilities;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Utility class to compare iTrust time ranges.
 */

public class TimeSpan {
	
	private Date start;
	private Date end;

	public final static String dateFormat = "MM/dd/yyyy HH:mm";

	public TimeSpan(Date date, long durationInMinutes) {
		start = (Date)date.clone();
		end = new Date(start.getTime() + (durationInMinutes * 60 * 1000));
	}
	
	public boolean intersects(TimeSpan span2) {
		long s1 = start.getTime();
		long e1 = end.getTime();
		long s2 = span2.getStart().getTime();
		long e2 = span2.getEnd().getTime();
		
		if ((s1 == s2) || (e1 == e2)) return true;
		
		if (inBetween(s1, s2, e2)) return true;
		if (inBetween(e1, s2, e2)) return true;
		if (inBetween(s2, s1, e1)) return true;
		if (inBetween(e1, s1, e1)) return true;

		return false;
	}
	
	private boolean inBetween(long a, long b, long c) {
		return ((a > b) && (a < c));
	}
	
	private Date getStart() {
		return start;
	}
	
	private Date getEnd() {
		return end;
	}
	
	public String getStartString() {
		return new SimpleDateFormat(dateFormat).format(start);
	}
	
	public String getEndString() {
		return new SimpleDateFormat(dateFormat).format(end);
	}

}
