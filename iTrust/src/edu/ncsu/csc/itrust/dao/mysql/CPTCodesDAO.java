package edu.ncsu.csc.itrust.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import edu.ncsu.csc.itrust.DBUtil;
import edu.ncsu.csc.itrust.beans.ProcedureBean;
import edu.ncsu.csc.itrust.beans.loaders.ProcedureBeanLoader;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.iTrustException;

/**
 * Used for managing CPT codes.
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
public class CPTCodesDAO {
	private DAOFactory factory;
	private ProcedureBeanLoader procedureBeanLoader = new ProcedureBeanLoader();

	public CPTCodesDAO(DAOFactory factory) {
		this.factory = factory;
	}

	/**
	 * Returns a list of all CPT codes.
	 * 
	 * @return
	 * @throws DBException
	 */
	public List<ProcedureBean> getAllCPTCodes() throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM CPTCodes ORDER BY CODE");
			ResultSet rs = ps.executeQuery();
			return procedureBeanLoader.loadList(rs);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
	
	/**
	 * Returns a list of all CPT codes.
	 * 
	 * @return
	 * @throws DBException
	 */
	public List<ProcedureBean> getImmunizationCPTCodes() throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("select * from cptcodes where attribute='immunization' order by code");
			ResultSet rs = ps.executeQuery();
			return procedureBeanLoader.loadList(rs);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}

	/**
	 * Returns a particular procedure description for a code.
	 * 
	 * @param code
	 * @return
	 * @throws DBException
	 */
	public ProcedureBean getCPTCode(String code) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM CPTCodes WHERE Code = ?");
			ps.setString(1, code);
			ResultSet rs = ps.executeQuery();
			if (rs.next())
				return procedureBeanLoader.loadSingle(rs);
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}

	/**
	 * Adds a new CPT code, returns that it was added successfully
	 * 
	 * @param proc
	 * @return
	 * @throws DBException
	 * @throws iTrustException
	 */
	public boolean addCPTCode(ProcedureBean proc) throws DBException, iTrustException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("INSERT INTO CPTCodes (Code, Description, Attribute) " + "VALUES (?,?,?)");
			ps.setString(1, proc.getCPTCode());
			ps.setString(2, proc.getDescription());
			ps.setString(3, proc.getAttribute());
			return (1 == ps.executeUpdate());
		} catch (SQLException e) {
			e.printStackTrace();
			if (1062 == e.getErrorCode())
				throw new iTrustException("Error: Code already exists.");
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}

	/**
	 * Change the procedure description for a particular CPT code
	 * 
	 * @param proc
	 * @return
	 * @throws DBException
	 */
	public int updateCode(ProcedureBean proc) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("UPDATE CPTCodes SET Description = ?, Attribute = ? WHERE Code = ?");
			ps.setString(1, proc.getDescription());
			ps.setString(2, proc.getAttribute());
			ps.setString(3, proc.getCPTCode());
			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}

}
