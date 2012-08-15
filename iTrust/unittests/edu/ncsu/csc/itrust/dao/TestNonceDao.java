package edu.ncsu.csc.itrust.dao;

import java.sql.Timestamp;
import edu.ncsu.csc.itrust.beans.NonceBean;
import edu.ncsu.csc.itrust.dao.mysql.NonceDAO;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.testutils.EvilDAOFactory;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;
import junit.framework.TestCase;

public class TestNonceDao extends TestCase {
	private NonceDAO dao = TestDAOFactory.getTestInstance().getNonceDAO();
	private TestDataGenerator gen = new TestDataGenerator();
	protected void setUp() throws Exception {
		gen.clearAllTables();
		super.setUp();
	}

	protected void tearDown() throws Exception {
		
		super.tearDown();
	}

	public void testAddNonce() {
		NonceBean nonce = new NonceBean();
		nonce.setExpires(new Timestamp(System.currentTimeMillis()));
		nonce.setNonce("oogabooga");
		try {
			dao.addNonce(nonce);
		} catch (DBException e) {
			fail(e.getMessage());
		}
		
		try {
			dao.addNonce(nonce);
			fail("cannot insert duplicate nonces");
		} catch (DBException e) {
			assertNotNull(e);
		}
		
		String tooLong = "1234567890";
		tooLong = tooLong + tooLong + tooLong + tooLong; //40
		tooLong = tooLong + tooLong + tooLong + tooLong; //160
		tooLong = tooLong + tooLong + tooLong + tooLong; //640
		try {
			nonce.setNonce(tooLong);
			dao.addNonce(nonce);
			fail("nonce too long");
			
		} catch (DBException e) {
		}
	}

	public void testGetNonce() {
		NonceBean nonce = new NonceBean();
		nonce.setExpires(new Timestamp(System.currentTimeMillis()));
		nonce.setNonce("oogabooga");
		try {
			dao.addNonce(nonce);
			NonceBean nonce2 = dao.getNonce("oogabooga");

			assertEquals(nonce.getNonce(), nonce2.getNonce());
			assertEquals(nonce.getExpires().getTime()/1000,nonce2.getExpires().getTime()/1000);
		} catch (DBException e) {
			fail(e.getMessage());
		}
		
		
		
	}

}
