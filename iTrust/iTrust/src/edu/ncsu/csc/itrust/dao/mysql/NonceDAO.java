package edu.ncsu.csc.itrust.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import edu.ncsu.csc.itrust.DBUtil;
import edu.ncsu.csc.itrust.beans.NonceBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.DBException;

/**
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
public class NonceDAO {
	private DAOFactory factory;

	/**
	 * The typical constructor.
	 * @param factory The {@link DAOFactory} associated with this DAO, which is used for obtaining SQL connections, etc.
	 */
	public NonceDAO(DAOFactory factory) {
		this.factory = factory;
	}



	/**
	 * Adds the specified Nonce (string + timestamp) to the database
	 */
	public void addNonce(NonceBean bean) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		String nonce = bean.getNonce();
		Timestamp expires = bean.getExpires();
		expires.setNanos(0);
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("INSERT INTO Nonces (nonce, expires) "
					+ "VALUES (?,?)");
			ps.setString(1, nonce);
			ps.setTimestamp(2, expires);
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}

	/**
	 * Returns the nonce in the database matching the given string, null if not there
	 * @throws DBException 
	 */
	public NonceBean getNonce(String nonceText) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		NonceBean n=null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM Nonces WHERE nonce=?");
			ps.setString(1, nonceText);
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				String newNonceText = rs.getString("nonce");
				Timestamp expires = rs.getTimestamp("expires");
				expires.setNanos(0);
				NonceBean nonce = new NonceBean();
				nonce.setNonce(newNonceText);
				nonce.setExpires(expires);
				
				n=nonce;
			}
		} catch (SQLException e) {
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
		return n;
	}

}
