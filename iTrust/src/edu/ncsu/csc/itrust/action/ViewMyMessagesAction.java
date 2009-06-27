package edu.ncsu.csc.itrust.action;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import edu.ncsu.csc.itrust.beans.MessageBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.MessageDAO;
import edu.ncsu.csc.itrust.dao.mysql.PatientDAO;
import edu.ncsu.csc.itrust.dao.mysql.PersonnelDAO;
import edu.ncsu.csc.itrust.exception.iTrustException;

/**
 * Action class for ViewMyMessages.jsp
 *
 */
public class ViewMyMessagesAction {
	private long loggedInMID;
	private PatientDAO patientDAO;
	private PersonnelDAO personnelDAO;
	private MessageDAO messageDAO;

	/**
	 * Set up defaults
	 * 
	 * @param factory
	 * @param loggedInMID
	 */
	public ViewMyMessagesAction(DAOFactory factory, long loggedInMID) {
		this.loggedInMID = loggedInMID;
		this.patientDAO = factory.getPatientDAO();
		this.personnelDAO = factory.getPersonnelDAO();
		this.messageDAO = factory.getMessageDAO();
	}
	
	/**
	 * Gets all the messages fo rhte logged in user
	 * 
	 * @return a list of all the user's messages
	 * @throws SQLException
	 */
	public List<MessageBean> getAllMyMessages() throws SQLException {
		List<MessageBean> messages = new ArrayList<MessageBean>();
		messages = messageDAO.getMessagesFor(loggedInMID);
		return messages;
	}
	
	/**
	 * Gets a patient's name from their MID
	 * 
	 * @param mid the MID of the patient
	 * @return the patient's name
	 * @throws iTrustException
	 */
	public String getPatientName(long mid) throws iTrustException {
		return patientDAO.getName(mid);
	}
	
	/**
	 * Gets a personnel's name from their MID
	 * 
	 * @param mid the MID of the personnel
	 * @return the personnel's name
	 * @throws iTrustException
	 */
	public String getPersonnelName(long mid) throws iTrustException {
		return personnelDAO.getName(mid);
	}
}
