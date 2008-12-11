package edu.ncsu.csc.itrust.epidemic.malaria;

import java.util.Date;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.enums.State;
import edu.ncsu.csc.itrust.epidemic.MalariaDetector;
import edu.ncsu.csc.itrust.exception.DBException;

public class MockMalariaDetector extends MalariaDetector {
	public MockMalariaDetector(DAOFactory factory, String zip, State state, Date from, int weeksBack)
			throws DBException {
		super(factory, zip, state, from, weeksBack);
	}

	public double getThreshold(Date currentDate) throws DBException {
		return super.getWeeklyThreshold(currentDate);
	}
}
