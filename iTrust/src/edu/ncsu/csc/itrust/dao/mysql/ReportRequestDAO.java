package edu.ncsu.csc.itrust.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import edu.ncsu.csc.itrust.DBUtil;
import edu.ncsu.csc.itrust.beans.ReportRequestBean;
import edu.ncsu.csc.itrust.beans.loaders.ReportRequestBeanLoader;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.DBException;


public class ReportRequestDAO {
	private DAOFactory factory;
	private ReportRequestBeanLoader loader;

	public ReportRequestDAO(DAOFactory factory) {
		this.factory = factory;
		loader = new ReportRequestBeanLoader();
	}
	
	public ReportRequestBean getReportRequest(long id) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			if (id == 0L) throw new SQLException("ID cannot be null");
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM ReportRequests WHERE ID = ?");
			ps.setLong(1, id);
			ResultSet rs = ps.executeQuery();
			rs.next();
			return loader.loadSingle(rs);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
	
	public List<ReportRequestBean> getAllReportRequestsForRequester(long mid) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			if (mid == 0L) throw new SQLException("RequesterMID cannot be null");
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM ReportRequests WHERE RequesterMID = ?");
			ps.setLong(1, mid);
			ResultSet rs = ps.executeQuery();
			return loader.loadList(rs);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
	
	public List<ReportRequestBean> getAllReportRequestsForPatient(long pid) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			if (pid == 0L) throw new SQLException("PatientMID cannot be null");
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM ReportRequests WHERE PatientMID = ?");
			ps.setLong(1, pid);
			ResultSet rs = ps.executeQuery();
			return loader.loadList(rs);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
	
	public List<ReportRequestBean> getAllReportRequests() throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM ReportRequests");
			ResultSet rs = ps.executeQuery();
			return loader.loadList(rs);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
	
	public long addReportRequest(long requesterMID, long patientMID, Date date) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			if (requesterMID == 0L) throw new SQLException("RequesterMID cannot be null");
			conn = factory.getConnection();
			ps = conn.prepareStatement("INSERT INTO ReportRequests (ID, RequesterMID, PatientMID, RequestedDate, Status) VALUES (null,?,?,?,'Requested')");
			ps.setLong(1, requesterMID);
			ps.setLong(2, patientMID);
			ps.setTimestamp(3, new java.sql.Timestamp(date.getTime()));
			ps.executeUpdate();
			return DBUtil.getLastInsert(conn);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
	
	public void approveReportRequest(long ID, long approverMID, Date date) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			if (ID == 0L) throw new SQLException("ID cannot be null");
			conn = factory.getConnection();
			ps = conn.prepareStatement("UPDATE ReportRequests set ApproverMID = ?, ApprovedDate = ?, Status = 'Approved' where ID = ?");
			ps.setLong(1, approverMID);
			ps.setTimestamp(2, new java.sql.Timestamp(date.getTime()));
			ps.setLong(3, ID);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}

	public void rejectReportRequest(long ID, long approverMID, Date date, String comment) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			if (ID == 0L) throw new SQLException("ID cannot be null");
			conn = factory.getConnection();
			ps = conn.prepareStatement("UPDATE ReportRequests set ApproverMID = ?, ApprovedDate = ?, Status = 'Rejected', comment = ? where ID = ?");
			ps.setLong(1, approverMID);
			ps.setTimestamp(2, new java.sql.Timestamp(date.getTime()));
			ps.setString(3, comment);
			ps.setLong(4, ID);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
	
	public void setViewed(long ID, Date date) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			if (ID == 0L) throw new SQLException("ID cannot be null");
			conn = factory.getConnection();
			ps = conn.prepareStatement("UPDATE ReportRequests set ViewedDate = ?, Status = 'Viewed' where ID = ?");
			ps.setTimestamp(1, new java.sql.Timestamp(date.getTime()));
			ps.setLong(2, ID);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
		
	}

}
