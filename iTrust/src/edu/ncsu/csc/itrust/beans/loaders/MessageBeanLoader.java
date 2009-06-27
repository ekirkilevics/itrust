package edu.ncsu.csc.itrust.beans.loaders;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import edu.ncsu.csc.itrust.beans.MessageBean;

public class MessageBeanLoader implements BeanLoader<MessageBean> {

	public List<MessageBean> loadList(ResultSet rs) throws SQLException {
		List<MessageBean> list = new ArrayList<MessageBean>();
		while (rs.next())
			list.add(loadSingle(rs));
		return list;
	}

	public PreparedStatement loadParameters(PreparedStatement ps, MessageBean message) throws SQLException {
			ps.setLong(1, message.getFrom());
			ps.setLong(2, message.getTo());
			ps.setString(3, message.getBody());			
		if (message.getParentMessageId() != 0L) {
			ps.setString(4, message.getBody());
		}
		return ps;
	}

	public MessageBean loadSingle(ResultSet rs) throws SQLException {
		MessageBean message = new MessageBean();
		message.setMessageId(rs.getLong("message_id"));
		message.setFrom(rs.getLong("from_id"));
		message.setTo(rs.getLong("to_id"));
		message.setBody(rs.getString("message"));
		message.setSentDate(rs.getTimestamp("sent_date"));
		return message;
	}

}
