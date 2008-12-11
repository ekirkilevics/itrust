package edu.ncsu.csc.itrust.beans.loaders;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import edu.ncsu.csc.itrust.beans.AppointmentBean;


public class AppointmentBeanLoader implements BeanLoader<AppointmentBean> {
	
	public List<AppointmentBean> loadList(ResultSet rs) throws SQLException {
		ArrayList<AppointmentBean> list = new ArrayList<AppointmentBean>();
		while (rs.next()) {
			list.add(loadSingle(rs));
		}
		return list;
	}

	public AppointmentBean loadSingle(ResultSet rs) throws SQLException {
		AppointmentBean appointment = new AppointmentBean();
		appointment.setID(rs.getLong("ID"));
		appointment.setRequestID(rs.getLong("RequestID"));
		appointment.setPatientMID(rs.getLong("PatientMID"));
		appointment.setLHCPMID(rs.getLong("LHCPMID"));
		appointment.setAppointmentDate(rs.getTimestamp("DateOfAppt"));
		appointment.setMinutes(rs.getInt("Minutes"));
		return appointment;
	}

	public PreparedStatement loadParameters(PreparedStatement ps, AppointmentBean bean) throws SQLException {
		throw new IllegalStateException("unimplemented!");
	}

}
