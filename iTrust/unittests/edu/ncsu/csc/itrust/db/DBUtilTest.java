package edu.ncsu.csc.itrust.db;

import java.io.PrintStream;
import java.sql.Connection;
import junit.framework.TestCase;
import edu.ncsu.csc.itrust.DBUtil;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.testutils.MockSystemOut;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;

public class DBUtilTest extends TestCase {

	public void testDB() throws Exception {
		DAOFactory df = TestDAOFactory.getTestInstance();
		Connection c = df.getConnection();
		c.close();
		PrintStream err = System.err;
		//Assert what has been printed to the console
		MockSystemOut mockOut = new MockSystemOut();
		System.setErr(mockOut);
		DBUtil.closeConnection(c, null);
		assertEquals("Error closing connections", mockOut.getConsole().split("\n")[0]);
		System.setErr(err);
	}
}
