package edu.ncsu.csc.itrust.action;

import java.util.List;
import edu.ncsu.csc.itrust.action.base.OfficeVisitBaseAction;
import edu.ncsu.csc.itrust.beans.HospitalBean;
import edu.ncsu.csc.itrust.beans.LabProcedureBean;
import edu.ncsu.csc.itrust.beans.MedicationBean;
import edu.ncsu.csc.itrust.beans.OfficeVisitBean;
import edu.ncsu.csc.itrust.beans.PrescriptionBean;
import edu.ncsu.csc.itrust.beans.forms.EditOfficeVisitForm;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.HospitalsDAO;
import edu.ncsu.csc.itrust.dao.mysql.LabProcedureDAO;
import edu.ncsu.csc.itrust.dao.mysql.OfficeVisitDAO;
import edu.ncsu.csc.itrust.dao.mysql.PersonnelDAO;
import edu.ncsu.csc.itrust.dao.mysql.TransactionDAO;
import edu.ncsu.csc.itrust.enums.TransactionType;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.iTrustException;
import edu.ncsu.csc.itrust.validate.EditOfficeVisitValidator;

/**
 * Edits the office visits of a patient Used by editOfficeVisit.jsp
 * 
 * @author laurenhayward
 * 
 */
public class EditOfficeVisitAction extends OfficeVisitBaseAction {
	private EditOfficeVisitValidator validator = new EditOfficeVisitValidator();
	private PersonnelDAO personnelDAO;
	private HospitalsDAO hospitalDAO;
	private OfficeVisitDAO ovDAO;
	private LabProcedureDAO lpDAO;
	private TransactionDAO transDAO;
	private long loggedInMID;
	private long pid;

	private enum OVSubAction {
		ADD_DIAGNOSIS,
		REMOVE_DIAGNOSIS,
		ADD_PROCEDURE,
		REMOVE_PROCEDURE,
		ADD_MEDICATION,
		REMOVE_MEDICATION,
		ADD_LAB_PROCEDURE,
		REMOVE_LAB_PROCEDURE,
		ADD_IMMUNIZATION,
		REMOVE_IMMUNIZATION
	};

	/**
	 * Patient id and office visit id validated by super class
	 * 
	 * @param factory
	 * @param loggedInMID
	 * @param pidString
	 * @param ovIDString
	 * @throws iTrustException
	 */
	public EditOfficeVisitAction(DAOFactory factory, long loggedInMID, String pidString, String ovIDString)
			throws iTrustException {
		super(factory, pidString, ovIDString);
		pid = Long.parseLong(pidString);
		ovDAO = factory.getOfficeVisitDAO();
		this.personnelDAO = factory.getPersonnelDAO();
		this.hospitalDAO = factory.getHospitalsDAO();
		this.lpDAO = factory.getLabProcedureDAO();
		this.transDAO = factory.getTransactionDAO();
		this.loggedInMID = loggedInMID;
	}

	/**
	 * Returns the office visit bean for the office visit
	 * 
	 * @return the OfficeVisitBean of the office visit
	 * @throws iTrustException
	 */
	public OfficeVisitBean getOfficeVisit() throws iTrustException {
		OfficeVisitBean officeVisit = ovDAO.getOfficeVisit(ovID);
		transDAO.logTransaction(TransactionType.VIEW_OFFICE_VISIT, loggedInMID, pid, "EditOffceVisit - View office visits");
		return officeVisit;
	}
	
	/**
	 * Returns a list of the lab procedures that have been done in an office visit.
	 * 
	 * @param mid the doctor the visit was with
	 * @param ovid the office visit's id
	 * @return a list of the lab procedures that were done in that visit
	 * @throws DBException
	 */

	public List<LabProcedureBean> getLabProcedures(long mid, long ovid) throws DBException {
		transDAO.logTransaction(TransactionType.VIEW_LAB_PROCEDURE, loggedInMID, pid, "EditOffceVisit - View lab procedures");
		return lpDAO.getAllLabProceduresForDocOV(mid, ovid);
	}

	/**
	 * This is a list of all hospitals, ordered by the office visit's hcp FIRST
	 * 
	 * @param hcpID
	 * @return
	 * @throws iTrustException
	 */
	public List<HospitalBean> getHospitals(long hcpID) throws iTrustException {
		List<HospitalBean> hcpsHospitals = personnelDAO.getHospitals(hcpID);
		List<HospitalBean> allHospitals = hospitalDAO.getAllHospitals();
		return combineLists(hcpsHospitals, allHospitals);
	}

	
	/**
	 * Combines two lists of hospitals
	 * 
	 * @param hcpsHospitals hospitals the HCP is assigned to
	 * @param allHospitals all hopsitals
	 * @return the combined list
	 */
	private List<HospitalBean> combineLists(List<HospitalBean> hcpsHospitals, List<HospitalBean> allHospitals) {
		for (HospitalBean hos : allHospitals) {
			if (!hcpsHospitals.contains(hos))
				hcpsHospitals.add(hos);
		}
		return hcpsHospitals;
	}

