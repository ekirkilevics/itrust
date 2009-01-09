package edu.ncsu.csc.itrust.testutils;

import java.io.FileNotFoundException;
import java.io.PrintStream;

public class MockSystemOut extends PrintStream {
	private String console = "";

	public MockSystemOut() throws FileNotFoundException {
		super("doc/readme/err.txt");
	}

	public String getConsole() {
		return console;
	}

	@Override
	public void println(String x) {
		console += x + "\n";
	}
}
