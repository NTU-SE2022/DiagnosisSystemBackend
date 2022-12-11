package com.group3.DiagnosisSystemBackend.dto;

import com.group3.DiagnosisSystemBackend.dao.Clinic;

public class ClinicResponse extends Response{

	private Clinic data;
	
	public ClinicResponse(int status, String message, Clinic ClinicInfo) {
		super(status, message);
		// TODO Auto-generated constructor stub
		this.data = ClinicInfo;
	}

	public Clinic getData() {
		return data;
	}

	public void setData(Clinic ClinicInfo) {
		this.data = ClinicInfo;
	}
}
