/**
 * 
 */
package edu.ncsu.csc.itrust.beans.loaders;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import edu.ncsu.csc.itrust.beans.ExpertBean;

/**
 * @author Tiago Sommer Damasceno (tsdamasc)
 *
 */
public class ExpertBeanLoader {
	public List<ExpertBean> loadList(ResultSet rs) throws SQLException {
		ArrayList<ExpertBean> list = new ArrayList<ExpertBean>();
		while (rs.next()) {
			list.add(loadSingle(rs));
		}
		return list;
	}
	
	public ExpertBean loadSingle(ResultSet rs) throws SQLException {
		ExpertBean expert = new ExpertBean(rs.getString("HospitalName"), rs.getString("Address"), rs.getString("City"), rs.getString("State"), rs.getString("Zip"), rs.getDouble("Lat"), rs.getDouble("Lng"), rs.getString("Frequency"), rs.getString("LaboratoryProcedureCode"));
		return expert;
	}

}
