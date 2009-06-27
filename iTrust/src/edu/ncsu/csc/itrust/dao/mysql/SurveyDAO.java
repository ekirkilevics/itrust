package edu.ncsu.csc.itrust.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import edu.ncsu.csc.itrust.DBUtil;
import edu.ncsu.csc.itrust.beans.SurveyBean;
import edu.ncsu.csc.itrust.beans.loaders.SurveyLoader;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.DBException;

/**
 * 
 * @author Ted Lowery (tblowery@ncsu.edu)
 *
 */

public class SurveyDAO {
	private DAOFactory factory;
	private SurveyLoader surveyLoader;

	public SurveyDAO(DAOFactory factory) {
		this.factory = factory;
		this.surveyLoader = new SurveyLoader();
	}
	
	/**
	 * Insert survey data into database.
	 * @param surveyBean
	 * @return
	 */
	public void addCompletedSurvey(SurveyBean surveyBean, Date date) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("INSERT INTO OVSurvey (VisitID, SurveyDate) VALUES (?,?)");
			ps.setLong(1, surveyBean.getVisitID());
			ps.setTimestamp(2, new java.sql.Timestamp(date.getTime()));
			ps.executeUpdate();
			if (surveyBean.getWaitingRoomMinutes() > 0) {
				ps = conn.prepareStatement("update OVSurvey set WaitingRoomMinutes = ? where VisitID = ?");
				ps.setFloat(1, surveyBean.getWaitingRoomMinutes());
				ps.setLong(2, surveyBean.getVisitID());
				ps.executeUpdate();
			}
			if (surveyBean.getExamRoomMinutes() > 0) {
				ps = conn.prepareStatement("update OVSurvey set ExamRoomMinutes = ? where VisitID = ?");
				ps.setFloat(1, surveyBean.getExamRoomMinutes());
				ps.setLong(2, surveyBean.getVisitID());
				ps.executeUpdate();
			}
			if (surveyBean.getVisitSatisfaction() > 0) {
				ps = conn.prepareStatement("update OVSurvey set VisitSatisfaction = ? where VisitID = ?");
				ps.setFloat(1, surveyBean.getVisitSatisfaction());
				ps.setLong(2, surveyBean.getVisitID());
				ps.executeUpdate();
			}
			if (surveyBean.getTreatmentSatisfaction() > 0) {
				ps = conn.prepareStatement("update OVSurvey set TreatmentSatisfaction = ? where VisitID = ?");
				ps.setFloat(1, surveyBean.getTreatmentSatisfaction());
				ps.setLong(2, surveyBean.getVisitID());
				ps.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
	
	/**
	 * Gets survey data from the database by the office visit id. Creates an instance of an
	 * SurveyBean with data and returns it.
	 * 
	 * @param id
	 * @return
	 * @throws DBException
	 */
	public SurveyBean getSurveyData(long id) throws DBException {

		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM OVSurvey WHERE VisitID = ?");
			ps.setLong(1, id);
			ResultSet rs = ps.executeQuery();
			rs.next();
			return surveyLoader.loadSingle(rs);

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
	
	/**
	 * Has this survey been completed?
	 * 
	 * @return boolean
	 * @throws DBException
	 */
	public boolean isSurveyCompleted(long visitID) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT count(*) FROM OVSurvey WHERE VisitID = ?");
			ps.setLong(1, visitID);
			ResultSet rs = ps.executeQuery();
			rs.next();
			return (rs.getInt(1) == 0) ? false : true;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}

}
