package edu.ncsu.csc.itrust.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import edu.ncsu.csc.itrust.DBUtil;
import edu.ncsu.csc.itrust.beans.UserPrefsBean;
import edu.ncsu.csc.itrust.beans.loaders.UserPrefsLoader;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.DBException;

/**
 * UserPrefsDAO is for all queries related to user preferences.
 * 
 * DAO stands for Database Access Object. All DAOs are intended to be reflections of the database, that is,
 * one DAO per table in the database (most of the time). For more complex sets of queries, extra DAOs are
 * added. DAOs can assume that all data has been validated and is correct.
 * 
 * DAOs should never have setters or any other parameter to the constructor than a factory. All DAOs should be
 * accessed by DAOFactory (@see {@link DAOFactory}) and every DAO should have a factory - for obtaining JDBC
 * connections and/or accessing other DAOs.
 * 
 * 
 * @author Andy
 * 
 */
public class UserPrefsDAO {
	private DAOFactory factory;
	private UserPrefsLoader userPrefsLoader;

	/**
	 * The typical constructor.
	 * @param factory The {@link DAOFactory} associated with this DAO, which is used for obtaining SQL connections, etc.
	 */
	public UserPrefsDAO(DAOFactory factory) {
		userPrefsLoader = new UserPrefsLoader();
		this.factory = factory;
	}

	/**
	 * Retrieves a UserPrefsBean with all of the specific information for a given user.
	 * 
	 * @param mid The MID of the user in question.
	 * @return A UserPrefsBean representing the employee.
	 * @throws DBException
	 */
	public UserPrefsBean getUserPrefs(long mid) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM UserPrefs WHERE MID = ?");
			ps.setLong(1, mid);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return userPrefsLoader.loadSingle(rs);
			} else
				return null;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
	
	/**
	 * Updates the user preferences for a user.
	 * 
	 * @param p The UserPrefs bean with the updated information.
	 * @throws DBException
	 */
	public void editUserPrefs(UserPrefsBean p) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("INSERT INTO UserPrefs (MID, ThemeColor, SecondaryColor) " +
					"VALUES (?,?,?) ON DUPLICATE KEY UPDATE ThemeColor=?,SecondaryColor=?");
			userPrefsLoader.loadParameters(ps, p);
			ps.setString(4, p.getThemeColor());
			ps.setString(5, p.getSecondaryColor());
			System.out.println(ps+"\n\n\n\n\n\n");
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
}
