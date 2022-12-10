package com.group3.DiagnosisSystemBackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;


@OpenAPIDefinition(info = @Info(title = "Diagnosis System API", version = "1.0.0"), 
				   servers = {@Server(url="https://diagnosis-back.host.chillmonkey.com.tw/")
				   			  , @Server(url="http://localhost:8081/")})
@SpringBootApplication
public class DiagnosisSystemBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(DiagnosisSystemBackendApplication.class, args);
	}

}
