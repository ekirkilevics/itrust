package edu.ncsu.csc.itrust.action;

import edu.ncsu.csc.itrust.action.base.PersonnelBaseAction;
import edu.ncsu.csc.itrust.beans.UserPrefsBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.UserPrefsDAO;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.iTrustException;
import edu.ncsu.csc.itrust.validate.UserPrefsValidator;

/**
 * Edits the designated personnel Used by admin/editPersonnel.jsp, staff/editMyDemographics.jsp,
 * editPersonnel.jsp
 * 
 * @author laurenhayward
 * 
 */
public class UserPrefsAction {
	private long loggedInMID;
	private UserPrefsDAO userPrefsDAO;
	private UserPrefsValidator validator = new UserPrefsValidator();

	/**
	 * Super class validates the patient id
	 * 
	 * @param factory The DAOFactory used to create the DAOs for this action.
	 * @param loggedInMID The MID of the user editing this personnel.
	 * @param pidString The ID of the user being edited.
	 * @throws iTrustException
	 */
	public UserPrefsAction(DAOFactory factory, long loggedInMID) throws iTrustException {
		this.userPrefsDAO = factory.getUserPrefsDAO();
		this.loggedInMID = loggedInMID;
	}

	/**
	 * Takes information from the personnelForm param and updates the patient
	 * 
	 * @param personnelForm
	 *            PersonnelBean with new information
	 * @throws iTrustException
	 * @throws FormValidationException
	 */
	public void updateUserPrefs(UserPrefsBean userPrefsForm) throws iTrustException,
			FormValidationException {
		userPrefsForm.setMID(this.loggedInMID);
		validator.validate(userPrefsForm);
		userPrefsDAO.editUserPrefs(userPrefsForm);
	}
	
	/**
	 * Used by admin to change the global default color setting.
	 * 
	 * @param userPrefsForm PersonnelBean with new information
	 * @throws iTrustException
	 * @throws FormValidationException
	 */
	public void updateGlobalPrefs(UserPrefsBean userPrefsForm) throws iTrustException,
			FormValidationException {
		userPrefsForm.setMID(0);
		validator.validate(userPrefsForm);
		userPrefsDAO.editUserPrefs(userPrefsForm);
	}
	
	/**
	 * Takes information from the Form param and updates the patient
	 * 
	 * @param mid MID of the desired user's preferences.
	 * @throws iTrustException
	 * @throws FormValidationException
	 */
	public UserPrefsBean getUserPrefs() throws iTrustException,
			FormValidationException {
		return userPrefsDAO.getUserPrefs(this.loggedInMID);
	}
	
	/**
	 * Retrieves a UserPrefsBean for user MID 0, for default color.
	 * 
	 * @return UserPrefsBean containing the default color.
	 * @throws iTrustException
	 */
	public UserPrefsBean getDefaultColor() throws iTrustException {
		return userPrefsDAO.getUserPrefs(0);
	}
}
