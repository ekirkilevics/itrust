package edu.ncsu.csc.itrust.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import edu.ncsu.csc.itrust.DBUtil;
import edu.ncsu.csc.itrust.beans.HospitalBean;
import edu.ncsu.csc.itrust.beans.PersonnelBean;
import edu.ncsu.csc.itrust.beans.PrescriptionBean;
import edu.ncsu.csc.itrust.beans.loaders.HospitalBeanLoader;
import edu.ncsu.csc.itrust.beans.loaders.PersonnelLoader;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.iTrustException;
import edu.ncsu.csc.itrust.enums.Role;

/**
 * Used for managing information related to personnel: HCPs, UAPs, Admins
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
public class PersonnelDAO {
	private DAOFactory factory;
	private PersonnelLoader personnelLoader;
	private HospitalBeanLoader hospitalBeanLoader;

	public PersonnelDAO(DAOFactory factory) {
		this.factory = factory;
		personnelLoader = new PersonnelLoader();
		hospitalBeanLoader = new HospitalBeanLoader();
	}

	/**
	 * Returns the name for a given MID
	 * 
	 * @param mid
	 * @return
	 * @throws iTrustException
	 * @throws DBException
	 */
	public String getName(long mid) throws iTrustException, DBException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = factory.getConnection();
			pstmt = conn.prepareStatement("SELECT firstName, lastName FROM Personnel WHERE MID=?");
			pstmt.setLong(1, mid);
			ResultSet rs;

			rs = pstmt.executeQuery();
			if (rs.next()) {
				return rs.getString("firstName") + " " + rs.getString("lastName");
			} else {
				throw new iTrustException("User does not exist");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, pstmt);
		}
	}

	/**
	 * Adds an empty personnel, and returns the MID.
	 * 
	 * @return
	 * @throws DBException
	 * @throws iTrustException
	 */
	public long addEmptyPersonnel(Role role) throws DBException, iTrustException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		long newID;

		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT MAX(MID) FROM Personnel WHERE MID > 8999999999");
			rs = ps.executeQuery();
			newID = rs.next() ? rs.getLong(1) : 8999999999l;

			ps = conn.prepareStatement("INSERT INTO Personnel(MID,Role) VALUES(?,?)");
			ps.setLong(1, ++newID);
			ps.setString(2, role.name());
			ps.executeUpdate();
			return newID;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}

	/**
	 * Add an empty personnel, and the person's boss. Returns the new MID
	 * 
	 * @param amid
	 * @return
	 * @throws DBException
	 * @throws iTrustException
	 */
	public long addEmptyPersonnel(long amid) throws DBException, iTrustException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String tableRole = "";
		long newID;

		try {
			// TODO Use getLastInsert() instead
			/* QUERY WORKS UNLESS WE RUN OUT OF UAP IDs... */
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT MAX(MID) FROM Personnel WHERE MID < 9000000000");
			rs = ps.executeQuery();
			newID = rs.next() ? rs.getLong(1) : 7999999999l;
			if (0 == newID)
				newID = 7999999999l;
			tableRole = "uap";

			ps = conn.prepareStatement("INSERT INTO Personnel(MID, Role) VALUES(?, ?)");
			ps.setLong(1, ++newID);
			ps.setString(2, tableRole);
			ps.executeUpdate();
			ps = conn.prepareStatement("INSERT INTO HCPRelations(HCP, UAP) VALUES(?, ?)");
			ps.setLong(1, amid);
			ps.setLong(2, newID);
			ps.executeUpdate();
			return newID;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}

	public PersonnelBean getPersonnel(long mid) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM Personnel WHERE MID = ?");
			ps.setLong(1, mid);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return personnelLoader.loadSingle(rs);
			} else
				return null;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}

	public void editPersonnel(PersonnelBean p) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("UPDATE Personnel SET AMID=?,firstName=?,lastName=?,"
					+ "phone1=?,phone2=?,phone3=?, address1=?,address2=?,city=?, state=?, zip=?, zip1=?, zip2=?, email=?"
					+ " WHERE MID=?");
			personnelLoader.loadParameters(ps, p);
			ps.setLong(15, p.getMID());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}

	public boolean checkPersonnelExists(long pid) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM Personnel WHERE MID=?");
			ps.setLong(1, pid);
			ResultSet rs = ps.executeQuery();
			return rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}

	public List<HospitalBean> getHospitals(long mid) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM HCPAssignedHos hah,Hospitals h "
					+ "WHERE hah.HCPID=? AND hah.HosID=h.HospitalID ORDER BY HospitalName ASC");
			ps.setLong(1, mid);
			ResultSet rs = ps.executeQuery();
			return hospitalBeanLoader.loadList(rs);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
	
	public List<PersonnelBean> getAllPersonnel() throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM personnel where role in ('hcp','uap','er') ");
			ResultSet rs = ps.executeQuery();
			return personnelLoader.loadList(rs);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}

	public List<PersonnelBean> getUAPsForHCP(long hcpid) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM Personnel WHERE MID IN (SELECT UAP FROM HCPRelations WHERE HCP=?)");
			ps.setLong(1, hcpid);
			ResultSet rs = ps.executeQuery();
			return personnelLoader.loadList(rs);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
	
	public PersonnelBean getPrescribingDoctor(PrescriptionBean prescription) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM Personnel WHERE MID IN (SELECT HCPID FROM OfficeVisits WHERE ID=?)");
			ps.setLong(1, prescription.getVisitID());
			ResultSet rs = ps.executeQuery();
			return personnelLoader.loadList(rs).get(0);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
}