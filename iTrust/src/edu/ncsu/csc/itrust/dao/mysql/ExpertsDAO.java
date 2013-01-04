/**
 * 
 */
package edu.ncsu.csc.itrust.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import edu.ncsu.csc.itrust.DBUtil;
import edu.ncsu.csc.itrust.beans.ExpertBean;
import edu.ncsu.csc.itrust.beans.loaders.ExpertBeanLoader;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.DBException;

/**
 * @author Tiago Sommer Damasceno (tsdamasc)
 *
 */
public class ExpertsDAO {
	private DAOFactory factory;
	private ExpertBeanLoader expertLoader = new ExpertBeanLoader();

	
	public ExpertsDAO (DAOFactory factory) {
		this.factory = factory;
	}
	
	public List<ExpertBean> getTopFiveExperts(String LOINC) throws DBException {
		List<ExpertBean> list = getExperts(LOINC);
		if(list.size() <= 5) return list;
		
		// Gets top 5 Experts
		int size = list.size();
		while (size > 5) {list.remove(--size);} // Removes Top 6 to Top <size>
		
		return list;
		
	}
	
	public List<ExpertBean> getExperts(String LOINC) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT HospitalName, Address, City, State, Zip, Lat, Lng, count(LaboratoryProcedureCode) AS \"Frequency\", LaboratoryProcedureCode FROM labprocedure,officevisits,hospitals WHERE officevisits.ID = labprocedure.OfficeVisitID AND officevisits.HospitalID = hospitals.HospitalID AND LaboratoryProcedureCode = ? GROUP BY HospitalName ORDER BY Frequency DESC");
			ps.setString(1, LOINC);
			ResultSet rs = ps.executeQuery();
			return expertLoader.loadList(rs);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
	

}
