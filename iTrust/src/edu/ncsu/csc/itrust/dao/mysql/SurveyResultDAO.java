package edu.ncsu.csc.itrust.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import edu.ncsu.csc.itrust.DBUtil;
import edu.ncsu.csc.itrust.beans.SurveyResultBean;
import edu.ncsu.csc.itrust.beans.loaders.SurveyResultBeanLoader;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.DBException;

/**
 * 
 * @author Ted Lowery (tblowery@ncsu.edu)
 *
 */

public class SurveyResultDAO {
	private DAOFactory factory;
	private SurveyResultBeanLoader loader;
	
	public SurveyResultDAO(DAOFactory factory) {
		this.factory = factory;
		this.loader = new SurveyResultBeanLoader();
	}
	
	public List<SurveyResultBean> getSurveyResultsForZip(String zip, String specialty) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;

		StringBuffer sql = new StringBuffer();
		sql.append("select p.mid, p.firstname, p.lastname, p.address1, p.address2, p.city, p.state, p.zip, p.specialty, ");
		sql.append("'na' hospitalID, ");
		sql.append("avg(s.WaitingRoomMinutes) AvgWaitingRoomMinutes, ");
		sql.append("avg(s.ExamRoomMinutes) AvgExamRoomMinutes, ");
		sql.append("avg(s.VisitSatisfaction) AvgVisitSatisfaction, ");
		sql.append("avg(s.TreatmentSatisfaction) AvgTreatmentSatisfation, ");
		sql.append("count(*) / ");
		sql.append("	(select count(*) from personnel p1, officevisits v1 ");
		sql.append("	 where v1.hcpid = p1.mid ");
		sql.append("	 and substr(p1.zip,1,3) = ? ");
		sql.append("	 and p1.mid = p.mid) * 100 PercentSatisfactionResults ");
		sql.append("from ovsurvey s, personnel p, officevisits v ");
		sql.append("where s.visitid = v.id ");
		sql.append("and v.hcpid = p.mid ");
		sql.append("and substr(p.zip,1,3) = ? ");
		if (!specialty.equals(SurveyResultBean.ANY_SPECIALTY))
			sql.append("and specialty = ?");
		sql.append("group by p.mid, p.firstname, p.lastname, p.address1, p.address2, p.city, p.state, p.zip, hospitalID ");
		if (!specialty.equals(SurveyResultBean.ANY_SPECIALTY))
			sql.append(", p.specialty ");
		sql.append("order by p.mid ");
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement(sql.toString());
			ps.setString(1, zip.substring(0, 3));
			ps.setString(2, zip.substring(0, 3));
			if (!specialty.equals(SurveyResultBean.ANY_SPECIALTY))
				ps.setString(3, specialty);
			return loader.loadList(ps.executeQuery());

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
	
	public List<SurveyResultBean> getSurveyResultsForHospital(String hospitalID, String specialty) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;

		StringBuffer sql = new StringBuffer();
		sql.append("select p.mid, p.firstname, p.lastname, p.address1, p.address2, p.city, p.state, p.zip, p.specialty, ");
		sql.append("h.hosid hospitalID, ");
		sql.append("avg(s.WaitingRoomMinutes) AvgWaitingRoomMinutes, ");
		sql.append("avg(s.ExamRoomMinutes) AvgExamRoomMinutes, ");
		sql.append("avg(s.VisitSatisfaction) AvgVisitSatisfaction, ");
		sql.append("avg(s.TreatmentSatisfaction) AvgTreatmentSatisfation, ");
		sql.append("count(*) / ");
		sql.append("	(select count(*) from personnel p1, officevisits v1, HCPAssignedHos h1 ");
		sql.append("	 where v1.hcpid = p1.mid ");
		sql.append("	 and v1.hcpid = h1.hcpid ");
		sql.append("	 and h1.hosid = ? ");
		sql.append("	 and p1.mid = p.mid) * 100 PercentSatisfactionResults ");
		sql.append("from ovsurvey s, personnel p, officevisits v, HCPAssignedHos h ");
		sql.append("where s.visitid = v.id ");
		sql.append("and v.hcpid = p.mid ");
		sql.append("and v.hcpid = h.hcpid ");
		sql.append("and h.hosid = ? ");
		if (!specialty.equals(SurveyResultBean.ANY_SPECIALTY))
			sql.append("and p.specialty = ?");
		sql.append("group by p.mid, p.firstname, p.lastname, p.address1, p.address2, p.city, p.state, p.zip, hospitalID ");
		if (!specialty.equals(SurveyResultBean.ANY_SPECIALTY))
			sql.append(", p.specialty ");
		sql.append("order by p.mid ");
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement(sql.toString());
			ps.setString(1, hospitalID);
			ps.setString(2, hospitalID);
			if (!specialty.equals(SurveyResultBean.ANY_SPECIALTY)) {
				ps.setString(3, specialty);
			}
			return loader.loadList(ps.executeQuery());

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
		
	}
}
