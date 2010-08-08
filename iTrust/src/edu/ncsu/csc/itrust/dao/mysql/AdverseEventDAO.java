package edu.ncsu.csc.itrust.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import edu.ncsu.csc.itrust.DBUtil;
import edu.ncsu.csc.itrust.beans.AdverseEventBean;
import edu.ncsu.csc.itrust.beans.loaders.AdverseEventBeanLoader;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import java.sql.Timestamp;

/**
 * Used for the logging mechanism.
 * 
 * DAO stands for Database Access Object. All DAOs are intended to be reflections of the database, that is,
 * one DAO per table in the database (most of the time). For more complex sets of queries, extra DAOs are
 * added. DAOs can assume that all data has been validated and is correct.
 * 
 * DAOs should never have setters or any other parameter to the constructor than a factory. All DAOs should be
 * accessed by DAOFactory (@see {@link DAOFactory}) and every DAO should have a factory - for obtaining JDBC
 * connections and/or accessing other DAOs.
 * 
 * @author Andy
 * 
 */
public class AdverseEventDAO {
	private DAOFactory factory;
	private AdverseEventBeanLoader aeLoader;

	/**
	 * The typical constructor.
	 * @param factory The {@link DAOFactory} associated with this DAO, which is used for obtaining SQL connections, etc.
	 */
	public AdverseEventDAO(DAOFactory factory) {
		this.factory = factory;
		this.aeLoader = new AdverseEventBeanLoader();
	}

	/**
	 * Gets all the adverse event reports for a certain user MID.
	 * @param mid The MID of the user to be looked up.
	 * @return A java.util.List of AdverseEventBeans.
	 * @throws SQLException
	 */
public List<AdverseEventBean> getReportsFor(long mid) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;

		conn = factory.getConnection();
		ps = conn.prepareStatement("SELECT * FROM AdverseEvents WHERE PatientMID = ?");
		ps.setLong(1, mid);
		ResultSet rs = ps.executeQuery();

		List<AdverseEventBean> aeList = this.aeLoader.loadList(rs);

		DBUtil.closeConnection(conn, ps);

		return aeList;
	}

	/**
	 * Adds a message to the database.
	 * @param mBean A bean representing the message to be added.
	 * @throws SQLException
	 */
	public void addReport(AdverseEventBean aeBean, long hcpmid) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		try{
		conn = factory.getConnection();
		ps = conn.prepareStatement(
				"INSERT INTO AdverseEvents (PatientMID, PresImmu, Code, Comment, Prescriber) "
				  + "VALUES (?, ?, ?, ?, ?)");
		ps.setString(1, aeBean.getMID());
		ps.setString(2, aeBean.getDrug());
		ps.setString(3, aeBean.getCode());
		ps.setString(4, aeBean.getDescription());
		ps.setLong(5, hcpmid);
		ps.executeUpdate();
		}catch(SQLException e){
			DBUtil.closeConnection(conn, ps);
			throw e;
		}
		DBUtil.closeConnection(conn, ps);
	}
	
	public long getHCPMID(int id) throws SQLException{
		Connection conn = null;
		PreparedStatement ps = null;

		conn = factory.getConnection();
		ps = conn.prepareStatement("SELECT * FROM AdverseEvents WHERE id=?");
		ps.setInt(1, id);
		ResultSet rs = ps.executeQuery();

		long hcpMID = 0;
		if(rs.next()) {
			
			 hcpMID = rs.getLong("Prescriber");
		}

		DBUtil.closeConnection(conn, ps);

		return hcpMID;
	}
	
	public AdverseEventBean getReport(int id) throws SQLException{
		Connection conn = null;
		PreparedStatement ps = null;

		conn = factory.getConnection();
		ps = conn.prepareStatement("SELECT * FROM AdverseEvents WHERE id=?");
		ps.setInt(1, id);
		ResultSet rs = ps.executeQuery();

		List<AdverseEventBean> aeList = aeLoader.loadList(rs);
		DBUtil.closeConnection(conn, ps);
		return aeList.get(0);
	}
	
	public long removeReport(int id) throws SQLException{
		Connection conn = null;
		PreparedStatement ps = null;
		
		conn = factory.getConnection();
		ps = conn.prepareStatement("UPDATE AdverseEvents SET Status = ? WHERE id = ?");
		String removed = "removed";
		ps.setString(1, removed);
		ps.setInt(2,id);
		ps.executeUpdate();
		long a = DBUtil.getLastInsert(conn);
		DBUtil.closeConnection(conn, ps);
		return a;
		
	}
	
	public List<AdverseEventBean> getPerscriptions(String start, String end) throws SQLException, ParseException{
		Connection conn = null;
		PreparedStatement ps = null;
		SimpleDateFormat fmt = new SimpleDateFormat("MM/dd/yyy");
		Date beginning = fmt.parse(start);
		Date ending = fmt.parse(end);
 
		conn = factory.getConnection();
		//AND NDCodes.Code=AdverseEvents.Code 
		ps = conn.prepareStatement("SELECT * FROM AdverseEvents,NDCodes WHERE AdverseEvents.TimeLogged >= ? AND AdverseEvents.TimeLogged <= ? AND NDCodes.Code=AdverseEvents.Code ORDER BY AdverseEvents.Code, AdverseEvents.TimeLogged DESC");
		 
		ps.setTimestamp(1, new Timestamp(beginning.getTime()));
		ps.setTimestamp(2, new Timestamp(ending.getTime() + 1000L * 60L * 60 * 24L));
		ResultSet rs = ps.executeQuery();

		List<AdverseEventBean> aeList = aeLoader.loadList(rs);

		DBUtil.closeConnection(conn, ps);
		return aeList;
	}
	
	public List<AdverseEventBean> getImmunizations(String start, String end) throws SQLException, ParseException{
		Connection conn = null;
		PreparedStatement ps = null;
		SimpleDateFormat fmt = new SimpleDateFormat("MM/dd/yyy");
		Date beginning = fmt.parse(start);
		Date ending = fmt.parse(end);
 
		conn = factory.getConnection();
		//AND NDCodes.Code=AdverseEvents.Code 
		ps = conn.prepareStatement("SELECT * FROM AdverseEvents,CPTCodes WHERE AdverseEvents.TimeLogged >= ? AND AdverseEvents.TimeLogged <= ? AND CPTCodes.Code=AdverseEvents.Code ORDER BY AdverseEvents.Code, AdverseEvents.TimeLogged DESC");
		 
		ps.setTimestamp(1, new Timestamp(beginning.getTime()));
		ps.setTimestamp(2, new Timestamp(ending.getTime() + 1000L * 60L * 60 * 24L));
		ResultSet rs = ps.executeQuery();

		List<AdverseEventBean> aeList = aeLoader.loadList(rs);

		DBUtil.closeConnection(conn, ps);
		return aeList;
	}
}