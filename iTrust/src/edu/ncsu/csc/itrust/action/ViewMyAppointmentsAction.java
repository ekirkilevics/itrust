package edu.ncsu.csc.itrust.action;

import java.util.Date;
import java.util.List;
import edu.ncsu.csc.itrust.beans.AppointmentBean;
import edu.ncsu.csc.itrust.beans.AppointmentRequestBean;
import edu.ncsu.csc.itrust.beans.HospitalBean;
import edu.ncsu.csc.itrust.beans.PatientBean;
import edu.ncsu.csc.itrust.beans.PersonnelBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.AppointmentDAO;
import edu.ncsu.csc.itrust.dao.mysql.AppointmentRequestDAO;
import edu.ncsu.csc.itrust.dao.mysql.HospitalsDAO;
import edu.ncsu.csc.itrust.dao.mysql.PatientDAO;
import edu.ncsu.csc.itrust.dao.mysql.PersonnelDAO;
import edu.ncsu.csc.itrust.exception.iTrustException;
import edu.ncsu.csc.itrust.utilities.TimeSpan;
/**
 * Action class for ViewMyAppointments.jsp
 *
 */

public class ViewMyAppointmentsAction {
	private long loggedInMID;
	private PatientDAO patientDAO;
	private PersonnelDAO personnelDAO;
	private AppointmentDAO appointmentDAO;
	private AppointmentRequestDAO appointmentRequestDAO;
	private HospitalsDAO hospitalDAO;
	private AppointmentRequestAction arAction;

	public ViewMyAppointmentsAction(DAOFactory factory, long loggedInMID) {
		this.loggedInMID = loggedInMID;
		this.patientDAO = factory.getPatientDAO();
		this.personnelDAO = factory.getPersonnelDAO();
		this.appointmentDAO = factory.getAppointmentDAO();
		this.appointmentRequestDAO = factory.getAppointmentRequestDAO();
		this.hospitalDAO = factory.getHospitalsDAO();
		this.arAction = new AppointmentRequestAction(factory, loggedInMID);
	}

	/**
	 * Returns a PatientBean for the currently logged in patient
	 * 
	 * @return PatientBean for the currently logged in patient
	 * @throws iTrustException
	 */
	public PatientBean getPatient() throws iTrustException {
		return patientDAO.getPatient(loggedInMID);
	}

	/**
	 * Returns a PersonnelBean for the currently logged in personnel.
	 * 
	 * @return PersonnelBean for the currently logged in personnel.
	 * @throws iTrustException
	 */
	public PersonnelBean getPersonnel() throws iTrustException {
		return personnelDAO.getPersonnel(loggedInMID);
	}

	/**
	 * Returns a HospitalBean for all hospitals
	 * 
	 * @return a HospitalBean for all hospitals
	 * @throws iTrustException
	 */
	public List<HospitalBean> getAllHospitals() throws iTrustException {
		return hospitalDAO.getAllHospitals();
	}

	/**
	 * Returns a HospitalBean for the selected hospital
	 * 
	 * @param hospital
	 * @return a HospitalBean for the selected hospital
	 * @throws iTrustException
	 */
	public HospitalBean getHospital(String hospital) throws iTrustException {
		return hospitalDAO.getHospital(hospital);
	}

	/**
	 * Returns a list of PersonnelBeans for all personnel
	 * 
	 * @return a list of PersonnelBeans for all personnel
	 * @throws iTrustException
	 */
	public List<PersonnelBean> getAllPersonnel() throws iTrustException {
		return personnelDAO.getAllPersonnel();
	}
	

	/**
	 * Returns a list of PatientBeans for all patients
	 * @return a list of PatientBeans for all patients
	 * @throws iTrustException
	 */
	public List<PatientBean> getAllPatients() throws iTrustException {
		return patientDAO.getAllPatients();
	}

	/**
	 * Returns a list of AppointmentBeans for the currently logged in patient
	 * 
	 * @return a list of AppointmentBeans for the currently logged in patient
	 * @throws iTrustException
	 */
	public List<AppointmentBean> getAppointments() throws iTrustException {
		return appointmentDAO.getAllAppointments(loggedInMID);
	}

	/**
	 * Returns a list of AppointmentBeans for the currently logged in patient
	 * 
	 * @return a list of AppointmentBeans for the currently logged in patient
	 * @throws iTrustException
	 */
	public List<AppointmentBean> getAppointmentsForNextWeek() throws iTrustException {
		return appointmentDAO.getAllAppointmentsForNextWeek(loggedInMID);
	}

	/**
	 * Returns a list of AppointmentBeans for the currently logged in patient
	 * 
	 * @return a list of AppointmentBeans for the currently logged in patient
	 * @throws iTrustException
	 */
	public List<AppointmentRequestBean> getRequestsNeedingReponse(String include) throws iTrustException {
		return appointmentRequestDAO.getAllAppointmentRequestsByStatus(loggedInMID, include);
	}

	/**
	 * Returns a list of AppointmentBeans for the currently logged in patient
	 * 
	 * @return a list of AppointmentBeans for the currently logged in patient
	 * @throws iTrustException
	 */
	public List<AppointmentRequestBean> getRequestHistory(String exclude) throws iTrustException {
		return appointmentRequestDAO.getAppointmentRequestHistory(loggedInMID, exclude);
	}

	/**
	 * Returns an appointment request bean
	 * @param id the appointment to return
	 * @return appointment request bean for the given id
	 * @throws iTrustException
	 */
	public AppointmentRequestBean getAppointmentRequest(long id) throws iTrustException {
		return appointmentRequestDAO.getAppointmentRequest(id);
	}

