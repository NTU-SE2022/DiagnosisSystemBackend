package com.group3.DiagnosisSystemBackend.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.group3.DiagnosisSystemBackend.repository.SymptomRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.group3.DiagnosisSystemBackend.dto.PatientAddressResponse;
import com.group3.DiagnosisSystemBackend.dto.SuccessResponse;
import com.group3.DiagnosisSystemBackend.dto.SymptomResponse;
import com.group3.DiagnosisSystemBackend.dto.SymptomsList;

@Tag(name = "Symptom API")
@RestController
@RequestMapping("/api")
public class SymptomController {
	@Autowired
	private SymptomRepository symptomRepository;
	
	@Operation(summary = "Get All Symptoms", 
			   description = "Get All Symptoms")
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK"
										, content = @Content(schema = @Schema(implementation = SymptomResponse.class), examples = {@ExampleObject(value = "{\"status\": 200, \"message\":\"OK\", \"data\": {\"symptomsList\": [{\"name\": \"Burning\"},{\"name\": \"cancer\"}]}}")})
						   ),@ApiResponse(responseCode = "500", description = "Internal Server Error"
						   				, content = @Content(schema = @Schema(implementation = SymptomResponse.class), examples = {@ExampleObject(value = "{\"status\": 500, \"message\":\"Internal Server Error\", \"data\": null}")})
						   )
				  })
	@GetMapping(path = "symptoms",  produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SymptomResponse> getAllSymptoms() {
		try {
			SymptomsList symptomsList = new SymptomsList(symptomRepository.findAll());
			if(symptomsList.getSymptomsList() != null) {
				return new ResponseEntity<>(new SymptomResponse(HttpStatus.OK.value(), "OK", symptomsList), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(new SymptomResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error", null), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		catch (Exception e) {
        	System.out.println(e.toString());
			return new ResponseEntity<>(new SymptomResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
		
	}

}
