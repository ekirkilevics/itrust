package edu.ncsu.csc.itrust.action;

import edu.ncsu.csc.itrust.beans.DiagnosisBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.ICDCodesDAO;
import edu.ncsu.csc.itrust.dao.mysql.TransactionDAO;
import edu.ncsu.csc.itrust.enums.TransactionType;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.iTrustException;
import edu.ncsu.csc.itrust.validate.DiagnosisBeanValidator;

/**
 * Handles updating the ICD Code (Diagnosis) List Used by editICDCodes.jsp
 * 
 * @author laurenhayward
 * 
 */
public class UpdateICDCodeListAction {
	private long performerID = 0;
	private ICDCodesDAO icdDAO;
	private TransactionDAO transDAO;
	private DiagnosisBeanValidator validator = new DiagnosisBeanValidator();

	/**
	 * Set up
	 * 
	 * @param factory
	 * @param performerID
	 */
	public UpdateICDCodeListAction(DAOFactory factory, long performerID) {
		this.performerID = performerID;
		transDAO = factory.getTransactionDAO();
		icdDAO = factory.getICDCodesDAO();
	}

	/**
	 * Adds a new ICD code (diagnosis) based on the DiagnosisBean passed as a param
	 * 
	 * @param diagn
	 *            The new diagnosis (ICD code)
	 * @return Status message
	 * @throws FormValidationException
	 */
	public String addICDCode(DiagnosisBean diagn) throws FormValidationException {
		validator.validate(diagn);
		try {
			if (icdDAO.addICDCode(diagn)) {
				transDAO.logTransaction(TransactionType.MANAGE_DIAGNOSIS_CODE, performerID, 0L,
						"added ICD code " + diagn.getICDCode());
				return "Success: " + diagn.getICDCode() + " - " + diagn.getDescription() + " added";
			} else
				return "unexpected error"; // TODO: needs better error message
		} catch (DBException e) {
			e.printStackTrace();
			return e.getMessage();
		} catch (iTrustException e) {
			return e.getMessage();
		}
	}

	/**
	 * Updates a diagnosis with new information from the DiagnosisBean passed as a param
	 * 
	 * @param diagn
	 *            new information to update (but same code)
	 * @return Status message
	 * @throws FormValidationException
	 */
	public String updateInformation(DiagnosisBean diagn) throws FormValidationException {
		validator.validate(diagn);
		try {
			int rows = icdDAO.updateCode(diagn);
			if (0 == rows) {
				return "Error: Code not found.";
			} else {
				transDAO.logTransaction(TransactionType.MANAGE_DIAGNOSIS_CODE, performerID, 0L,
						"updated ICD code " + diagn.getICDCode());
				return "Success: " + rows + " row(s) updated";
			}
		} catch (DBException e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}

}
