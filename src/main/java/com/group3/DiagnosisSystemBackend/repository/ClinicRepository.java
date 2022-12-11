package com.group3.DiagnosisSystemBackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.group3.DiagnosisSystemBackend.dao.Clinic;

@Repository
public interface ClinicRepository extends JpaRepository<Clinic, Integer>{
	public Clinic findById(int roomNo);
	@Query(value = "select * from clinic where if(?1 !='',patient=?1,1=1)",nativeQuery = true)
	public Clinic findByPatient(String patient);
}
