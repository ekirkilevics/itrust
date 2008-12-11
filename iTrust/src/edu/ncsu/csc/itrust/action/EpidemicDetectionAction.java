package edu.ncsu.csc.itrust.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import edu.ncsu.csc.itrust.beans.forms.EpidemicForm;
import edu.ncsu.csc.itrust.beans.forms.EpidemicReturnForm;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.TransactionDAO;
import edu.ncsu.csc.itrust.enums.State;
import edu.ncsu.csc.itrust.enums.TransactionType;
import edu.ncsu.csc.itrust.epidemic.EpidemicChooser;
import edu.ncsu.csc.itrust.epidemic.EpidemicDetector;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.validate.EpidemicFormValidator;

/**
 * Detects an epidemic Used in epidemicGraph.jsp
 * 
 * @author laurenhayward
 * 
 */
public class EpidemicDetectionAction {
	private DAOFactory factory;
	private long loggedInMID;
	private TransactionDAO transDAO;

	/**
	 * Sets up defaults
	 * @param factory
	 * @param loggedInMID
	 */
	public EpidemicDetectionAction(DAOFactory factory, long loggedInMID) {
		this.factory = factory;
		this.loggedInMID = loggedInMID;
		this.transDAO = factory.getTransactionDAO();
	}

	/**
	 * Checks to see if there is an epidemic and makes calls to setup the graph Logs request biosurveillance
	 * 
	 * @param form
	 * @return
	 * @throws FormValidationException
	 * @throws DBException
	 */
	public EpidemicReturnForm execute(EpidemicForm form) throws FormValidationException, DBException {
		new EpidemicFormValidator().validate(form);
		EpidemicReturnForm rForm = new EpidemicReturnForm();
		rForm.setImageQuery(getImageQuery(form));
		if ("true".equals(form.getUseEpidemic())) {
			rForm.setUsedEpidemic(true);
			rForm.setEpidemicMessage(checkForEpidemic(form));
		} else
			rForm.setUsedEpidemic(false);
		transDAO.logTransaction(TransactionType.REQUEST_BIOSURVEILLANCE, loggedInMID);
		return rForm;
	}

	/**
	 * Creates a link to the image in question
	 * 
	 * @param form the data to create the image from
	 * @return a string pointing to the image
	 * @throws FormValidationException
	 */
	
	private String getImageQuery(EpidemicForm form) throws FormValidationException {
		double icdLower;
		double icdUpper;
		if ("true".equals(form.getUseEpidemic())) {
			EpidemicChooser chooser = new EpidemicChooser();
			icdLower = chooser.getICDLower(form.getDetector());
			icdUpper = chooser.getICDUpper(form.getDetector());
		} else {
			try {
				icdLower = Double.valueOf(form.getIcdLower());
				icdUpper = Double.valueOf(form.getIcdUpper());
			} catch (NumberFormatException nfe) {
				throw new FormValidationException("Please enter a valid range");
			}
		}
		return "date=" + form.getDate() + "&icdLower=" + icdLower + "&icdUpper=" + icdUpper + "&state="
				+ State.parse(form.getState()).getAbbrev() + "&weeksBack=" + form.getWeeksBack() + "&zip="
				+ form.getZip();
	}

	/**
	 * Checks for an epidemic
	 * 
	 * @param form the data to check
	 * @return information regarding the epidemic or an error message
	 * @throws FormValidationException
	 */
	private String checkForEpidemic(EpidemicForm form) throws FormValidationException {
		try {
			State state = State.parse(form.getState());
			Date from = new SimpleDateFormat("MM/dd/yyyy").parse(form.getDate());
			int weeksBack = Integer.valueOf(form.getWeeksBack());
			String epidemicString = chooseEpidemic(form.getDetector(), form.getZip(), state, from, weeksBack)
					.checkForEpidemic();
			return "".equals(epidemicString) ? "No epidemic detected" : epidemicString;
		} catch (DBException e) {
			e.printStackTrace();
			return "Error in epidemic detection";
		} catch (ParseException e) {
			e.printStackTrace();
			return "Error in epidemic detection";
		}
	}

	/**
	 * Selects the epidemic to check.
	 * 
	 * @param detector which detector to use
	 * @param zip zip code of the area to be checked
	 * @param state state that is being checked
	 * @param from the begining date to check
	 * @param weeksBack how many weeks back to check
	 * @return the detector for that epidemic
	 * @throws FormValidationException
	 * @throws DBException
	 */
	private EpidemicDetector chooseEpidemic(String detector, String zip, State state, Date from, int weeksBack)
			throws FormValidationException, DBException {
		return new EpidemicChooser().chooseEpidemic(factory, detector, zip, state, from, weeksBack);
	}
}
