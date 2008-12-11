package edu.ncsu.csc.itrust.server;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.DBException;

public class SessionTimeoutListener implements HttpSessionListener {
	private DAOFactory factory;

	public SessionTimeoutListener() {
		this.factory = DAOFactory.getProductionInstance();
	}

	public SessionTimeoutListener(DAOFactory factory) {
		this.factory = factory;
	}

	public void sessionCreated(HttpSessionEvent arg0) {
		HttpSession session = arg0.getSession();
		int mins = 20;
		try {
			mins = factory.getAccessDAO().getSessionTimeoutMins();
		} catch (DBException e) {
			System.err.println("Unable to set session timeout, defaulting to 20 minutes");
			e.printStackTrace();
		}
		if (mins < 1)
			mins = 1;
		session.setMaxInactiveInterval(mins * 60);
	}

	public void sessionDestroyed(HttpSessionEvent arg0) {
		// nothing to do here
	}
}
