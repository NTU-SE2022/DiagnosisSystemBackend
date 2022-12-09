package com.group3.DiagnosisSystemBackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.group3.DiagnosisSystemBackend.dao.Doctor;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, String> {
	@Query(value = "select password from doctor where if(?1 !='',account=?1,1=1) ",nativeQuery = true)
	public String findPasswordByAccount(String account);
}
