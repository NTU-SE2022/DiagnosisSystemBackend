package com.group3.DiagnosisSystemBackend.dto;

public class ClinicReplaceResponse {
	private int status;
	private Patient response;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Patient getResponse() {
		return response;
	}

	public void setResponse(Patient response) {
		this.response = response;
	}
}
