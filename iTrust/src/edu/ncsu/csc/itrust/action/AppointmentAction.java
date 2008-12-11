package edu.ncsu.csc.itrust.action;

import edu.ncsu.csc.itrust.beans.AppointmentBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.AppointmentDAO;
import edu.ncsu.csc.itrust.dao.mysql.TransactionDAO;
import edu.ncsu.csc.itrust.enums.TransactionType;
import edu.ncsu.csc.itrust.exception.DBException;
/**
 * Class for the myAppointments jsp.  Shows appointments and adds appointments.
 * 
 * @author 
 *
 */
public class AppointmentAction {
	private TransactionDAO transDAO;
	private AppointmentDAO apptDAO;
	long loggedInMID;

/**
 * Sets up the defaults for the class
 * 
 * @param factory factory for creating the defaults.
 * @param loggedInMID person currently logged in 
 * @ author 
 */
	public AppointmentAction(DAOFactory factory, long loggedInMID) {
		transDAO = factory.getTransactionDAO();
		apptDAO = factory.getAppointmentDAO();
		this.loggedInMID = loggedInMID;
	}
	
	/**
	 * Adds an appointment to the list
	 * 
	 * @param appointment  Appointment to be added
	 * @return The id of the newly added appointment
	 * @throws DBException
	 */
	public long addAppointment(AppointmentBean appointment) throws DBException {
		long appointmentID = apptDAO.addAppointment(appointment);
		transDAO.logTransaction(TransactionType.ADD_APPOINTMENT, loggedInMID, 
				(loggedInMID == appointment.getLHCPMID() ? appointment.getPatientMID() : appointment.getLHCPMID()), "appointment id: "
				+ appointmentID + " for " + appointment.getAppointmentDateString());
		return appointmentID;
	}
}
