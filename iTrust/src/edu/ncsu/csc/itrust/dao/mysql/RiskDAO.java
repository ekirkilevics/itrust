package edu.ncsu.csc.itrust.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import edu.ncsu.csc.itrust.DBUtil;
import edu.ncsu.csc.itrust.beans.FamilyMemberBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.DBException;

/**
 * Used for finding risk factors for a given patient.
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
public class RiskDAO {
	private DAOFactory factory;

	public RiskDAO(DAOFactory factory) {
		this.factory = factory;
	}

	/**
	 * This method is implemented using {@link FamilyDAO} for 2 reasons: (a) definitions of family members
	 * might change, so it's better to centralize that code and (2) to make this code a little bit "nicer"
	 * even though it executes one query per family member. If this method gets slow, then you will need to
	 * refactor. Otherwise, let's just keep it simple...
	 * 
	 * @param patientID
	 * @param icdLower
	 * @param icdUpper
	 * @return
	 * @throws DBException
	 */
	public boolean hasFamilyHistory(long patientID, double icdLower, double icdUpper) throws DBException {
		List<FamilyMemberBean> familyMembers = getFamilyMembers(patientID);
		for (FamilyMemberBean famMember : familyMembers) {
			if (hadPriorDiagnoses(famMember.getMid(), icdLower, icdUpper))
				return true;
		}
		return false;
	}

	private List<FamilyMemberBean> getFamilyMembers(long patientID) throws DBException {
		FamilyDAO famDAO = factory.getFamilyDAO();
		List<FamilyMemberBean> familyMembers = famDAO.getParents(patientID);
		familyMembers.addAll(famDAO.getSiblings(patientID));
		return familyMembers;
	}

	/**
	 * Returns whether or not a patient had a childhood infection for the exact, given ICD codes.
	 * 
	 * @param patientID
	 * @param icdCodes
	 * @return
	 * @throws DBException
	 */
	public boolean hadChildhoodInfection(long patientID, double... icdCodes) throws DBException {
		// Note the datediff call - this is a MySQL function that takes the difference between two
		// dates and returns that value in terms of days. 6570 days is 18 years (not counting leap years)
		// TODO Use a range
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM OVDiagnosis ovd, OfficeVisits ov, Patients p "
					+ "WHERE ovd.visitID=ov.id AND ov.patientid=p.mid AND p.mid=? "
					+ "AND datediff(ov.visitdate,p.dateofbirth) < 6570 AND ovd.icdcode IN ("
					+ createPrepared(icdCodes.length) + ")");
			ps.setLong(1, patientID);
			setICDs(2, ps, icdCodes);
			return ps.executeQuery().next(); // if this query has ANY rows, then yes
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}

	private String createPrepared(int length) {
		String str = "";
		for (int i = 0; i < length; i++)
			str += "?,";
		return str.substring(0, str.length() - 1);
	}

	private void setICDs(int start, PreparedStatement ps, double[] icdCodes) throws SQLException {
		for (double icdCode : icdCodes) {
			ps.setDouble(start++, icdCode);
		}
	}

	/**
	 * Returns if the patient has every smoked in their life
	 * 
	 * @param patientID
	 * @return
	 * @throws DBException
	 */
	public boolean hasSmoked(long patientID) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn
					.prepareStatement("SELECT * FROM PersonalHealthInformation WHERE PatientID=? AND Smoker=1");
			ps.setLong(1, patientID);
			return ps.executeQuery().next(); // if this query has ANY rows, then yes
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}

	/**
	 * Returns if a patient has ever been diagnosed with the given ICD code, in the range [lower,upper)
	 * 
	 * @param patientID
	 * @param lowerICDCode
	 * @param upperICDCode
	 * @return
	 * @throws DBException
	 */
	public boolean hadPriorDiagnoses(long patientID, double lowerICDCode, double upperICDCode)
			throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM OVDiagnosis ovd, OfficeVisits ov, Patients p "
					+ "WHERE ovd.visitID=ov.id AND ov.patientid=p.mid AND p.mid=? "
					+ "AND ovd.icdcode>=? AND ovd.icdcode<?");
			ps.setLong(1, patientID);
			ps.setDouble(2, lowerICDCode);
			ps.setDouble(3, upperICDCode);
			return ps.executeQuery().next(); // if this query has ANY rows, then yes
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
}
