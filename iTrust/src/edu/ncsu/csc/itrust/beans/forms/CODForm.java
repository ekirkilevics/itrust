package edu.ncsu.csc.itrust.beans.forms;

import java.util.Date;
import edu.ncsu.csc.itrust.enums.Gender;

public class CODForm {
	private String afterYear = "";
	private String beforeYear = "";
	private Gender gen = Gender.NotSpecified;

	public Gender getGen() {
		return this.gen;
	}

	public void setGen(Gender gen) {
		this.gen = gen;
	}

	public static int getCurrentYear() {
		Date d = new Date();
		return Integer.parseInt(d.toString().substring(d.toString().length() - 4));
	}

	public String getAfterDate() {
		return this.afterYear + "-01-01";
	}

	public void setAfterYear(String afterDate) {
		this.afterYear = afterDate;
	}

	public String getBeforeDate() {
		return this.beforeYear + "-12-31";
	}

	public void setBeforeYear(String beforeDate) {
		this.beforeYear = beforeDate;
	}

	public String getAfterYear() {
		return afterYear;
	}

	public String getBeforeYear() {
		return beforeYear;
	}
}
