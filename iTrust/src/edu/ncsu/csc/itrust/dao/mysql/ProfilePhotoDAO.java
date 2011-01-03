package edu.ncsu.csc.itrust.dao.mysql;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import javax.imageio.stream.MemoryCacheImageInputStream;
import javax.imageio.stream.MemoryCacheImageOutputStream;
import javax.sql.rowset.serial.SerialBlob;
import edu.ncsu.csc.itrust.DBUtil;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.iTrustException;

public class ProfilePhotoDAO {

	private DAOFactory factory;

	public ProfilePhotoDAO(DAOFactory factory) {
		this.factory = factory;
	}

	/**
	 * Given an MID and an uploaded buffered image, store it in the database
	 * 
	 * @param mid
	 * @param photo
	 * @return
	 * @throws DBException
	 * @throws IOException
	 */
	public int store(long mid, BufferedImage photo) throws DBException, IOException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("INSERT INTO ProfilePhotos(MID, Photo) VALUES(?,?) ON DUPLICATE KEY UPDATE Photo=?");
			ps.setLong(1, mid);
			// byte[] bytes = new byte[500000]; // no bigger than this
			// ImageInputStream iis = ImageIO.createImageInputStream(photo);
			// photo.
			// iis.read(bytes);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageOutputStream ios = new MemoryCacheImageOutputStream(baos);
			ImageIO.write(photo, "jpeg", ios);
			Blob photoBlob = new SerialBlob(baos.toByteArray());
			ps.setBlob(2, photoBlob);
			ps.setBlob(3, photoBlob);
			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}

	/**
	 * Return a profile photo for the given MID
	 * 
	 * @param mid
	 * @return
	 * @throws iTrustException
	 * @throws IOException
	 */
	public BufferedImage get(long mid) throws iTrustException, IOException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM ProfilePhotos WHERE MID = ?");
			ps.setLong(1, mid);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				Blob blob = rs.getBlob("Photo");
				BufferedImage bi = ImageIO.read(new MemoryCacheImageInputStream(blob.getBinaryStream()));
				return bi;
			} else
				throw new iTrustException("No photo available");
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}

}