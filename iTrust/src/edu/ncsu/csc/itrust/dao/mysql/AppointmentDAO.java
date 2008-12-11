package edu.ncsu.csc.itrust.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import edu.ncsu.csc.itrust.DBUtil;
import edu.ncsu.csc.itrust.beans.AppointmentBean;
import edu.ncsu.csc.itrust.beans.loaders.AppointmentBeanLoader;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.DBException;

public class AppointmentDAO {
	private DAOFactory factory;
	private AppointmentBeanLoader appointmentLoader;

	public AppointmentDAO(DAOFactory factory) {
		this.factory = factory;
		appointmentLoader = new AppointmentBeanLoader();
	}
	
	public AppointmentBean getAppointment(long id) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM Appointments WHERE ID = ?");
			ps.setLong(1, id);
			ResultSet rs = ps.executeQuery();
			rs.next();
			return appointmentLoader.loadSingle(rs);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
	
	public List<AppointmentBean> getAllAppointments(long mid) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			if (mid == 0L) throw new SQLException("RequesterMID cannot be null");
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM Appointments WHERE PatientMID = ? OR LHCPMID = ?");
			ps.setLong(1, mid);
			ps.setLong(2, mid);
			ResultSet rs = ps.executeQuery();
			return appointmentLoader.loadList(rs);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
	
	public List<AppointmentBean> getAllAppointmentsForNextWeek(long mid) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			if (mid == 0L) throw new SQLException("RequesterMID cannot be null");
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM Appointments WHERE (PatientMID = ? OR LHCPMID = ?) AND DateDiff(SYSDATE(),DateOfAppt) <= 7");
					///(SYSDATE() + 7) >= DateOfAppt AND SYSDATE() <= DateOfAppt"
			ps.setLong(1, mid);
			ps.setLong(2, mid);
			ResultSet rs = ps.executeQuery();
			return appointmentLoader.loadList(rs);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
	
	public List<AppointmentBean> getAllAppointmentsForPatient(long mid) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			if (mid == 0L) throw new SQLException("RequesterMID cannot be null");
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM Appointments WHERE PatientMID = ?");
			ps.setLong(1, mid);
			ResultSet rs = ps.executeQuery();
			return appointmentLoader.loadList(rs);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
	
	public List<AppointmentBean> getAllAppointmentsForLHCP(long mid) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			if (mid == 0L) throw new SQLException("RequesterMID cannot be null");
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM Appointments WHERE LHCPMID = ?");
			ps.setLong(1, mid);
			ResultSet rs = ps.executeQuery();
			return appointmentLoader.loadList(rs);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
	
	public long addAppointment(AppointmentBean appointment) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			if (appointment.getPatientMID() == 0L) throw new SQLException("PatientMID cannot be null");
			conn = factory.getConnection();
			ps = conn.prepareStatement("INSERT INTO appointments (ID, RequestID, PatientMID, LHCPMID, DateOfAppt, Minutes) VALUES (null,?,?,?,?,?)");
			ps.setLong(1, appointment.getRequestID());
			ps.setLong(2, appointment.getPatientMID());
			ps.setLong(3, appointment.getLHCPMID());
			ps.setTimestamp(4, new java.sql.Timestamp(appointment.getAppointmentDate().getTime()));
			ps.setInt(5, appointment.getMinutes());
			ps.executeUpdate();
			return DBUtil.getLastInsert(conn);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
	
}
