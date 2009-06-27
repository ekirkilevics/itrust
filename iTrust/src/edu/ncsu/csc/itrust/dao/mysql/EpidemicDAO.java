package edu.ncsu.csc.itrust.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import edu.ncsu.csc.itrust.DBUtil;
import edu.ncsu.csc.itrust.beans.DiagnosisCount;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.enums.State;
import edu.ncsu.csc.itrust.exception.DBException;

/**
 * Used for queries related to epidemic detection.
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
public class EpidemicDAO {
	private DAOFactory factory;

	public EpidemicDAO(DAOFactory factory) {
		this.factory = factory;
	}

	/**
	 * This complicated query counts up the number of occurrences of a diagnosis with the given constraints,
	 * grouped by week. Also, the loader below adds in zeros if there weren't any diagnoses that week.
	 * 
	 * @param lowerICD -
	 *            the lower bound for the diagnosis
	 * @param upperICD -
	 *            the upper bound for the diagnosis
	 * @param zip -
	 *            just uses the first three digits for region
	 * @param state -
	 *            uses this for finding the state
	 * @param from -
	 *            the date we're looking at
	 * @param weeksBack -
	 *            the weeks back that we're looking at
	 * @return
	 * @throws DBException
	 */
	public List<DiagnosisCount> getDiagnosisCounts(double lowerICD, double upperICD, String zip, State state,
			Date from, int weeksBack) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn
					.prepareStatement("SELECT Count(IF(substring(p.zip1,1,3)=?, ovd.id, NULL)) AS NumInRegion, "
							+ "Count(IF(p.state=?, ovd.id, NULL)) AS NumInState, Count(*) AS NumInTotal,"
							+ "floor(dateDiff(?,ov.visitDate) / 7 + 1) as WeeksBack "
							+ "FROM Patients p, OfficeVisits ov, OVDiagnosis ovd WHERE p.MID=ov.PatientID AND "
							+ "ov.id=ovd.VisitID AND ? <= ovd.ICDCode AND ovd.ICDCode < ? "
							+ "GROUP BY WeeksBack HAVING WeeksBack <= ? ORDER BY WeeksBack DESC");
			ps.setString(1, zip.substring(0, 3));
			ps.setString(2, state.toString());
			ps.setDate(3, new java.sql.Date(from.getTime()));
			ps.setDouble(4, lowerICD);
			ps.setDouble(5, upperICD);
			ps.setInt(6, weeksBack);
			ResultSet rs = ps.executeQuery();
			return loadCounts(rs, weeksBack);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}

	/**
	 * Adds an empty row of zeros if there was no row
	 * 
	 * @param rs
	 * @param weeksBack
	 * @return
	 * @throws SQLException
	 */
	private List<DiagnosisCount> loadCounts(ResultSet rs, int weeksBack) throws SQLException {
		ArrayList<DiagnosisCount> list = new ArrayList<DiagnosisCount>();
		for (int i = weeksBack; i >= 0 && rs.next(); i--) {
			if (rs.getInt("WeeksBack") == i) {
				DiagnosisCount count = new DiagnosisCount();
				count.setNumInRegion(rs.getInt("NumInRegion"));
				count.setNumInState(rs.getInt("NumInState"));
				count.setNumInTotal(rs.getInt("NumInTotal"));
				list.add(count);
			} else {
				for (; i > 0 && i > rs.getInt("WeeksBack"); i--) {
					list.add(new DiagnosisCount());
				}
			}
		}
		if (list.size() < weeksBack) { // if there aren't any in the last few weeks
			for (int i = list.size(); i < weeksBack; i++)
				list.add(new DiagnosisCount());
		}
		return list;
	}

	/**
	 * Returns the weekly average of diagnoses in [icdLower,icdUpper), in a particular zip code, for one week
	 * before the given date
	 * 
	 * @param icdLower
	 * @param icdUpper
	 * @param zip
	 * @param currentDate
	 * @return
	 * @throws DBException
	 */
	public double getWeeklyAverage(double icdLower, double icdUpper, String zip, Date currentDate)
			throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT Count(*) AS WeeklyAverage "
					+ "FROM Patients p, OfficeVisits ov, OVDiagnosis ovd "
					+ "WHERE p.MID=ov.PatientID AND ov.id=ovd.VisitID AND substring(p.zip1,1,3)=? "
					+ "AND ? <= ovd.ICDCode AND ovd.ICDCode < ? "
					+ "AND date_format(ov.VisitDate, '%m%d') <= date_format(?,'%m%d') "
					+ "AND date_format(ov.VisitDate, '%m%d') >= date_format(? - INTERVAL 1 week,'%m%d') "
					+ "AND Year(ov.VisitDate) < Year(?) GROUP BY Year(ov.VisitDate)");
			ps.setString(1, zip.substring(0, 3));
			ps.setDouble(2, icdLower);
			ps.setDouble(3, icdUpper);
			java.sql.Date sqlCurrentDate = new java.sql.Date(currentDate.getTime());
			ps.setDate(4, sqlCurrentDate);
			ps.setDate(5, sqlCurrentDate);
			ps.setDate(6, sqlCurrentDate);
			ResultSet rs = ps.executeQuery();
			return loadWeeklyAverage(rs);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}

	private double loadWeeklyAverage(ResultSet rs) throws SQLException {
		double sum = 0;
		double num = 0;
		while (rs.next()) {
			sum += rs.getInt("WeeklyAverage");
			num++;
		}
		if (num > 0)
			return sum / num;
		else
			return 0;
	}
}