	/**
	 * Updates the office visit with information from the form passed in
	 * 
	 * @param form
	 *            information to update
	 * @return "success" or exception's message
	 * @throws FormValidationException
	 */
	public String updateInformation(EditOfficeVisitForm form) throws FormValidationException {
		String confirm = "";
		try {
			checkAddSubAction(OVSubAction.ADD_DIAGNOSIS, form.getAddDiagID(), ovID, null);
			checkAddSubAction(OVSubAction.ADD_PROCEDURE, form.getAddProcID(), ovID, null);
			checkAddSubAction(OVSubAction.ADD_IMMUNIZATION, form.getAddImmunizationID(), ovID, null);
			checkAddSubAction(OVSubAction.ADD_LAB_PROCEDURE, form.getAddLabProcID(), ovID, null);
			checkAddPrescription(form, ovID);
			checkRemoveSubAction(OVSubAction.REMOVE_DIAGNOSIS, form.getRemoveDiagID());
			checkRemoveSubAction(OVSubAction.REMOVE_LAB_PROCEDURE, form.getRemoveLabProcID());
			checkRemoveSubAction(OVSubAction.REMOVE_PROCEDURE, form.getRemoveProcID());
			checkRemoveSubAction(OVSubAction.REMOVE_IMMUNIZATION, form.getRemoveImmunizationID());
			checkRemoveSubAction(OVSubAction.REMOVE_MEDICATION, form.getRemoveMedID());
			updateOv(form);
			transDAO.logTransaction(TransactionType.DOCUMENT_OFFICE_VISIT, loggedInMID, getOfficeVisit().getPatientID(), "EditOfficeVisit - edited office visit " + ovID);
			confirm = "success";
			return confirm;
		} catch (iTrustException e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
/**
 * Adds a prescription to an office visit
 * 
 * @param form the information about the prescription
 * @param ovID the id for the office visit
 * @throws DBException
 * @throws FormValidationException
 */
	
	private void checkAddPrescription(EditOfficeVisitForm form, long ovID) throws DBException,
			FormValidationException {
		if (form.getAddMedID() != null && !"".equals(form.getAddMedID())) {
			new EditOfficeVisitValidator(true).validate(form);
			PrescriptionBean pres = new PrescriptionBean();
			pres.setDosage(Integer.valueOf(form.getDosage()));
			pres.setEndDateStr(form.getEndDate());
			pres.setStartDateStr(form.getStartDate());
			pres.setInstructions(form.getInstructions());
			MedicationBean med = new MedicationBean();
			med.setNDCode(form.getAddMedID());
			pres.setMedication(med);
			pres.setVisitID(ovID);
			transDAO.logTransaction(TransactionType.ADD_PRESCRIPTION, loggedInMID, pid, "EditOffceVisit - Add prescription");
			ovDAO.addPrescription(pres);
		}
	}

	/**
	 * Updates the office visit.
	 * 
	 * @param form form with all the information
	 * @throws DBException
	 * @throws FormValidationException
	 */
	private void updateOv(EditOfficeVisitForm form) throws DBException, FormValidationException {
		validator.validate(form);
		OfficeVisitBean ov = new OfficeVisitBean(ovID);
		ov.setNotes(form.getNotes());
		ov.setVisitDateStr(form.getVisitDate());
		ov.setHcpID(Long.valueOf(form.getHcpID()));
		ov.setPatientID(Long.valueOf(form.getPatientID()));
		ov.setHospitalID(form.getHospitalID());
		transDAO.logTransaction(TransactionType.UPDATE_OFFICE_VISIT, loggedInMID, pid, "EditOffceVisit - Update office visit");
		ovDAO.update(ov);
	}

	
	/**
	 * Adds a diagnosis or a procedure to an office visit
	 * 
	 * @param action the type of action to add
	 * @param code the CPT code of the action
	 * @param visitID the office visit to add the action to
	 * @param dateOfDeath the date of death, if needed
	 * @return true if the operation completed; false if the code was null
	 * @throws DBException
	 * @throws iTrustException
	 */
	
	private boolean checkAddSubAction(OVSubAction action, String code, long visitID, String dateOfDeath)
			throws DBException, iTrustException {
		if (code == null || "".equals(code)) {
			return false;
		} else {
			switch (action) {
			case ADD_DIAGNOSIS:
				ovDAO.addDiagnosisToOfficeVisit(Double.valueOf(code), visitID);
				break;
			case ADD_IMMUNIZATION:
			case ADD_PROCEDURE:
				ovDAO.addProcedureToOfficeVisit(code, visitID);
				break;
			case ADD_LAB_PROCEDURE:
				ovDAO.addLabProcedureToOfficeVisit(code, visitID, pid);
				break;
			default:
				return false;
			}
			return true;
		}
	}
	
	/**
	 * Removes an action from an office visit
	 * 
	 * @param action type of action to remove
	 * @param input id for the action to remove
	 * @return true if the operation completed; false if the code was null
	 * @throws DBException
	 */

	private boolean checkRemoveSubAction(OVSubAction action, String input) throws DBException {
		if (input == null || "".equals(input))
			return false;
		long removeID;
		try {
			removeID = Long.valueOf(input);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return false;
		}
		switch (action) {
		case REMOVE_DIAGNOSIS:
			ovDAO.removeDiagnosisFromOfficeVisit(removeID);
			break;
		case REMOVE_IMMUNIZATION:
		case REMOVE_PROCEDURE:
			ovDAO.removeProcedureFromOfficeVisit(removeID);
			break;
		case REMOVE_MEDICATION:
			ovDAO.removePrescription(removeID);
			break;
		case REMOVE_LAB_PROCEDURE:
			ovDAO.removeLabProcedureFromOfficeVisit(removeID);
			break;
		}
		return true;
	}

}
