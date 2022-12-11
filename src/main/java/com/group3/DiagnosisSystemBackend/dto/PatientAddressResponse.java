package com.group3.DiagnosisSystemBackend.dto;

public class PatientAddressResponse extends Response{
	
	private PatientAddressesList data;
	
	public PatientAddressResponse(int status, String message, PatientAddressesList patientsData) {
		super(status, message);
		this.data = patientsData;
		// TODO Auto-generated constructor stub
	}

	public PatientAddressesList getData() {
		return data;
	}

	public void setData(PatientAddressesList patientsData) {
		this.data = patientsData;
	}
}
