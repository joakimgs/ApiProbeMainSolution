package com.infrasight.tests.apiprobe;

/**
 * Represents an office (plats) for Arbetsformedligen
 */
class Office {
	/** Office Code (platskod) **/
	String officeCode;
	
	/** Office name (platsnamn) **/
	String officeName;
	
	/** Contact e-mail **/
	String email;
	
	public Office(String officeName, String officeCode) {
		this.officeName = officeName;
		this.officeCode = officeCode;
	}
	
	public String getOfficeCode() {
		return officeCode;
	}
	
	public String getOfficeName() {
		return officeName;
	}

	public String toString() {
		return officeName + " (" + officeCode + "): ";// + email;
	}
}
