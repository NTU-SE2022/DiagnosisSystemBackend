package com.group3.DiagnosisSystemBackend.blockchain;

import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;

public class MedicalCertificate {
	@Schema(example="0x2c4AF1E09e27b7d24fE121a912Cf12b7b7582b96")
	private String patientAddress;
	
	@Schema(example="Burning,cancer,Diving", description="symptoms are seperated by coma.")
	private String symptoms;
	
	@Schema(example="0,2,1", description="levels are seperated by coma.")
	private String levels;
	
	public MedicalCertificate() {
	}
	
	public MedicalCertificate(String patientAddress, String symptoms, String levels) {
		this.patientAddress = patientAddress;
		this.symptoms = symptoms;
		this.levels = levels;
	}
	
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
	
	public void showMedicalCertificate() {
		System.out.println(patientAddress);
    	System.out.println(symptoms);
    	System.out.println(levels);
	}
}
