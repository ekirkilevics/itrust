package edu.ncsu.csc.itrust.exception;

public class NoHealthRecordsException extends iTrustException {

	public static final String MESSAGE = "The patient did not have any health records recorded. "
			+ "No risks can be calculated if no records exist";

	private static final long serialVersionUID = 7082694071460355325L;

	public NoHealthRecordsException() {
		super(MESSAGE);
	}
}
