package edu.ncsu.csc.itrust.action;

import java.util.List;
import edu.ncsu.csc.itrust.beans.PatientBean;
import edu.ncsu.csc.itrust.beans.PersonnelBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.PatientDAO;
import edu.ncsu.csc.itrust.dao.mysql.PersonnelDAO;
import edu.ncsu.csc.itrust.dao.mysql.TransactionDAO;
import edu.ncsu.csc.itrust.enums.TransactionType;
import edu.ncsu.csc.itrust.exception.DBException;


public class SearchUsersAction {
	private PatientDAO patientDAO;
	private PersonnelDAO personnelDAO;
	private TransactionDAO transDAO;
	private long loggedInMID;

	/**
	 * Set up defaults
	 * 
	 * @param factory
	 * @param loggedInMID
	 */
	public SearchUsersAction(DAOFactory factory, long loggedInMID) {
		this.patientDAO = factory.getPatientDAO();
		this.personnelDAO = factory.getPersonnelDAO();
		this.transDAO = factory.getTransactionDAO();
		this.loggedInMID = loggedInMID;
	}
	

	public List<PersonnelBean> searchForPersonnelWithName(String firstName, String lastName) {
		
		try {	
			if("".equals(firstName))
				firstName = "%";
			if("".equals(lastName))
				lastName = "%";
			return personnelDAO.searchForPersonnelWithName(firstName, lastName);
		}
		catch (DBException e) {
			System.out.println("DB Exception from SearchUsersAction");
			e.printStackTrace();
			return null;
		}
	}
	
	
	public List<PatientBean> searchForPatientsWithName(String firstName, String lastName) {
	
		try {	
			if("".equals(firstName))
				firstName = "%";
			if("".equals(lastName))
				lastName = "%";
			return patientDAO.searchForPatientsWithName(firstName, lastName);
		}
		catch (DBException e) {
			System.out.println("DB Exception from SearchUsersAction");
			e.printStackTrace();
			return null;
		}
	}
}
