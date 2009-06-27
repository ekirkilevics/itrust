package edu.ncsu.csc.itrust.beans.loaders;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import edu.ncsu.csc.itrust.beans.ReportRequestBean;

/**
 * 
 * @author Ted Lowery (tblowery@ncsu.edu)
 *
 */

public class ReportRequestBeanLoader implements BeanLoader<ReportRequestBean> {

	public List<ReportRequestBean> loadList(ResultSet rs) throws SQLException {
		ArrayList<ReportRequestBean> list = new ArrayList<ReportRequestBean>();
		while (rs.next()) {
			list.add(loadSingle(rs));
		}
		return list;
	}

	public ReportRequestBean loadSingle(ResultSet rs) throws SQLException {
		ReportRequestBean b = new ReportRequestBean();
		b.setID(rs.getLong("ID"));
		b.setRequesterMID(rs.getLong("RequesterMID"));
		b.setPatientMID(rs.getLong("PatientMID"));
		b.setApproverMID(rs.getLong("ApproverMID"));
		b.setRequestedDate(rs.getTimestamp("RequestedDate"));
		b.setApprovedDate(rs.getTimestamp("ApprovedDate"));
		b.setViewedDate(rs.getTimestamp("ViewedDate"));
		b.setStatus(rs.getString("Status"));
		b.setComment(rs.getString("Comment"));
		return b;
	}

	public PreparedStatement loadParameters(PreparedStatement ps, ReportRequestBean bean) throws SQLException {
		throw new IllegalStateException("unimplemented!");
	}


}
