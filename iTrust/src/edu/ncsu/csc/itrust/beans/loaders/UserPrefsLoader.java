package edu.ncsu.csc.itrust.beans.loaders;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import edu.ncsu.csc.itrust.beans.PersonnelBean;
import edu.ncsu.csc.itrust.beans.UserPrefsBean;
import edu.ncsu.csc.itrust.enums.Role;

/**
 * A loader for PersonnelBeans.
 * 
 * Loads in information to/from beans using ResultSets and PreparedStatements. Use the superclass to enforce consistency. 
 * For details on the paradigm for a loader (and what its methods do), see {@link BeanLoader}
 */
public class UserPrefsLoader implements BeanLoader<UserPrefsBean> {
	public List<UserPrefsBean> loadList(ResultSet rs) throws SQLException {
		//not implemented
		return null;
	}

	public UserPrefsBean loadSingle(ResultSet rs) throws SQLException {
		UserPrefsBean p = new UserPrefsBean();
		p.setMID(rs.getLong("MID"));
		p.setThemeColor(rs.getString("ThemeColor"));
		p.setSecondaryColor(rs.getString("SecondaryColor"));
		return p;
	}

	public PreparedStatement loadParameters(PreparedStatement ps, UserPrefsBean p) throws SQLException {
		ps.setLong(1, p.getMID());
		ps.setString(2, p.getThemeColor());
		ps.setString(3, p.getSecondaryColor());
		return ps;
	}
}
