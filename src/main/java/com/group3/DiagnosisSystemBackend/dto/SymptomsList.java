package com.group3.DiagnosisSystemBackend.dto;

import com.group3.DiagnosisSystemBackend.dao.Symptom;
import java.util.List;

public class SymptomsList {
	private List<Symptom> symptomsList;
	
	public SymptomsList(List<Symptom> symptomsList) {
		this.symptomsList = symptomsList;
	}
	
	public List<Symptom> getSymptomsList() {
		return symptomsList;
	}

	public void setSymptomsList(List<Symptom> symptomsList) {
		this.symptomsList = symptomsList;
	}
	
}
