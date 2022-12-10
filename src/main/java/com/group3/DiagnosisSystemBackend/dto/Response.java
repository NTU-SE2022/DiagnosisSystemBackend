package com.group3.DiagnosisSystemBackend.dto;

public class Response {
	private int status;
	private String message;
	private boolean success;
	
	public Response(int status, boolean success, String message) {
		this.status = status;
		this.success = success;
		this.message = message;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	public boolean getSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
