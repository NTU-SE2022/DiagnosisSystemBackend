package com.group3.DiagnosisSystemBackend.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.group3.DiagnosisSystemBackend.repository.SymptomRepository;
import com.group3.DiagnosisSystemBackend.dto.SymptomResponse;

@RestController
@RequestMapping("/api")
public class SymptomController {
	@Autowired
	private SymptomRepository symptomRepository;
	
	@GetMapping("symptoms")
	public SymptomResponse getAllSymptoms() {
		SymptomResponse response = new SymptomResponse();
		com.group3.DiagnosisSystemBackend.dto.Symptom symptom = new com.group3.DiagnosisSystemBackend.dto.Symptom();
		symptom.setSymptom(symptomRepository.findAll());
		if(symptom.getSymptom() != null) {
			response.setStatus(HttpStatus.OK.value());
		} else {
			symptom.setSymptom(new ArrayList<com.group3.DiagnosisSystemBackend.dao.Symptom>());
			response.setStatus(HttpStatus.BAD_REQUEST.value());
		}
		response.setResponse(symptom);
		return response;
	}

}
