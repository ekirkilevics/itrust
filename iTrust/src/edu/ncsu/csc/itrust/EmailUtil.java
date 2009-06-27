package edu.ncsu.csc.itrust;

import edu.ncsu.csc.itrust.beans.Email;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.DBException;

/**
 * Sends email to users. Since we don't want to train spammers in 326, this just inserts into a database. If
 * we put this into an actual system, we would replace this class with stuff from javax.mail
 * 
 * @author Andy
 * 
 */
public class EmailUtil {
	private DAOFactory factory;

	public EmailUtil(DAOFactory factory) {
		this.factory = factory;
	}

	public void sendEmail(Email email) throws DBException {
		factory.getFakeEmailDAO().sendEmailRecord(email);
		factory.getFakeEmailDAO().sendRealEmail(email);
	}
}
