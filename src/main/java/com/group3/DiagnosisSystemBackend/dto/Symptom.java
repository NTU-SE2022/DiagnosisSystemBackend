package com.group3.DiagnosisSystemBackend.dto;

import java.util.List;

public class Symptom {
	private List<com.group3.DiagnosisSystemBackend.dao.Symptom> symptom;

	public List<com.group3.DiagnosisSystemBackend.dao.Symptom> getSymptom() {
		return symptom;
	}

	public void setSymptom(List<com.group3.DiagnosisSystemBackend.dao.Symptom> symptom) {
		this.symptom = symptom;
	}
	
}
