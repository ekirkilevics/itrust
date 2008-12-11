package edu.ncsu.csc.itrust.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import edu.ncsu.csc.itrust.DBUtil;
import edu.ncsu.csc.itrust.beans.OperationalProfile;
import edu.ncsu.csc.itrust.beans.TransactionBean;
import edu.ncsu.csc.itrust.beans.loaders.OperationalProfileLoader;
import edu.ncsu.csc.itrust.beans.loaders.TransactionBeanLoader;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.enums.TransactionType;
import edu.ncsu.csc.itrust.exception.DBException;

/**
 * Used for the logging mechanism.
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
public class TransactionDAO {
	private DAOFactory factory;
	private TransactionBeanLoader loader = new TransactionBeanLoader();
	private OperationalProfileLoader operationalProfileLoader = new OperationalProfileLoader();

	public TransactionDAO(DAOFactory factory) {
		this.factory = factory;
	}

	/**
	 * Returns the whole transaction log
	 * 
	 * @return
	 * @throws DBException
	 */
	public List<TransactionBean> getAllTransactions() throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM TransactionLog ORDER BY timeLogged DESC");
			ResultSet rs = ps.executeQuery();
			return loader.loadList(rs);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}

	/**
	 * Log a transaction, just giving it the person who is logged in and the type
	 * 
	 * @param type
	 * @param loggedInMID
	 * @throws DBException
	 */
	public void logTransaction(TransactionType type, long loggedInMID) throws DBException {
		logTransaction(type, loggedInMID, 0L, "");
	}

	/**
	 * Log a transaction, with all of the info. The meaning of secondaryMID and addedInfo changes depending on
	 * the transaction type.
	 * 
	 * @param type
	 * @param loggedInMID
	 * @param secondaryMID
	 * @param addedInfo
	 * @throws DBException
	 */
	public void logTransaction(TransactionType type, long loggedInMID, long secondaryMID, String addedInfo)
			throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("INSERT INTO TransactionLog(loggedInMID, secondaryMID, "
					+ "transactionCode, addedInfo) VALUES(?,?,?,?)");
			ps.setLong(1, loggedInMID);
			ps.setLong(2, secondaryMID);
			ps.setInt(3, type.getCode());
			ps.setString(4, addedInfo);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}

	/**
	 * Return a list of all transactions in which an HCP accessed the given patient's record
	 * 
	 * @param patientID
	 * @return
	 * @throws DBException
	 */
	public List<TransactionBean> getAllRecordAccesses(long patientID) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = factory.getConnection();
			ps = conn
					.prepareStatement("SELECT * FROM TransactionLog WHERE secondaryMID=? AND transactionCode "
							+ "IN(" + TransactionType.patientViewableStr + ") ORDER BY timeLogged DESC");
			ps.setLong(1, patientID);
			ResultSet rs = ps.executeQuery();
			return loader.loadList(rs);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}

	/**
	 * Return a list of all transactions in which an HCP accessed the given patient's record, within the dates
	 * 
	 * @param patientID
	 * @param lower
	 * @param upper
	 * @return
	 * @throws DBException
	 */
	public List<TransactionBean> getRecordAccesses(long patientID, Date lower, Date upper) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn
					.prepareStatement("SELECT * FROM TransactionLog WHERE secondaryMID=? AND transactionCode IN ("
							+ TransactionType.patientViewableStr
							+ ") "
							+ "AND timeLogged >= ? AND timeLogged <= ? ORDER BY timeLogged DESC");
			ps.setLong(1, patientID);
			ps.setTimestamp(2, new Timestamp(lower.getTime()));
			// add 1 day's worth to include the upper
			ps.setTimestamp(3, new Timestamp(upper.getTime() + 1000L * 60L * 60 * 24L));
			ResultSet rs = ps.executeQuery();
			return loader.loadList(rs);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}

	/**
	 * Returns the operation profile
	 * 
	 * @return
	 * @throws DBException
	 */
	public OperationalProfile getOperationalProfile() throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT TransactionCode, count(transactionID) as TotalCount, "
					+ "count(if(loggedInMID<9000000000, transactionID, null)) as PatientCount, "
					+ "count(if(loggedInMID>=9000000000, transactionID, null)) as PersonnelCount "
					+ "FROM TransactionLog GROUP BY transactionCode ORDER BY transactionCode ASC");
			ResultSet rs = ps.executeQuery();
			return operationalProfileLoader.loadSingle(rs);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
}
