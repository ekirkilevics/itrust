package edu.ncsu.csc.itrust.action;

import java.util.List;
import edu.ncsu.csc.itrust.action.base.PersonnelBaseAction;
import edu.ncsu.csc.itrust.beans.CODBean;
import edu.ncsu.csc.itrust.beans.forms.CODForm;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.CODDAO;
import edu.ncsu.csc.itrust.dao.mysql.TransactionDAO;
import edu.ncsu.csc.itrust.enums.TransactionType;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.iTrustException;
import edu.ncsu.csc.itrust.validate.CODFormValidator;

/**
 * Cause of Death action, used for codtrends.jsp.
 * 
 * 
 */
public class CODAction extends PersonnelBaseAction {
	private CODDAO coddao;
	private long loggedInMID;
	private CODForm form;
	private TransactionDAO transDAO;

	/**
	 * Sets up defaults
	 * 
	 * @param factory
	 * @param loggedInMID
	 * @throws iTrustException
	 */
	public CODAction(DAOFactory factory, long loggedInMID) throws iTrustException {
		super(factory, "" + loggedInMID);
		this.coddao = factory.getCODDAO();
		this.loggedInMID = loggedInMID;
		this.form = new CODForm();
		this.transDAO = new TransactionDAO(factory);
	}

	/**
	 * Validates the form used to make the request, an exception is thrown if the input is not valid.
	 * 
	 * @param rForm
	 * @throws FormValidationException
	 * @throws iTrustException
	 */
	public void setForm(CODForm rForm) throws FormValidationException, iTrustException {
		new CODFormValidator().validate(rForm);
		this.form = rForm;
		transDAO.logTransaction(TransactionType.CAUSE_OF_DEATH, this.loggedInMID);
	}

	/**
	 * Returns a list of the top two causes of death. Note that the list could be more than two for ties.
	 * 
	 * @return
	 * @throws iTrustException
	 */
	public List<CODBean> getAllCODs() throws iTrustException {
		// TODO this needs to be extracted to a separate class - too hard to read
		coddao.setGender(form.getGen());
		List<CODBean> theList = coddao.getCODsAllPatients(form.getAfterDate(), form.getBeforeDate());
		return theList;
	}

	/**
	 * Returns a top-two list of Causes of Death based on "My Patients". A patient is associated with an HCP
	 * in this case if there is a documented office visit. Note that the list could be more than two for ties.
	 * 
	 * @return
	 * @throws iTrustException
	 */
	public List<CODBean> getMyCODs() throws iTrustException {

		coddao.setGender(form.getGen());
		List<CODBean> theList = coddao.getCODsMyPatients(this.loggedInMID, form.getAfterDate(), form
				.getBeforeDate());
		return theList;
	}
}
