package edu.ncsu.csc.itrust.enums;

public enum Gender {
	Male("Male"), Female("Female"), NotSpecified("Not Specified");
	private String name;

	private Gender(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return getName();
	}

	public static Gender parse(String input) {
		for (Gender gender : Gender.values()) {
			if (gender.name.equals(input))
				return gender;
		}
		return NotSpecified;
	}
}
