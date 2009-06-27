package edu.ncsu.csc.itrust.beans.loaders;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface BeanLoader<T> {
	public List<T> loadList(ResultSet rs) throws SQLException;

	public T loadSingle(ResultSet rs) throws SQLException;

	public PreparedStatement loadParameters(PreparedStatement ps, T bean) throws SQLException;
}
