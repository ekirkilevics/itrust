package edu.ncsu.csc.itrust.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import edu.ncsu.csc.itrust.DBUtil;
import edu.ncsu.csc.itrust.beans.CODBean;
import edu.ncsu.csc.itrust.beans.loaders.CODBeanLoader;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.enums.Gender;
import edu.ncsu.csc.itrust.exception.DBException;

/**
 * Used for Cause of Death-related queries.
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
public class CODDAO {
	// TODO DAO's shouldn't have setters. No class should have setters if they don't need to; setters == evil
	private DAOFactory factory;
	private CODBeanLoader codloader = new CODBeanLoader();
	private Gender gen;

	public CODDAO(DAOFactory factory) {
		this.factory = factory;
		gen = Gender.NotSpecified;
	}

	/**
	 * Set the gender, must be run before any of the get methods
	 * 
	 * @param rGen
	 */
	public void setGender(Gender rGen) {
		this.gen = rGen;
	}

	/**
	 * Return the gender that was (hopefully) set
	 * 
	 * @return
	 */
	public Gender getGender() {
		return this.gen;
	}

	private String getGenderWhereClause() {
		if (gen.equals(Gender.NotSpecified))
			return "";
		else
			return " AND Patients.Gender = '" + gen.toString() + "' ";
	}

	/**
	 * Return a list of counts of COD in the dates with the proper gender
	 * 
	 * @param afterDate
	 * @param beforeDate
	 * @return
	 * @throws DBException
	 */
	public List<CODBean> getCODsAllPatients(String afterDate, String beforeDate) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn
					.prepareStatement("select count(distinct(Patients.MID)) as Total, "
							+ " ICDCodes.Description, Patients.CauseOfDeath from OfficeVisits, Patients, ICDCodes WHERE "
							+ " OfficeVisits.PatientID = Patients.MID AND Patients.CauseOfDeath = ICDCodes.Code"
							+ " and Patients.DateOfDeath between ? and ? " + getGenderWhereClause()
							+ " group by (ICDCodes.Code) order by Total desc limit 2;");

			ps.setString(1, afterDate);
			ps.setString(2, beforeDate);
			ResultSet rs = ps.executeQuery();
			return codloader.loadList(rs);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}

	/**
	 * Return a list of counts of COD in the dates with the proper gender and with patients who have ever had
	 * an office visit with this HCP.
	 * 
	 * @param rHCPID
	 * @param afterDate
	 * @param beforeDate
	 * @return
	 * @throws DBException
	 */
	public List<CODBean> getCODsMyPatients(long rHCPID, String afterDate, String beforeDate)
			throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn
					.prepareStatement("select count(distinct(Patients.MID)) as Total, "
							+ "ICDCodes.Description, Patients.CauseOfDeath from OfficeVisits, Patients, ICDCodes WHERE "
							+ "OfficeVisits.PatientID = Patients.MID AND Patients.CauseOfDeath = ICDCodes.Code"
							+ " and Patients.DateOfDeath between ? and ? " + getGenderWhereClause()
							+ " and OfficeVisits.HCPID = ? "
							+ " group by (ICDCodes.Code) order by Total desc limit 2;");

			ps.setString(1, afterDate);
			ps.setString(2, beforeDate);
			ps.setLong(3, rHCPID);

			ResultSet rs = ps.executeQuery();
			return codloader.loadList(rs);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}

}
