package edu.ncsu.csc.itrust.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import edu.ncsu.csc.itrust.DBUtil;
import edu.ncsu.csc.itrust.beans.AppointmentRequestBean;
import edu.ncsu.csc.itrust.beans.loaders.AppointmentRequestBeanLoader;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.DBException;

/**
 * 
 * @author Ted Lowery (tblowery@ncsu.edu)
 *
 */
public class AppointmentRequestDAO {

	private DAOFactory factory;
	private AppointmentRequestBeanLoader appointmentRequestLoader;

	public AppointmentRequestDAO(DAOFactory factory) {
		this.factory = factory;
		appointmentRequestLoader = new AppointmentRequestBeanLoader();
	}

	/**
	 * Gets an appointment request from the database by the PK id. Creates an instance of an
	 * AppointmentRequestBean with data and returns it.
	 * 
	 * @param id
	 * @return
	 * @throws DBException
	 */
	public AppointmentRequestBean getAppointmentRequest(long id) throws DBException {

		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM AppointmentRequests WHERE ID = ?");
			ps.setLong(1, id);
			ResultSet rs = ps.executeQuery();
			rs.next();
			return appointmentRequestLoader.loadSingle(rs);

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}

	/**
	 * Insert data into database.
	 * 
	 * @param appointmentRequest
	 */
	public long addAppointmentRequest(AppointmentRequestBean appointmentRequest) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			if (appointmentRequest.getRequesterMID() == 0L) throw new SQLException("RequesterMID cannot be null");
			conn = factory.getConnection();
			ps = conn.prepareStatement("INSERT INTO appointmentRequests (ID, RequesterMID, RequestedMID, HospitalID, Date1, Date2, Minutes, Reason, WeeksUntilVisit, Status) VALUES (null,?,?,?,?,?,?,?,?,?)");
			ps.setLong(1, appointmentRequest.getRequesterMID());
			ps.setLong(2, appointmentRequest.getRequestedMID());
			ps.setString(3, appointmentRequest.getHospitalID());
			try{
			if (appointmentRequest.getDate1() == null) ps.setNull(4, java.sql.Types.TIMESTAMP);
			else ps.setTimestamp(4, new java.sql.Timestamp(appointmentRequest.getDate1().getTime()));
			
			if (appointmentRequest.getDate2() == null) ps.setNull(5, java.sql.Types.TIMESTAMP);
			else ps.setTimestamp(5, new java.sql.Timestamp(appointmentRequest.getDate2().getTime()));
			}
			catch(java.text.ParseException e){
				System.err.println(e.getMessage());
			}	
			ps.setInt(6, appointmentRequest.getMinutes());
			ps.setString(7, appointmentRequest.getReason());
			ps.setInt(8, appointmentRequest.getWeeksUntilVisit());
			ps.setString(9, appointmentRequest.getStatus());
			ps.executeUpdate();
			return DBUtil.getLastInsert(conn);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}

	/**
	 * Updates status in an appointment request.
	 * 
	 * @param appointmentRequest
	 * @throws DBException
	 */
	public void updateAppointmentRequest(AppointmentRequestBean appointmentRequest) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			if (appointmentRequest.getRequesterMID() == 0L) throw new SQLException("RequesterMID cannot be null");
			conn = factory.getConnection();
			ps = conn.prepareStatement("UPDATE AppointmentRequests SET Status = ? where id = ?");
			ps.setString(1, appointmentRequest.getStatus());
			ps.setLong(2, appointmentRequest.getID());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}

	/**
	 * Returns a list of all appointment requests. Gets everything out of database where the patient is
	 * requester or requested.
	 */
	public List<AppointmentRequestBean> getAllAppointmentRequests(long mid) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			if (mid == 0L) throw new SQLException("RequesterMID cannot be null");
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM AppointmentRequests WHERE RequesterMID = ? OR RequestedMID = ?");
			ps.setLong(1, mid);
			ps.setLong(2, mid);
			ResultSet rs = ps.executeQuery();
			return appointmentRequestLoader.loadList(rs);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}

	/**
	 * Returns a list of all appointment requests. Gets everything out of database where the patient is
	 * requester or requested.
	 */
	public List<AppointmentRequestBean> getAllAppointmentRequestsByStatus(long mid, String status) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			if (mid == 0L) throw new SQLException("RequesterMID cannot be null");
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM AppointmentRequests WHERE (RequestedMID = ? or HospitalID in (select hosid from hcpassignedhos where HCPID = ?)) and Status = ? ");
			ps.setLong(1, mid);
			ps.setLong(2, mid);
			ps.setString(3, status);
			ResultSet rs = ps.executeQuery();
			return appointmentRequestLoader.loadList(rs);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}

	/**
	 * Returns a list of all appointment requests. Gets everything out of database where the patient is
	 * requester or requested.
	 */
	public List<AppointmentRequestBean> getAppointmentRequestHistory(long mid, String status) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			if (mid == 0L) throw new SQLException("RequesterMID cannot be null");
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM AppointmentRequests WHERE (RequesterMID = ? OR RequestedMID = ? or HospitalID in (select hosid from hcpassignedhos where HCPID = ?)) and Status <> ?");
			ps.setLong(1, mid);
			ps.setLong(2, mid);
			ps.setLong(3, mid);
			ps.setString(4, status);
			ResultSet rs = ps.executeQuery();
			return appointmentRequestLoader.loadList(rs);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
}
