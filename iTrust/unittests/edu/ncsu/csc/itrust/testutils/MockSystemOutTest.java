package edu.ncsu.csc.itrust.testutils;

import java.io.PrintStream;
import junit.framework.TestCase;

public class MockSystemOutTest extends TestCase {
	public void testOutput() throws Exception {
		PrintStream systemout = System.out;
		MockSystemOut mockOut = new MockSystemOut();
		System.setOut(mockOut);
		System.out.println("test error");
		assertEquals("test error\n", mockOut.getConsole());
		System.setOut(systemout);
	}
}
