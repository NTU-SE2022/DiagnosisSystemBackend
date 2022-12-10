package com.group3.DiagnosisSystemBackend.dto;

import com.group3.DiagnosisSystemBackend.dao.Clinic;

public class ClinicResponse {
	private int status;
	private Clinic response;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Clinic getResponse() {
		return response;
	}

	public void setResponse(Clinic response) {
		this.response = response;
	}
}
