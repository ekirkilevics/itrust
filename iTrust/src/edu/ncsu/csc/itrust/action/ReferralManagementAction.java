package edu.ncsu.csc.itrust.action;


import edu.ncsu.csc.itrust.beans.ReferralBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.ReferralDAO;
import edu.ncsu.csc.itrust.dao.mysql.TransactionDAO;
import edu.ncsu.csc.itrust.enums.TransactionType;
import edu.ncsu.csc.itrust.exception.iTrustException;
import edu.ncsu.csc.itrust.exception.DBException;
import java.util.List;


public class ReferralManagementAction {
	private long loggedInMID;
	private TransactionDAO transDAO;
	private ReferralDAO referralDAO;

	/**
	 * Super class validates the patient id
	 * 
	 * @param factory
	 * @param loggedInMID
	 * @param pidString
	 * @throws iTrustException
	 */
	public ReferralManagementAction(DAOFactory factory, long loggedInMID) throws iTrustException {
		
		this.referralDAO = factory.getReferralDAO();
		this.loggedInMID = loggedInMID;
		this.transDAO = factory.getTransactionDAO();
	}

	
	public void sendReferral(ReferralBean r) throws DBException {
		referralDAO.addReferral(r);
		transDAO.logTransaction(TransactionType.SEND_REFERRAL, loggedInMID);
	}
	
	public void updateReferral(ReferralBean r) throws DBException {
		referralDAO.editReferral(r);
	}
	
	public List<ReferralBean> getReferralsSentFromMe() throws DBException {
		return referralDAO.getReferralsSentFrom(loggedInMID);
	}
	
	public List<ReferralBean> getReferralsSentToMe() throws DBException {
		return referralDAO.getReferralsSentTo(loggedInMID);
	}

}
