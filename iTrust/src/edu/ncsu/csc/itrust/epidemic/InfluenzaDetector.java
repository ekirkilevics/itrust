package edu.ncsu.csc.itrust.epidemic;

import java.util.Date;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.enums.State;
import edu.ncsu.csc.itrust.exception.DBException;

/**
 * Strategy for detecting an influenza epidemic, base on a weekly count exceeding a threshold. The threshold
 * is calculated on a formula found in a paper (see requirements).
 * 
 * @author Andy
 * 
 */
public class InfluenzaDetector extends EpidemicDetector {
	public static final double ICD_LOWER = 487.0;
	public static final double ICD_UPPER = 488.0;

	public InfluenzaDetector(DAOFactory factory, String zip, State state, Date from, int weeksBack)
			throws DBException {
		super(factory, zip, state, from, weeksBack);
	}

	@Override
	public double getIcdLower() {
		return ICD_LOWER;
	}

	@Override
	public double getIcdUpper() {
		return ICD_UPPER;
	}

	@Override
	protected double getWeeklyThreshold(Date currentDate) throws DBException {
		return calcThreshold(calcT(currentDate));
	}

	private double calcThreshold(double t) {
		return 65.34 + 0.271 * t + 3.45 * Math.sin(2 * Math.PI * t / 52.0) + 8.41
				* Math.cos(2 * Math.PI * t / 52.0);
	}

	private static long baseDateTime = 883890000000L; // see comment below

	private long calcT(Date currentDate) {
		return (currentDate.getTime() - baseDateTime) / EpidemicDetector.WEEK_MS;
	}
	/*
	 * Code for calculating the ms time of 01/04/1998 - try it out yourself!! long baseTime = 0L; try {
	 * baseDateTime = new SimpleDateFormat("MM/dd/yyyy").parse("01/04/1998").getTime(); } catch
	 * (ParseException e) { e.printStackTrace(); } System.out.println(baseDateTime);
	 */
}
