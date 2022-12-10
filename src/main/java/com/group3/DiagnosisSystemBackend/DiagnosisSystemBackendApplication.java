package com.group3.DiagnosisSystemBackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;


@OpenAPIDefinition(info = @Info(title = "Diagnosis System API", version = "1.0.0"))
@SpringBootApplication
public class DiagnosisSystemBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(DiagnosisSystemBackendApplication.class, args);
	}

}
