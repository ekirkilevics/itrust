package edu.ncsu.csc.itrust.epidemic;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import edu.ncsu.csc.itrust.beans.DiagnosisCount;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.enums.State;
import edu.ncsu.csc.itrust.exception.DBException;

/**
 * Abstract class for detecting epidemics. Provides some helpful methods for detecting epidemics.
 * 
 * @author Andy
 * 
 */
abstract public class EpidemicDetector {
	public static final int WEEK_MS = 1000 * 60 * 60 * 24 * 7;
	protected DAOFactory factory;
	protected String zip;
	private List<DiagnosisCount> diagnosisCounts;
	private Date week0;

	protected EpidemicDetector(DAOFactory factory, String zip, State state, Date from, int weeksBack)
			throws DBException {
		this.factory = factory;
		this.week0 = new Date(from.getTime() - ((long) weeksBack * WEEK_MS));
		this.zip = zip;
		diagnosisCounts = factory.getEpidemicDAO().getDiagnosisCounts(getIcdLower(), getIcdUpper(), zip,
				state, from, weeksBack);
	}

	abstract public double getIcdLower();

	abstract public double getIcdUpper();

	abstract protected double getWeeklyThreshold(Date currentDate) throws DBException;

	public List<DiagnosisCount> getDiagnosisCounts() throws DBException {
		return diagnosisCounts;
	}

	/**
	 * Checks for diagnosis count above the threshold for two consecutive weeks over the timespan. Now the DAO
	 * returns a count of diagnoses for a given week span. That week span is 7 days subtracted from the given
	 * date "from". So if it's the 28th, and we're looking at 3 weeks back, then week 0 is the 7th. That
	 * should explain the confusing transformations over the variable "i"
	 * 
	 * @return message - empty if there's no epidemic, description of weeks if there is an epidemic
	 * @throws DBException
	 */
	public String checkForEpidemic() throws DBException {
		String message = "";
		for (int i = 0; i < diagnosisCounts.size() - 1; i++) {
			if (weekHasEpidemic(i) && weekHasEpidemic(i + 1)) { // two consecutive weeks
				if (message.equals("")) // then we have SOME epidemic, maybe more
					message = "Epidemic detected for weeks of: ";
				message += new SimpleDateFormat("MM/dd/yyyy").format(getWeekDate(i)) + " to "
						+ new SimpleDateFormat("MM/dd/yyyy").format(getWeekDate(i + 2)) + ", ";
			}
		}
		return chopOffLast2(message);
	}

	// Just a formatting trick for listing things with commas in between
	private String chopOffLast2(String message) {
		return message.length() == 0 ? message : message.substring(0, message.length() - 2);
	}

	// Check against the threshold.
	private boolean weekHasEpidemic(int i) throws DBException {
		return diagnosisCounts.get(i).getNumInRegion() > getWeeklyThreshold(getWeekDate(i));
	}

	// Calculates the date from i. For example, if it's the 13th, and we're looking at 1 week back,
	// then for week i=0, the date is the 6th.
	private Date getWeekDate(long i) {
		return new Date(week0.getTime() + i * WEEK_MS);
	}
}
