package edu.ncsu.csc.itrust.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import edu.ncsu.csc.itrust.DBUtil;
import edu.ncsu.csc.itrust.beans.MessageBean;
import edu.ncsu.csc.itrust.beans.loaders.MessageBeanLoader;
import edu.ncsu.csc.itrust.dao.DAOFactory;

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
public class MessageDAO {
	private DAOFactory factory;
	private MessageBeanLoader mbLoader;

	/**
	 * The typical constructor.
	 * @param factory The {@link DAOFactory} associated with this DAO, which is used for obtaining SQL connections, etc.
	 */
	public MessageDAO(DAOFactory factory) {
		this.factory = factory;
		this.mbLoader = new MessageBeanLoader();
	}

	public List<MessageBean> getMessagesFor(long mid) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;

		conn = factory.getConnection();
		ps = conn.prepareStatement("SELECT * FROM message WHERE to_id = ? ORDER BY sent_date DESC");
		ps.setLong(1, mid);
		ResultSet rs = ps.executeQuery();

		List<MessageBean> mbList = this.mbLoader.loadList(rs);

		DBUtil.closeConnection(conn, ps);

		return mbList;
	}

	public void addMessage(MessageBean mBean) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;

		conn = factory.getConnection();
		if (mBean.getParentMessageId() == 0L) {
			ps = conn.prepareStatement(
					"INSERT INTO message (from_id, to_id, sent_date, message) "
				  + "VALUES (?, ?, NOW(), ?)");
			this.mbLoader.loadParameters(ps, mBean);
		} else {
			ps = conn.prepareStatement(
					"INSERT INTO message (from_id, to_id, sent_date, message, parent_msg_id) "
				  + "  VALUES (?, ?, NOW(), ?, ?)");
			this.mbLoader.loadParameters(ps, mBean);
		}

		ps.executeUpdate();

		DBUtil.closeConnection(conn, ps);
	}
}