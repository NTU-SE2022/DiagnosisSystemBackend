package com.group3.DiagnosisSystemBackend.dto;

public class SymptomResponse extends Response{

	private SymptomsList data;
	
	public SymptomResponse(int status, String message, SymptomsList symtpomsList) {
		super(status, message);
		this.data = symtpomsList;
		// TODO Auto-generated constructor stub
	}

	public SymptomsList getData() {
		return data;
	}

	public void setData(SymptomsList symtpomsList) {
		this.data = symtpomsList;
	}
}
