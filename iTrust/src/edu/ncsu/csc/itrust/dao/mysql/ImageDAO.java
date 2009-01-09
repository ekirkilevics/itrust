package edu.ncsu.csc.itrust.dao.mysql;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import edu.ncsu.csc.itrust.DBUtil;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.DBException;

public class ImageDAO {
	private DAOFactory factory;

	public ImageDAO(DAOFactory factory) {
		this.factory = factory;
	}

	public int insertImage(InputStream fis) throws DBException, FileNotFoundException, IOException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("INSERT INTO images (img_id, image) VALUES (NULL, ?)");
					ps.setBinaryStream(1,fis,fis.available());
					
			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
	
	public long getLastImageId() throws DBException
	{
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT MAX(img_id) FROM images");
			ResultSet rs = ps.executeQuery();
			rs.next();
			return rs.getLong(1);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
	
	public int deleteImage(int id) throws DBException
	{
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("DELETE FROM images WHERE id = ?");
			int x = ps.executeUpdate();
			return x;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
	
	public InputStream getImageWithId(long id)  
	{ 
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM Images where img_id = ?");
					ps.setLong(1, id);
					
			ResultSet rs = ps.executeQuery();
			if (rs.first())
				return rs.getBinaryStream("image");
			else
			{
				return null;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
		return null;
	}

}
