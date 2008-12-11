package edu.ncsu.csc.itrust.action;

import java.util.List;
import edu.ncsu.csc.itrust.beans.Email;
import edu.ncsu.csc.itrust.beans.PersonnelBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.FakeEmailDAO;
import edu.ncsu.csc.itrust.dao.mysql.PersonnelDAO;
import edu.ncsu.csc.itrust.dao.mysql.TransactionDAO;
import edu.ncsu.csc.itrust.enums.TransactionType;
import edu.ncsu.csc.itrust.exception.iTrustException;

/**
 * Handles retrieving personnel beans for a given personnel Used by viewPersonnel.jsp
 * 
 * @author laurenhayward
 * 
 */
public class ViewPersonnelAction {
	private PersonnelDAO personnelDAO;
	private TransactionDAO transDAO;
	private FakeEmailDAO emailDAO;
	private long loggedInMID;

	/**
	 * Set up defaults
	 * 
	 * @param factory
	 * @param loggedInMID
	 */
	public ViewPersonnelAction(DAOFactory factory, long loggedInMID) {
		this.emailDAO = factory.getFakeEmailDAO();
		this.personnelDAO = factory.getPersonnelDAO();
		this.transDAO = factory.getTransactionDAO();
		this.loggedInMID = loggedInMID;
	}

	/**
	 * Retrieves a PersonnelBean for the mid passed as a param
	 * 
	 * @param input
	 *            the mid for which the PersonnelBean will be returned
	 * @return PersonnelBean
	 * @throws iTrustException
	 */
	public PersonnelBean getPersonnel(String input) throws iTrustException {
		try {
			long mid = Long.valueOf(input);
			PersonnelBean personnel = personnelDAO.getPersonnel(mid);
			if (personnel != null) {
				transDAO.logTransaction(TransactionType.ENTER_EDIT_DEMOGRAPHICS, loggedInMID, mid,
						"Patient viewed personnel " + mid);
				return personnel;
			} else
				throw new iTrustException("No personnel record exists for this MID");
		} catch (NumberFormatException e) {
			e.printStackTrace();
			throw new iTrustException("MID not a number");
		}
	}
	
	/**
	 * Returns a PatientBean for the currently logged in personnel
	 * 
	 * @return
	 * @throws iTrustException
	 */
	public List<Email> getEmailHistory() throws iTrustException {
		return emailDAO.getFakeEmailsByPerson(personnelDAO.getPersonnel(loggedInMID).getEmail());
	}

}
