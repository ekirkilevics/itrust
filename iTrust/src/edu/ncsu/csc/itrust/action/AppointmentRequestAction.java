

package edu.ncsu.csc.itrust.action;

import java.util.Arrays;
import edu.ncsu.csc.itrust.EmailUtil;
import edu.ncsu.csc.itrust.beans.AppointmentBean;
import edu.ncsu.csc.itrust.beans.AppointmentRequestBean;
import edu.ncsu.csc.itrust.beans.Email;
import edu.ncsu.csc.itrust.beans.PatientBean;
import edu.ncsu.csc.itrust.beans.PersonnelBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.AppointmentDAO;
import edu.ncsu.csc.itrust.dao.mysql.AppointmentRequestDAO;
import edu.ncsu.csc.itrust.dao.mysql.PatientDAO;
import edu.ncsu.csc.itrust.dao.mysql.PersonnelDAO;
import edu.ncsu.csc.itrust.dao.mysql.TransactionDAO;
import edu.ncsu.csc.itrust.enums.TransactionType;
import edu.ncsu.csc.itrust.exception.DBException;

/**
 * Action class for the Appointments Request JSP
 * 
 * @author Kate Lemanski
 * 
 */
public class AppointmentRequestAction {

	private TransactionDAO transDAO;
	private AppointmentRequestDAO apptReqDAO;
	private AppointmentDAO apptDAO;
	private EmailUtil emailutil;
	long loggedInMID;
	private DAOFactory factory;

	/**
	 * Sets up the defaults for the class
	 * 
	 * @param factory factory for creating the defaults.
	 * @param loggedInMID person currently logged in 
	 * @author Kate Lemanski
	 */
	public AppointmentRequestAction(DAOFactory factory, long loggedInMID) {
		transDAO = factory.getTransactionDAO();
		apptReqDAO = factory.getAppointmentRequestDAO();
		apptDAO = factory.getAppointmentDAO();
		this.loggedInMID = loggedInMID;
		emailutil = new EmailUtil(factory);
		this.factory = factory; 
	}

	/**
	 * Adds an appointment request, logs transaction, returns the appointment request ID.
	 * 
	 * @param appointmentRequest
	 * @return the appointment ID
	 * @throws DBException
	 * @author Kate Lemanski
	 */
	public long addAppointmentRequest(AppointmentRequestBean appointmentRequest) throws DBException {

		long appointmentRequestID = apptReqDAO.addAppointmentRequest(appointmentRequest);
		transDAO.logTransaction(TransactionType.ADD_APPOINTMENT_REQUEST, loggedInMID, 
				(loggedInMID == appointmentRequest.getRequestedMID() ? appointmentRequest.getRequesterMID() : appointmentRequest.getRequestedMID()),
				"appointment request id: " + appointmentRequestID);
		return appointmentRequestID;
	}
	/**
	 * Updates a pre-exsisting appointment.  The event is logged.
	 * 
	 * @param loggedInMID  MID of the person who is logged in
	 * @param previousApptRequestID the appointment to be updated
	 * @param newStatus the new status of the appointment
	 * @param type what type of transaction this is (for logging)
	 * @throws DBException
	 * @author Kate Lemanski
	 */
	public void updateAppointmentRequest(long loggedInMID, long previousApptRequestID, String newStatus, TransactionType type) 
	throws DBException {
		// update previous (original) request
		AppointmentRequestBean apptRequestPrevious = apptReqDAO.getAppointmentRequest(previousApptRequestID);
		apptRequestPrevious.setStatus(newStatus);
		apptReqDAO.updateAppointmentRequest(apptRequestPrevious);
		transDAO.logTransaction(type, loggedInMID, 0,
				"appointment request id " + previousApptRequestID + " " + newStatus);
	}

	/**
	 * Rejects an appointment, updates status in AppointmentRequest table in database, logs transaction.
	 * 
	 * @param apptRequestID
	 * @author Kate Lemanski
	 * 
	 */
	public void rejectAppointmentRequest(long apptRequestID) throws DBException {

		// update previous (original) request
		updateAppointmentRequest(loggedInMID, apptRequestID, AppointmentRequestBean.Rejected, TransactionType.REJECT_APPOINTMENT_REQUEST);
		if (loggedInMID == apptReqDAO.getAppointmentRequest(apptRequestID).getRequestedMID() || 0 == apptReqDAO.getAppointmentRequest(apptRequestID).getRequestedMID()){
			emailutil.sendEmail(makeEmailApp(apptRequestID, AppointmentRequestBean.Rejected, -1L, loggedInMID, 
					apptReqDAO.getAppointmentRequest(apptRequestID).getRequesterMID()));
		}
		else if (loggedInMID == apptReqDAO.getAppointmentRequest(apptRequestID).getRequesterMID()){
			emailutil.sendEmail(makeEmailApp(apptRequestID, AppointmentRequestBean.Rejected, -1L, loggedInMID, 
					apptReqDAO.getAppointmentRequest(apptRequestID).getRequestedMID()));
		}
	}

