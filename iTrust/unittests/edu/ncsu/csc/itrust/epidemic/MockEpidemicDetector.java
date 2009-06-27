package edu.ncsu.csc.itrust.epidemic;

import java.util.Date;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.enums.State;
import edu.ncsu.csc.itrust.exception.DBException;

public class MockEpidemicDetector extends EpidemicDetector {
	private int thresh = 1;

	public MockEpidemicDetector(DAOFactory factory, String zip, State state,
			Date from, int weeksBack) throws DBException {
		super(factory, zip, state, from, weeksBack);
	}

	@Override
	public double getIcdLower() {
		return 84.0;
	}

	@Override
	public double getIcdUpper() {
		return 85.0;
	}

	@Override
	protected double getWeeklyThreshold(Date currentDate) {
		return thresh;
	}

	public int getThresh() {
		return thresh;
	}

	public void setThresh(int thresh) {
		this.thresh = thresh;
	}
}
