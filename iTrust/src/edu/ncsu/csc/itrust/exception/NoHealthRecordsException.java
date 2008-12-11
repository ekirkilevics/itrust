package edu.ncsu.csc.itrust.exception;

public class NoHealthRecordsException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NoHealthRecordsException() {
		super("No health records exist for this patient");
	}
}
