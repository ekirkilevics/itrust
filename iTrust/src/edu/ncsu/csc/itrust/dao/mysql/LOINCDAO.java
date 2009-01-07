package edu.ncsu.csc.itrust.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import edu.ncsu.csc.itrust.DBUtil;
import edu.ncsu.csc.itrust.beans.LOINCbean;
import edu.ncsu.csc.itrust.beans.loaders.LOINCBeanLoader;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.iTrustException;

public class LOINCDAO {
	private DAOFactory factory;
	private LOINCBeanLoader LOINCLoader;

	public LOINCDAO(DAOFactory factory) {
		this.factory = factory;
		LOINCLoader = new LOINCBeanLoader();
	}
	
	/**
	 * Adds a LOINC
	 * 
	 * @param hosp
	 * @return
	 * @throws DBException
	 * @throws iTrustException
	 */
	public void addLOINC(LOINCbean hosp) throws DBException, iTrustException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("INSERT INTO LOINC (LaboratoryProcedureCode, Component, KindOfProperty, TimeAspect, System, ScaleType, MethodType) " + "VALUES (?,?,?,?,?,?,?)");
			ps.setString(1, hosp.getLabProcedureCode());
			ps.setString(2, hosp.getComponent());
			ps.setString(3, hosp.getKindOfProperty());
			ps.setString(4, hosp.getTimeAspect());
			ps.setString(5, hosp.getSystem());
			ps.setString(6, hosp.getScaleType());
			ps.setString(7, hosp.getMethodType());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			if (1062 == e.getErrorCode())
				throw new iTrustException("Error: LOINC already exists.");
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}	

	/**
	 * Gets a LOINC by LaboratoryProcedureCode
	 * 
	 * @param hosp
	 * @return
	 * @throws DBException
	 * @throws iTrustException
	 */

	
	/**
	 * Returns all LOINCs associated with LaboratoryProcedureCode
	 * 
	 * @param visitID
	 * @return
	 * @throws DBException
	 */
	public List<LOINCbean> getProcedures(String visitID) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("Select * From LOINC Where LaboratoryProcedureCode = ? ");
			ps.setString(1, visitID);
			ResultSet rs = ps.executeQuery();
			return LOINCLoader.loadList(rs);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
	
	
	/**
	 * Updates the information in LOINC.
	 * 
	 * @param ov
	 * @throws DBException
	 */
	public int update(LOINCbean ov) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("UPDATE LOINC SET Component=?, KindOfProperty=?, TimeAspect=?, System=?, ScaleType=?, MethodType=? WHERE LaboratoryProcedureCode=?");
			ps.setString(1, ov.getComponent());
			ps.setString(2, ov.getKindOfProperty());
			ps.setString(3, ov.getTimeAspect());
			ps.setString(4, ov.getSystem());
			ps.setString(5, ov.getScaleType());
			ps.setString(6, ov.getMethodType());
			ps.setString(7, ov.getLabProcedureCode());
			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
	
	/**
	 * this may not be needed
	 * @return
	 * @throws DBException
	 */
		public List<LOINCbean> getAllLOINC() throws DBException {
			Connection conn = null;
			PreparedStatement ps = null;

			try {
				conn = factory.getConnection();
				ps = conn.prepareStatement("SELECT * FROM LOINC");
				ResultSet rs = ps.executeQuery();
				return LOINCLoader.loadList(rs);
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DBException(e);
			} finally {
				DBUtil.closeConnection(conn, ps);
			}
		}


}