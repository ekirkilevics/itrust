package edu.ncsu.csc.itrust.action;

import java.sql.SQLException;
import java.util.List;
import edu.ncsu.csc.itrust.beans.AdverseEventBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.AdverseEventDAO;
import edu.ncsu.csc.itrust.dao.mysql.TransactionDAO;
import edu.ncsu.csc.itrust.exception.DBException;

public class ViewAdverseEventAction {
	private long loggedInMID;
	private AdverseEventDAO adEventDAO;
	private TransactionDAO transDAO;

	public ViewAdverseEventAction(DAOFactory factory)
	{
		this.loggedInMID = loggedInMID;
		this.adEventDAO = factory.getAdverseEventDAO();
		this.transDAO = factory.getTransactionDAO();
	}
	
	public AdverseEventBean getAdverseEvent(int id) throws SQLException
	{
		return adEventDAO.getReport(id);
	}
	
	public List<AdverseEventBean> getUnremovedAdverseEventsByCode(String code) throws DBException
	{
		return adEventDAO.getUnremovedAdverseEventsByCode(code);
	}
	
	public String getNameForCode(String code) throws DBException
	{
		return adEventDAO.getNameForCode(code);
	}
}
