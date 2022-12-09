package com.group3.DiagnosisSystemBackend.dto;

import java.util.List;
import com.group3.DiagnosisSystemBackend.dao.PatientAddress;

public class Patient {
	private List<PatientAddress> patient;

	public List<PatientAddress> getPatient() {
		return patient;
	}

	public void setPatient(List<PatientAddress> patient) {
		this.patient = patient;
	}

}
