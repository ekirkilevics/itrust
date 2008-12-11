package edu.ncsu.csc.itrust.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import edu.ncsu.csc.itrust.DBUtil;
import edu.ncsu.csc.itrust.beans.LabProcedureBean;
import edu.ncsu.csc.itrust.beans.loaders.LabProcedureBeanLoader;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.DBException;

public class LabProcedureDAO {
	private DAOFactory factory;
	private LabProcedureBeanLoader labProcedureLoader;

	public LabProcedureDAO(DAOFactory factory) {
		this.factory = factory;
		labProcedureLoader = new LabProcedureBeanLoader();
	}
	
	public List<LabProcedureBean> getLabProceduresForPatient(long id) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			if (id == 0L) throw new SQLException("PatientMID cannot be null");
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM LabProcedure WHERE PatientMID = ? AND Rights = ? ORDER BY UpdatedDate DESC");
			ps.setLong(1, id);
			ps.setString(2, LabProcedureBean.Allow);
			ResultSet rs = ps.executeQuery();
			return labProcedureLoader.loadList(rs);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
	
	public List<LabProcedureBean> getLabProceduresForPatientForNextMonth(long id) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			if (id == 0L) throw new SQLException("PatientMID cannot be null");
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM LabProcedure WHERE PatientMID = ? AND Rights = ? AND Status = ? AND (DateDiff(SYSDATE(),UpdatedDate) <= 30) ORDER BY UpdatedDate DESC");
			ps.setLong(1, id);
			ps.setString(2, LabProcedureBean.Allow);
			ps.setString(3, LabProcedureBean.Completed);
			ResultSet rs = ps.executeQuery();
			return labProcedureLoader.loadList(rs);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
	
	public LabProcedureBean getLabProcedure(long id) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM LabProcedure WHERE LaboratoryProcedureID = ?");
			ps.setLong(1, id);
			ResultSet rs = ps.executeQuery();
			rs.next();
			return labProcedureLoader.loadSingle(rs);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
	/**
	 * Gets all procedures for Patient
	 * @param mid patient id
	 * @return
	 * @throws DBException
	 */
	public List<LabProcedureBean> getAllLabProceduresDate(long mid) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			if (mid == 0L) throw new SQLException("PatientMID cannot be null");
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM LabProcedure WHERE PatientMID = ? ORDER BY UpdatedDate DESC");
			ps.setLong(1, mid);
			ResultSet rs = ps.executeQuery();
			return labProcedureLoader.loadList(rs);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
	/**
	 * This gets all the procedures for a particular patient on a particular office visit
	 * @param mid
	 * @param ovid
	 * @return
	 * @throws DBException
	 */
	public List<LabProcedureBean> getAllLabProceduresForDocOV(long mid, long ovid) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			if (mid == 0L) throw new SQLException("PatientMID cannot be null");
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM LabProcedure WHERE PatientMID = ? AND OfficeVisitID = ? ORDER BY UpdatedDate DESC");
			ps.setLong(1, mid);
			ps.setLong(2, ovid);
			ResultSet rs = ps.executeQuery();
			return labProcedureLoader.loadList(rs);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
	
	/**
	 * This gets all the procedures for a particular patient on a particular office visit
	 * @param mid
	 * @param ovid
	 * @return
	 * @throws DBException
	 */
	public List<LabProcedureBean> getAllLabProceduresForDocOV(long ovid) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM LabProcedure WHERE OfficeVisitID = ? ");
			ps.setLong(1, ovid);
			ResultSet rs = ps.executeQuery();
			return labProcedureLoader.loadList(rs);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
	
/**
 * this may not be needed
 * @return
 * @throws DBException
 */
	public List<LabProcedureBean> getAllLabProcedures() throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM LabProcedure ORDER BY UpdatedDate ASC");
			ResultSet rs = ps.executeQuery();
			return labProcedureLoader.loadList(rs);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
	
	public List<LabProcedureBean> getLabProceduresForLHCPForNextMonth(long ovid) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			if (ovid == 0L) throw new SQLException("OfficeVisitID cannot be null");
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM LabProcedure WHERE OfficeVisitID = ? AND Status = ? AND (DateDiff(SYSDATE(),UpdatedDate) <= 30) ORDER BY UpdatedDate DESC");
			ps.setLong(1, ovid);
			ps.setString(2, LabProcedureBean.Completed);
			ResultSet rs = ps.executeQuery();
			return labProcedureLoader.loadList(rs);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
	
	public long addLabProcedure(LabProcedureBean b) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			if (b.getPid() == 0L) throw new SQLException("PatientMID cannot be null");
			conn = factory.getConnection();
			ps = conn.prepareStatement("INSERT INTO LabProcedure (PatientMID, LaboratoryProcedureCode, Status, Commentary, Results, OfficeVisitID, Rights) VALUES (?,?,?,?,?,?,?)");
			ps.setLong(1, b.getPid());
			ps.setString(2, b.getLoinc());
			ps.setString(3, b.getStatus());
			ps.setString(4, b.getCommentary());
			ps.setString(5, b.getResults());
			ps.setLong(6, b.getOvID());
			ps.setString(7, b.getRights());
			ps.executeUpdate();
			return DBUtil.getLastInsert(conn);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
	public void updateLabProcedure(LabProcedureBean b) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			if (b.getPid() == 0L) throw new SQLException("PatientMID cannot be null");
			conn = factory.getConnection();
			ps = conn.prepareStatement("UPDATE LabProcedure SET Status = ?, Commentary = ?, Results = ?, UpdatedDate = ? WHERE LaboratoryProcedureID=?");
			ps.setString(1, b.getStatus());
			ps.setString(2, b.getCommentary());
			ps.setString(3, b.getResults());
			ps.setTimestamp(4, new java.sql.Timestamp(System.currentTimeMillis()));
			ps.setLong(5, b.getProcedureID());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
	
	public List<LabProcedureBean> getAllLabProceduresLOINC(long id) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			if (id == 0L) throw new SQLException("PatientMID cannot be null");
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM LabProcedure WHERE PatientMID = ? ORDER BY LaboratoryProcedureCode ASC");
			ps.setLong(1, id);
			ResultSet rs = ps.executeQuery();
			return labProcedureLoader.loadList(rs);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
	
	public void updateRights(LabProcedureBean b) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			if (b.getPid() == 0L) throw new SQLException("PatientMID cannot be null");
			conn = factory.getConnection();
			ps = conn.prepareStatement("UPDATE LabProcedure SET Rights = ?, UpdatedDate = ? WHERE LaboratoryProcedureID=?");
			ps.setString(1, b.getRights());
			ps.setTimestamp(2, new java.sql.Timestamp(System.currentTimeMillis()));
			ps.setLong(3, b.getProcedureID());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}



}
