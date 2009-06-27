package edu.ncsu.csc.itrust.beans.loaders;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import edu.ncsu.csc.itrust.beans.CODBean;

public class CODBeanLoader implements BeanLoader<CODBean> {

	public List<CODBean> loadList(ResultSet rs) throws SQLException {
		ArrayList<CODBean> list = new ArrayList<CODBean>();
		while (rs.next()) {
			list.add(loadSingle(rs));
		}
		return list;
	}

	public CODBean loadSingle(ResultSet rs) throws SQLException {
		CODBean cod = new CODBean();

		cod.setDiagnosisName(rs.getString("Description"));
		cod.setIcdCode(rs.getString("CauseOfDeath"));
		cod.setTotal(rs.getInt("Total"));

		return cod;
	}

	public PreparedStatement loadParameters(PreparedStatement ps, CODBean bean) throws SQLException {
		throw new IllegalStateException("unimplemented!");
	}

}
