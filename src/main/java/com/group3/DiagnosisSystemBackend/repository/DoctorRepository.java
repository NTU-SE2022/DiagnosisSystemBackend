package com.group3.DiagnosisSystemBackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.group3.DiagnosisSystemBackend.dao.Doctor;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, String> {

}
