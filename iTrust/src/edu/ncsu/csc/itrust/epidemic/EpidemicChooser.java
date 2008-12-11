package edu.ncsu.csc.itrust.epidemic;

import java.util.Date;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.enums.State;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.FormValidationException;

/**
 * Use this to figure out which epidemic strategy to use
 * 
 * @author Andy
 * 
 */
public class EpidemicChooser {

	public EpidemicDetector chooseEpidemic(DAOFactory factory, String detector, String zip, State state,
			Date from, int weeksBack) throws DBException, FormValidationException {
		// TODO When lots of epidemics are being detected, switch to using a HashMap as a jump table.
		// You'll have to change away from using a lot of parameters in the constructor, but that's okay
		// TODO put all of these parameters in an EpidemicParameter bean
		if ("malaria".equals(detector)) {
			return new MalariaDetector(factory, zip, state, from, weeksBack);
		} else if ("influenza".equals(detector)) {
			return new InfluenzaDetector(factory, zip, state, from, weeksBack);
		}
		throw new FormValidationException("Please select a diagnosis to detect");
	}

	public double getICDLower(String detector) throws FormValidationException {
		if ("malaria".equals(detector)) {
			return MalariaDetector.ICD_LOWER;
		} else if ("influenza".equals(detector)) {
			return InfluenzaDetector.ICD_LOWER;
		}
		throw new FormValidationException("Please select a diagnosis to detect");
	}

	public double getICDUpper(String detector) throws FormValidationException {
		if ("malaria".equals(detector)) {
			return MalariaDetector.ICD_UPPER;
		} else if ("influenza".equals(detector)) {
			return InfluenzaDetector.ICD_UPPER;
		}
		throw new FormValidationException("Please select a diagnosis to detect");
	}
}
