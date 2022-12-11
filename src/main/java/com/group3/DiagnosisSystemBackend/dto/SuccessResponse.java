package com.group3.DiagnosisSystemBackend.dto;

public class SuccessResponse extends Response {
	private boolean success;
	
	public SuccessResponse(int status, String message, boolean success) {
		super(status, message);
		this.success = success;
	}
	
	public boolean getSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

}
