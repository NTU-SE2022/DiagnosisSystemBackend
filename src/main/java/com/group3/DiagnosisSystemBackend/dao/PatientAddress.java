package com.group3.DiagnosisSystemBackend.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "patientAddress")
public class PatientAddress {
	@Id
    @Column(name = "address", nullable = false)
	private String address;
	
	//database default constructor
	public PatientAddress(){
		   super();
	}
	
	public PatientAddress(String address) {
		this.address = address;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}
