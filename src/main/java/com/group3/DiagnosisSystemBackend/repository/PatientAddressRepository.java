package com.group3.DiagnosisSystemBackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.group3.DiagnosisSystemBackend.dao.PatientAddress;

@Repository
public interface PatientAddressRepository extends JpaRepository<PatientAddress, String>{
}
