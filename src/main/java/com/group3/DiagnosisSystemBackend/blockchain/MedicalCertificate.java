package com.group3.DiagnosisSystemBackend.blockchain;

public class MedicalCertificate {
	private String patientAddress;
	private String symptoms;
	private String levels;
	
	public String getPatientAddress() {
		return patientAddress;
	}
	
	public void setPatientAddress(String patientAddress) {
		this.patientAddress = patientAddress;
	}
	
	public String getSymptoms() {
		return symptoms;
	}
	
	public void setSymptoms(String symptoms) {
		this.symptoms = symptoms;
	}
	
	public String getLevels() {
		return levels;
	}
	
	public void setLevels(String levels) {
		this.levels = levels;
	}
	
	public boolean checkNotNULL() {
		if ( (patientAddress==null) || (symptoms==null) || (levels==null)) return true;
		return false;
	}
	
	public void showMedicalCertificate() {
		System.out.println(patientAddress);
    	System.out.println(symptoms);
    	System.out.println(levels);
	}
}
