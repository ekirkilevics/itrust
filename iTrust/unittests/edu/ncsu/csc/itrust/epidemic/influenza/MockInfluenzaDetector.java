package edu.ncsu.csc.itrust.epidemic.influenza;

import java.util.Date;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.enums.State;
import edu.ncsu.csc.itrust.epidemic.InfluenzaDetector;
import edu.ncsu.csc.itrust.exception.DBException;

public class MockInfluenzaDetector extends InfluenzaDetector {
	public MockInfluenzaDetector(DAOFactory factory, String zip, State state, Date from, int weeksBack)
			throws DBException {
		super(factory, zip, state, from, weeksBack);
	}

	public double getThreshold(Date currentDate) throws DBException {
		return super.getWeeklyThreshold(currentDate);
	}
}
