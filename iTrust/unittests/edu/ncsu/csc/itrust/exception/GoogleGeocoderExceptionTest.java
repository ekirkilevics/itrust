package edu.ncsu.csc.itrust.exception;

import junit.framework.TestCase;

public class GoogleGeocoderExceptionTest extends TestCase {
	public void testMessage() throws Exception {
		try{
			throw new GoogleGeocoderException("Test");
		}catch(GoogleGeocoderException e){
			assertTrue(e.getMessage().equals("Test"));
		}
	}
}
