package com.group3.DiagnosisSystemBackend.dto;

public class SymptomResponse {
	private int status;
	private Symptom response;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Symptom getResponse() {
		return response;
	}

	public void setResponse(Symptom response) {
		this.response = response;
	}
}
