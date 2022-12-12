package com.group3.DiagnosisSystemBackend.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "doctor")
public class Doctor {
	@Id
    @Column(name = "account", nullable = false)
	@Schema(example="doctor")
	private String account;
	
	@Column(name = "password", nullable = false)
	@Schema(example="password")
	private String password;
	
	public String getAccount() {
		return account;
	}
	
	public void setAccount(String account) {
		this.account = account;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public boolean isEmpty() {
		if (account.isEmpty() || password.isEmpty()) {
			return true;
		}
		return false;
	}
}
