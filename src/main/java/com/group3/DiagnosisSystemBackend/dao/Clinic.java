package com.group3.DiagnosisSystemBackend.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.web3j.crypto.WalletUtils;

@Entity
@Table(name = "clinic")
public class Clinic {
	@Id
	@Column(name = "roomNo", nullable = false)
	private int roomNo;

	@Column(name = "patient")
	private String patient;

	public int getRoomNo() {
		return roomNo;
	}

	public void setRoomNo(int roomNo) {
		this.roomNo = roomNo;
	}

	public String getPatient() {
		return patient;
	}

	public void setPatient(String patient) throws Exception {
		if (patient.equals("none")) {
			this.patient = "";
		}
		else if (WalletUtils.isValidAddress(patient)) {
			this.patient = patient;
		}
		else {
			throw new Exception("patient address don't match wallet address format.");
		}
	}

}
