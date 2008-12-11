package edu.ncsu.csc.itrust.epidemic;

import java.util.Date;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.enums.State;
import edu.ncsu.csc.itrust.exception.DBException;

/**
 * Strategy for detecting a malaria epidemic, based on a weekly count exceeding a threshold. The threshold is
 * calculated by doing a percentage on the current average.
 * 
 * @author Andy
 * 
 */
public class MalariaDetector extends EpidemicDetector {
	public static final double ICD_UPPER = 85.0;
	public static final double ICD_LOWER = 84.0;
	private double percentage = 1.0; // if we've had more this year than before

	public MalariaDetector(DAOFactory factory, String zip, State state, Date from, int weeksBack)
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

	public void setPercentage(double percentage) {
		this.percentage = percentage;
	}

	@Override
	protected double getWeeklyThreshold(Date currentDate) throws DBException {
		return percentage
				* factory.getEpidemicDAO().getWeeklyAverage(getIcdLower(), getIcdUpper(), zip, currentDate);
	}
}
