package com.group3.DiagnosisSystemBackend.dto;

import java.util.List;
import com.group3.DiagnosisSystemBackend.dao.PatientAddress;

public class PatientAddressesList {
	private List<PatientAddress> patientAddressesList;
	
	public PatientAddressesList(List<PatientAddress> patientsAddressesList) {
		this.patientAddressesList = patientsAddressesList;
	}

	public List<PatientAddress> getPatientAddressesList() {
		return patientAddressesList;
	}

	public void setPatient(List<PatientAddress> patientsAddressesList) {
		this.patientAddressesList = patientsAddressesList;
	}

}