	/**
	 * Checks to see if there is an appointment conflict
	 *
	 * @param mid id of the patient in question
	 * @param date date of the appointment to be scheduled
	 * @param minutes how long the appointment should be
	 * @return true if there is a conflict, otherwise, false. 
	 * @throws iTrustException
	 */
	public boolean checkAppointmentConflictForPatient(long mid, Date date, int minutes)
			throws iTrustException {
		if (date == null)
			return false;
		TimeSpan s1 = new TimeSpan(date, minutes);
		List<AppointmentBean> list = appointmentDAO.getAllAppointmentsForPatient(mid);
		for (AppointmentBean bean : list) {
			TimeSpan s2 = new TimeSpan(bean.getAppointmentDate(), bean.getMinutes());
			if (s1.intersects(s2))
				return true;
		}
		return false;
	}

	/**
	 * Checks to see if there is an appointment conflict
	 *
	 * @param mid id of the LHCP in question
	 * @param date date of the appointment to be scheduled
	 * @param minutes how long the appointment should be
	 * @return true if there is a conflict, otherwise, false. 
	 * @throws iTrustException
	 */
	public boolean checkAppointmentConflictForLHCP(long mid, Date date, int minutes) throws iTrustException {
		if (date == null)
			return false;
		TimeSpan s1 = new TimeSpan(date, minutes);
		List<AppointmentBean> list = appointmentDAO.getAllAppointmentsForLHCP(mid);
		for (AppointmentBean bean : list) {
			TimeSpan s2 = new TimeSpan(bean.getAppointmentDate(), bean.getMinutes());
			if (s1.intersects(s2))
				return true;
		}
		return false;
	}

	/**
	 * Rejects an appointment (by patient)
	 * 
	 * @param requestID id for the appointment to reject
	 * @throws iTrustException
	 */
	public void rejectAppointmentRequestByPatient(long requestID) throws iTrustException {
		AppointmentRequestBean bean = appointmentRequestDAO.getAppointmentRequest(requestID);
		if (!bean.getStatus().equals(AppointmentRequestBean.NeedPatientConfirm))
			throw new iTrustException("Unable to reject this appointment request, status is "
					+ bean.getStatus());
		arAction.rejectAppointmentRequest(requestID);
	}

	/**
	 * Rejects an appointment (by LHCP)
	 * 
	 * @param requestID id for the appointment to reject
	 * @throws iTrustException
	 */
	public void rejectAppointmentRequestByLHCP(long requestID) throws iTrustException {
		AppointmentRequestBean bean = appointmentRequestDAO.getAppointmentRequest(requestID);
		if (!bean.getStatus().equals(AppointmentRequestBean.NeedLHCPConfirm))
			throw new iTrustException("Unable to reject this appointment request, status is "
					+ bean.getStatus());
		arAction.rejectAppointmentRequest(requestID);
	}

	/**
	 * Schedules a request for an appointment (by patient)
	 * 
	 * @param requestID id of the appointment
	 * @param choice which date to use 
	 * @throws iTrustException
	 */
	public void scheduleAppointmentRequestByPatient(long requestID, int choice) throws iTrustException {
		AppointmentRequestBean apptRequest = appointmentRequestDAO.getAppointmentRequest(requestID);
		if (!apptRequest.getStatus().equals(AppointmentRequestBean.NeedPatientConfirm))
			throw new iTrustException("Unable to schedule this appointment request, status is "
					+ apptRequest.getStatus());
		AppointmentBean appt = new AppointmentBean();
		appt.setRequestID(apptRequest.getID());
		appt.setPatientMID(apptRequest.getRequestedMID());
		appt.setLHCPMID(apptRequest.getRequesterMID());
		appt.setMinutes(apptRequest.getMinutes());
		try {
			if (choice == 1)
				appt.setAppointmentDate(apptRequest.getDate1());
			else
				appt.setAppointmentDate(apptRequest.getDate2());
		} catch (java.text.ParseException e) {
			//TODO How does this get handled to the user??
			System.err.println(e.getMessage());
		}
		arAction.scheduleAppointment(appt, requestID);
	}

	/**
	 * Schedules a request for an appointment (by LHCP)
	 * 
	 * @param loggedInMID currently logged in LHCP
	 * @param requestID id of the appointment
	 * @param choice which date to use 
	 * @throws iTrustException
	 */
	public void scheduleAppointmentRequestByLHCP(long loggedInMID, long requestID, int choice)
			throws iTrustException {
		AppointmentRequestBean apptRequest = appointmentRequestDAO.getAppointmentRequest(requestID);
		if (!apptRequest.getStatus().equals(AppointmentRequestBean.NeedLHCPConfirm))
			throw new iTrustException("Unable to schedule this appointment request, status is "
					+ apptRequest.getStatus());
		AppointmentBean appt = new AppointmentBean();
		appt.setRequestID(apptRequest.getID());
		appt.setPatientMID(apptRequest.getRequesterMID());
		appt.setLHCPMID(loggedInMID);
		appt.setMinutes(apptRequest.getMinutes());
		try {
			if (choice == 1)
				appt.setAppointmentDate(apptRequest.getDate1());
			else
				appt.setAppointmentDate(apptRequest.getDate2());
		} catch (java.text.ParseException e) {
			System.err.println(e.getMessage());
		}
		arAction.scheduleAppointment(appt, requestID);
	}

}