	/**
	 * Changes status of appointment to "rescheduled" and updates database. Creates a new appointment request
	 * bean with new information.
	 * 
	 * @param appointmentRequest new appointment
	 * @param previousApptRequestID the previous appointment
	 * @return ID of new appointment request
	 * @author Kate Lemanski
	 */
	public long rescheduleAppointmentRequest(AppointmentRequestBean appointmentRequest, long previousApptRequestID) throws DBException {

		// update previous (original) request
		updateAppointmentRequest(loggedInMID, previousApptRequestID, AppointmentRequestBean.Rescheduled, TransactionType.RESCHEDULE_APPOINTMENT_REQUEST);

		// create new request
		return addAppointmentRequest(appointmentRequest);

	}

	/**
	 * Updates status to scheduled.
	 * 
	 * @param appointment "new" appointment to be scheduled--this is all the updated information
	 * @param previousApptRequestID the id of the appointment to be updated
	 * @return the id of the "new" appointment
	 * @throws DBException
	 * @author Kate Lemanski
	 */
	public long scheduleAppointment(AppointmentBean appointment, long previousApptRequestID) throws DBException {

		// update previous (original) request
		updateAppointmentRequest(loggedInMID, previousApptRequestID, AppointmentRequestBean.Scheduled, TransactionType.SCHEDULE_APPOINTMENT_REQUEST);

		
		// create the new appointment
		long newApptID = apptDAO.addAppointment(appointment);
		transDAO.logTransaction(TransactionType.ADD_APPOINTMENT, loggedInMID, 
				(loggedInMID == appointment.getLHCPMID() ? appointment.getPatientMID() : appointment.getLHCPMID()), "appointment id: "
				+ newApptID + " for " + appointment.getAppointmentDateString());
		if (loggedInMID == apptReqDAO.getAppointmentRequest(previousApptRequestID).getRequestedMID() || 0 == apptReqDAO.getAppointmentRequest(previousApptRequestID).getRequestedMID()){
			emailutil.sendEmail(makeEmailApp(previousApptRequestID, AppointmentRequestBean.Scheduled, newApptID, loggedInMID, 
					apptReqDAO.getAppointmentRequest(previousApptRequestID).getRequesterMID()));
		}
		else if (loggedInMID == apptReqDAO.getAppointmentRequest(previousApptRequestID).getRequesterMID()){
			emailutil.sendEmail(makeEmailApp(previousApptRequestID, AppointmentRequestBean.Scheduled, newApptID, loggedInMID, 
					apptReqDAO.getAppointmentRequest(previousApptRequestID).getRequestedMID()));
		}
		return newApptID;

	}
	
	/**
	 * Sends out e-mail updates and reminders about appointments
	 * 
	 * @param apptReqID ID for the appointment in question.
	 * @param status the current status of this appointment
	 * @param newApptID the new ID for the appointment (used if things have changed)
	 * @param fromID who the e-mail is from
	 * @param toID who the e-mail is to
	 * @return returns the e-mail that is created.
	 * @throws DBException
	 * @author Kate Lemanski
	 */
	private Email makeEmailApp(long apptReqID, String status, long newApptID, long fromID, long toID) throws DBException{
		    AppointmentRequestBean b = apptReqDAO.getAppointmentRequest(apptReqID);
			Email email = new Email();
			PersonnelBean personnel = new PersonnelDAO(factory).getPersonnel(toID);
		    if(personnel != null){// LHCP requested an appointment
		    	PatientBean patient = new PatientDAO(factory).getPatient(fromID);
		    	email.setFrom("no-reply@itrust.com");

		    	email.setToList(Arrays.asList(personnel.getEmail(),patient.getEmail()));
		    	email.setSubject(String.format("An appointment has been  %s in iTrust", status));
		    	if (newApptID == -1) //rejected appt
		    	{
		    		email.setBody(String.format("Dear %s, \n The appointment with %s has been %s. \n" +
		    				" Additional Comments: %s"
		    				, personnel.getFullName(), patient.getFullName(), status.toLowerCase(),b.getReasonString()));
		    	}
		    	else //accepted appt
		    	{
		    		AppointmentBean apptBean = apptDAO.getAppointment(newApptID);
		    		email.setBody(String.format("Dear %s, \n The appointment with %s has been %s on %s for %s minutes."
		    				, personnel.getFullName(), patient.getFullName(), status.toLowerCase(),apptBean.getAppointmentDateString(),
		    				apptBean.getMinutes()));
		    		
		    	}
		    }
		    else{ //LHCP was requested
		    	personnel = new PersonnelDAO(factory).getPersonnel(fromID);
		    	PatientBean patient = new PatientDAO(factory).getPatient(toID);
		    	email.setFrom("no-reply@itrust.com");
		    	email.setToList(Arrays.asList(personnel.getEmail(),patient.getEmail()));
		    	email.setSubject(String.format("An appointment has been  %s in iTrust", status));
		    	if (newApptID == -1)
		    	{
		    		email.setBody(String.format("Dear %s, \n The appointment with %s has been %s. \n" +
		    				" Additional Comments: %s"
		    				, patient.getFullName(), personnel.getFullName(), status.toLowerCase(),b.getReasonString()));
		    	}
		    	else
		    	{
		    		AppointmentBean apptBean = apptDAO.getAppointment(newApptID);
		    		email.setBody(String.format("Dear %s, \n The appointment with %s has been %s on %s for %s minutes."
		    				, patient.getFullName(), personnel.getFullName(), status.toLowerCase(),apptBean.getAppointmentDateString(),
		    				apptBean.getMinutes()));
		    		
		    	}
		    }
			return email;
	}
}
