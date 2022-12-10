package com.group3.DiagnosisSystemBackend.dto;

import java.util.ArrayList;
import java.util.List;

import com.group3.DiagnosisSystemBackend.blockchain.MedicalCertificate;

public class MedicalCertificatesList {
	private ArrayList<MedicalCertificate> medicalCertificatesList;
	
	public MedicalCertificatesList() {
		// TODO Auto-generated constructor stub
		medicalCertificatesList = new ArrayList<MedicalCertificate>();
	}
	
	public List<MedicalCertificate> getMedicalCertificatesList() {
		return medicalCertificatesList;
	}

	public void setMedicalCertificatesList(ArrayList<MedicalCertificate> medicalCertificatesList) {
		this.medicalCertificatesList = medicalCertificatesList;
	}
	
	public void addMedicalCertificate(MedicalCertificate medicalCertificate) {
		medicalCertificatesList.add(medicalCertificate);
	}

}
