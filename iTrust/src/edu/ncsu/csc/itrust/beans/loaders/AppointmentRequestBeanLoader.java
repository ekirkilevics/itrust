package edu.ncsu.csc.itrust.beans.loaders;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import edu.ncsu.csc.itrust.beans.AppointmentRequestBean;

public class AppointmentRequestBeanLoader implements BeanLoader<AppointmentRequestBean> {
	private static final String format = AppointmentRequestBean.dateFormat;
	public List<AppointmentRequestBean> loadList(ResultSet rs) throws SQLException {
		ArrayList<AppointmentRequestBean> list = new ArrayList<AppointmentRequestBean>();
		while (rs.next()) {
			list.add(loadSingle(rs));
		}
		return list;
	}

	public PreparedStatement loadParameters(PreparedStatement ps, AppointmentRequestBean bean)
			throws SQLException {
		throw new IllegalStateException("unimplemented!");
	}

	public AppointmentRequestBean loadSingle(ResultSet rs) throws SQLException {
		AppointmentRequestBean appointmentRequest = new AppointmentRequestBean();
		appointmentRequest.setID(rs.getLong("ID"));
		appointmentRequest.setDate1(rs.getTimestamp("Date1"));
		appointmentRequest.setDate2(rs.getTimestamp("Date2"));
		try{
			if(appointmentRequest.getDate1() != null)
				appointmentRequest.setDate1String(new SimpleDateFormat(format).format(appointmentRequest.getDate1()));
			if(appointmentRequest.getDate2() != null)
				appointmentRequest.setDate2String(new SimpleDateFormat(format).format(appointmentRequest.getDate2()));
		}
		catch(ParseException e)
		{
			System.out.println(e.getMessage());
		}
		appointmentRequest.setHospitalID(rs.getString("HospitalID"));
		appointmentRequest.setReason(rs.getString("Reason"));
		appointmentRequest.setRequestedMID(rs.getLong("RequestedMID"));
		appointmentRequest.setRequesterMID(rs.getLong("RequesterMID"));
		appointmentRequest.setWeeksUntilVisit(rs.getInt("WeeksUntilVisit"));
		appointmentRequest.setStatus(rs.getString("Status"));
		appointmentRequest.setMinutes(rs.getInt("Minutes"));

		return appointmentRequest;
	}

}
