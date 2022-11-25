package com.group3.DiagnosisSystemBackend.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.group3.DiagnosisSystemBackend.dao.Symptom;

@Repository
public interface SymptomRepository extends JpaRepository<Symptom, String> {

}
