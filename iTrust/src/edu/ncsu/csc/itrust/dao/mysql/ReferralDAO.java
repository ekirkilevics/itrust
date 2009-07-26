package edu.ncsu.csc.itrust.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import edu.ncsu.csc.itrust.DBUtil;
import edu.ncsu.csc.itrust.beans.ReferralBean;
import edu.ncsu.csc.itrust.beans.loaders.ReferralBeanLoader;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.DBException;

/**
 * Used to update referrals, and fetch lists of referrals sent to and
 * from HCPs
 *
 */
public class ReferralDAO {
	private DAOFactory factory;
	private ReferralBeanLoader referralLoader;

	public ReferralDAO(DAOFactory factory) {
		this.factory = factory;
		referralLoader = new ReferralBeanLoader();
	}

	

	/**
	 * Gets a list of all referrals sent from an HCP
	 * @param mid The HCP's mid.
	 * @return The list of the referrals they sent.
	 * @throws DBException
	 */
	public List<ReferralBean> getReferralsSentFrom(long mid) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM referrals WHERE SenderID = ?");
			ps.setLong(1, mid);
			ResultSet rs = ps.executeQuery();
			
			
			return referralLoader.loadList(rs);
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
	
	
	/**
	 * Gets a list of all referrals sent to an HCP
	 * @param mid The HCP's mid.
	 * @return The list of the referrals sent to them.
	 * @throws DBException
	 */
	public List<ReferralBean> getReferralsSentTo(long mid) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM referrals WHERE ReceiverID = ?");
			ps.setLong(1, mid);
			ResultSet rs = ps.executeQuery();
			
			
			return referralLoader.loadList(rs);
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}


	/**
	 * Updates a given referral in the database.
	 * @param r The referral to update.
	 * @throws DBException
	 */
	public void editReferral(ReferralBean r) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("UPDATE referrals SET PatientID=?,SenderID=?,ReceiverID=?,"
					+ "ReferralDetails=?,ConsultationDetails=?,Status=?  WHERE ID=?");
			referralLoader.loadParameters(ps, r);
			ps.setLong(7, r.getId());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
	
	/**
	 * Updates a given referral in the database.
	 * @param r The referral to update.
	 * @throws DBException
	 */
	public void addReferral(ReferralBean r) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("INSERT INTO referrals (PatientID,SenderID,ReceiverID,"
					+ "ReferralDetails,ConsultationDetails,Status)  "
					+ "VALUES (?,?,?,?,?,?)");
			referralLoader.loadParameters(ps, r);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
	
}
