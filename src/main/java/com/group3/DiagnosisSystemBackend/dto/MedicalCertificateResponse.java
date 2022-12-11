package com.group3.DiagnosisSystemBackend.dto;

public class MedicalCertificateResponse extends Response {
	
	private MedicalCertificatesList data;
	
	public MedicalCertificateResponse(int status, String message, MedicalCertificatesList medicalCertificatesList) {
		super(status, message);
		this.data = medicalCertificatesList;
	}
	
	public MedicalCertificatesList getData() {
		return data;
	}
	
	public void setData(MedicalCertificatesList medicalCertificatesList) {
		this.data = medicalCertificatesList;
	}

}
