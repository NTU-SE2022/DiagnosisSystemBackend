package com.group3.DiagnosisSystemBackend.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.web3j.crypto.WalletUtils;

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

	public void setAddress(String address) throws Exception {
		if (WalletUtils.isValidAddress(this.address)) {
			this.address = address;
		}
		else {
			throw new Exception("patient address don't match wallet address format.");
		}
	}
	
}
