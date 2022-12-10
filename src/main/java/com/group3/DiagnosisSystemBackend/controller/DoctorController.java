package com.group3.DiagnosisSystemBackend.controller;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.StaticGasProvider;
import org.bouncycastle.util.BigIntegers;
import org.springframework.beans.factory.annotation.Autowired;
import org.json.JSONObject;

import com.group3.DiagnosisSystemBackend.blockchain.MedicalCertificate;
import com.group3.DiagnosisSystemBackend.blockchain.MedicalCertificateContract;
import com.group3.DiagnosisSystemBackend.dao.PatientAddress;
import com.group3.DiagnosisSystemBackend.dao.Clinic;
import com.group3.DiagnosisSystemBackend.dao.Doctor;
import com.group3.DiagnosisSystemBackend.dto.ClinicResponse;
import com.group3.DiagnosisSystemBackend.dto.PatientAddressResponse;
import com.group3.DiagnosisSystemBackend.dto.PatientAddressesList;
import com.group3.DiagnosisSystemBackend.dto.Response;
import com.group3.DiagnosisSystemBackend.dto.SuccessResponse;
import com.group3.DiagnosisSystemBackend.repository.PatientAddressRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.ExampleObject;

import com.group3.DiagnosisSystemBackend.repository.ClinicRepository;
import com.group3.DiagnosisSystemBackend.repository.DoctorRepository;

@Tag(name = "Doctor API")
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class DoctorController {

	@Autowired
	private PatientAddressRepository patientAddressRepository;
	@Autowired
	private ClinicRepository clinicRepository;
	@Autowired
	private DoctorRepository doctorRepository;
	@Autowired
	private Web3j web3j;
	private String hospitalPrivaeKey = "0xca10c417e24a246700e36ef9b4f3e3d30fe9926f9b714de50a99498c3ab0d1de";  // aws
	private String medicalCertiticateContractAddress = "0xe02401b8b4d84189d0c013e9e20b2c87a33a5881";          // aws
//	private String hospitalPrivaeKey = "d5f66c6ae911366a9e21819bc9734031fc984456c404ac37bfcd2ddbc42200c5";    // local
//	private String medicalCertiticateContractAddress = "0xae65681993ad0b95540b743b1ea7995dd174b173";          // local
	
	@Operation(summary = "Deploy contract and get contract address", description = "upload medicate certificate contract to private blockchain and get contract address")
	@GetMapping("/deployAndLoadContract")
	public String testWallet() throws Exception 
	{
		Credentials credentials = Credentials.create(hospitalPrivaeKey);
		
		ContractGasProvider provider = new StaticGasProvider(BigInteger.valueOf(20000000000L), BigInteger.valueOf(6721975L));
		MedicalCertificateContract contract = MedicalCertificateContract.deploy(web3j, credentials, provider).send();
        String contractAddress = contract.getContractAddress();
        System.out.println(contractAddress);
        System.out.println(contract.owner().send());
        return contractAddress;
	}
	
	@Operation(summary = "Test API Server", 
			   description = "Test API Server")
	@GetMapping(path = "/test")
    public @ResponseBody String greeting() 
	{
        return "Hello, World";
    }
	

	@Operation(summary = "Create Doctor Account for test, web no need to use", 
			   description = "Create Doctor Account for test, fronend web no need to use")
	@ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Created"
										, content = @Content(schema = @Schema(implementation = Doctor.class), examples = {@ExampleObject(value = "{\"status\": 201, \"message\":\"Created\", \"success\": true}")})
									),@ApiResponse(responseCode = "400", description = "Bad Request"
									    , content = @Content(schema = @Schema(implementation = Doctor.class), examples = {@ExampleObject(value = "{\"status\": 400, \"message\":\"Bad Request\", \"success\": false}")})
									),@ApiResponse(responseCode = "500", description = "Internal Server Error"
										, content = @Content(schema = @Schema(implementation = Doctor.class), examples = {@ExampleObject(value = "{\"status\": 500, \"message\":\"Internal Server Error\", \"success\": false}")})
									)
				 })
	@PostMapping(path = "register", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SuccessResponse> register(@RequestBody(required=true) Doctor newDoctor) 
	{
		try {
			String pw_hash = BCrypt.hashpw(newDoctor.getPassword(), BCrypt.gensalt());
			newDoctor.setPassword(pw_hash);
			doctorRepository.save(newDoctor);
			return new ResponseEntity<>(new SuccessResponse(HttpStatus.CREATED.value(), "Created", true), HttpStatus.CREATED);
		}
		catch (Exception e) {
        	System.out.println(e.toString());
			return new ResponseEntity<>(new SuccessResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error", false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
		
	}
	
	
	@Operation(summary = "Doctor Login", 
			   description = "Doctor Login")
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK"
										, content = @Content(schema = @Schema(implementation = Doctor.class), examples = {@ExampleObject(value = "{\"status\": 200, \"message\":\"OK\", \"success\": true}")})
									),@ApiResponse(responseCode = "400", description = "Bad Request"
									    , content = @Content(schema = @Schema(implementation = Doctor.class), examples = {@ExampleObject(value = "{\"status\": 400, \"message\":\"Bad Request\", \"success\": false}")})
									),@ApiResponse(responseCode = "500", description = "Internal Server Error"
										, content = @Content(schema = @Schema(implementation = Doctor.class), examples = {@ExampleObject(value = "{\"status\": 500, \"message\":\"Internal Server Error\", \"success\": false}")})
									)
				 })
	@PostMapping(path = "login", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SuccessResponse> login(@RequestBody(required = true) Doctor doctor) 
	{
		try {
			String dbPassword = doctorRepository.findPasswordByAccount(doctor.getAccount());
			if(dbPassword == null) return null;
			if (BCrypt.checkpw(doctor.getPassword(), dbPassword))
				return new ResponseEntity<>(new SuccessResponse(HttpStatus.OK.value(), "OK", true), HttpStatus.OK);
			else
				return new ResponseEntity<>(new SuccessResponse(HttpStatus.BAD_REQUEST.value(), "Bad Request", false), HttpStatus.BAD_REQUEST);
		}
        catch (Exception e) {
        	System.out.println(e.toString());
			return new ResponseEntity<>(new SuccessResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error", false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
		
	}
	
	
	@Operation(summary = "Create medical certificate and send patient wallet, then patient address insert db.", 
			   description = "Create medical certificate and send patient wallet, then patient address insert db.")
	@ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Created"
										, content = @Content(schema = @Schema(implementation = Response.class), examples = {@ExampleObject(value = "{\"status\": 201, \"message\":\"Created\", \"success\": true}")})
						   ),@ApiResponse(responseCode = "400", description = "Bad Request"
									    , content = @Content(schema = @Schema(implementation = Response.class), examples = {@ExampleObject(value = "{\"status\": 400, \"message\":\"Bad Request\", \"success\": false}")})
						   ),@ApiResponse(responseCode = "500", description = "Internal Server Error"
						   				  , content = @Content(schema = @Schema(implementation = Response.class), examples = {@ExampleObject(value = "{\"status\": 500, \"message\":\"Internal Server Error\", \"success\": false}")})
						   )
				  })
	@PostMapping(path = "/createMedicalCertificate", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SuccessResponse> createMedicalCertificate(
		@RequestBody(required=true) MedicalCertificate medicalCertificate) throws Exception 
	{
			
		if (medicalCertificate.checkNULL()) {
			return new ResponseEntity<>(new SuccessResponse(HttpStatus.BAD_REQUEST.value(), "Bad Request", false), HttpStatus.BAD_REQUEST);
		}
        	
		try {
			//add MedicalCertificateContract to patientAddress
			Credentials credentials = Credentials.create(hospitalPrivaeKey);
			ContractGasProvider provider = new StaticGasProvider(BigInteger.valueOf(20000000000L), BigInteger.valueOf(6721975L));
			MedicalCertificateContract contract = MedicalCertificateContract.load(medicalCertiticateContractAddress, web3j, credentials, provider);
			List oldMedicalCertificateIds = contract.listCertificatesIdOfAddress(medicalCertificate.getPatientAddress()).send();
	        contract.addCertificate(medicalCertificate.getSymptoms(), medicalCertificate.getLevels(), medicalCertificate.getPatientAddress()).send();
	        
	        //check MedicalCertificate send patientAddress
	        List medicalCertificateIds = contract.listCertificatesIdOfAddress(medicalCertificate.getPatientAddress()).send();
	        if (medicalCertificateIds.size() <= oldMedicalCertificateIds.size()) {
				return new ResponseEntity<>(new SuccessResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error", false), HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	        
	        //check to symtoms and levels in patient wallet match medicalCertificate from creator
	        int certificateId = medicalCertificateIds.size() - 1;
	        String patientWalletSymtoms = contract.getSymptoms((BigInteger) medicalCertificateIds.get(certificateId)).send();
	        String patientWalletLevels = contract.getLevels((BigInteger) medicalCertificateIds.get(certificateId)).send();
	        if (patientWalletSymtoms.equals(medicalCertificate.getSymptoms()) && patientWalletLevels.equals(medicalCertificate.getLevels())) {
	        	// insert patient address to db
	    		patientAddressRepository.save(new PatientAddress(medicalCertificate.getPatientAddress()));
	    		// Created Success
				return new ResponseEntity<>(new SuccessResponse(HttpStatus.CREATED.value(), "Created", true), HttpStatus.CREATED);
	        }
	        else {
	        	System.out.println(patientWalletSymtoms);
	        	System.out.println(patientWalletLevels);
				return new ResponseEntity<>(new SuccessResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error", false), HttpStatus.INTERNAL_SERVER_ERROR);
	        }
		}
        catch (Exception e) {
        	System.out.println(e.toString());
			return new ResponseEntity<>(new SuccessResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error", false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
	}
    
	
	@Operation(summary = "Get All Patient Addresses", 
			   description = "Get All Patient Addresses")
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK"
										, content = @Content(schema = @Schema(implementation = PatientAddressResponse.class), examples = {@ExampleObject(value = "{\"status\": 200, \"message\":\"OK\", \"data\": {\"patientAddressesList\": [{\"address\": \"0x2994433854D3805468467870E7231ECf46C6f9B8\"},{\"address\": \"0x2c4AF1E09e27b7d24fE121a912Cf12b7b7582b96\"}]}}")})
						   ),@ApiResponse(responseCode = "500", description = "Internal Server Error"
						   				, content = @Content(schema = @Schema(implementation = PatientAddressResponse.class), examples = {@ExampleObject(value = "{\"status\": 500, \"message\":\"Internal Server Error\", \"data\": null}")})
						   )
				  })
	@GetMapping(path = "patientAddresses",  produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PatientAddressResponse> getAllPatientAddresses() 
	{
		try {
			PatientAddressesList patientAddressesList = new PatientAddressesList(patientAddressRepository.findAll());
			return new ResponseEntity<>(new PatientAddressResponse(HttpStatus.OK.value(), "OK", patientAddressesList), HttpStatus.OK);
		}
		catch (Exception e) {
        	System.out.println(e.toString());
			return new ResponseEntity<>(new PatientAddressResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),  "Internal Server Error", null), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
	@Operation(summary = "Get information of room No in clinic", 
	   		   description = "Get information of room No in clinic")
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK"
					 					, content = @Content(schema = @Schema(implementation = ClinicResponse.class), examples = {@ExampleObject(value = "{\"status\": 200, \"message\":\"OK\", \"data\":{\"patientAddressesList\": [{\"address\": \"0x2994433854D3805468467870E7231ECf46C6f9B8\"},{\"address\": \"0x2c4AF1E09e27b7d24fE121a912Cf12b7b7582b96\"}]}}")})
						   ),@ApiResponse(responseCode = "400", description = "Bad Request"
			   				  			, content = @Content(schema = @Schema(implementation = ClinicResponse.class), examples = {@ExampleObject(value = "{\"status\": 400, \"message\":\"Bad Request\", \"data\": null}")})
						   ),@ApiResponse(responseCode = "500", description = "Internal Server Error"
						   				, content = @Content(schema = @Schema(implementation = ClinicResponse.class), examples = {@ExampleObject(value = "{\"status\": 500, \"message\":\"Internal Server Error\", \"data\": null}")})
						   )
				  })
	@GetMapping(path = "clinic/{roomNo}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ClinicResponse> getClinic(
		@Parameter(description = "the roomNo of the clinic", required = true) @PathVariable("roomNo") int roomNo) 
	{
		try {
			//get clinic from db
			Clinic clinic = clinicRepository.findById(roomNo);
			
			//set clinicResponse and return clinicResponse
			if(clinic != null) {
				return new ResponseEntity<>(new ClinicResponse(HttpStatus.OK.value(), "OK", clinic), HttpStatus.OK);
				//response.setStatus(HttpStatus.OK.value());
			} else {
				return new ResponseEntity<>(new ClinicResponse(HttpStatus.BAD_REQUEST.value(), "Bad Request", clinic), HttpStatus.BAD_REQUEST);
			}
		}
		catch (Exception e) {
        	System.out.println(e.toString());
        	return new ResponseEntity<>(new ClinicResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error", null), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@Operation(summary = "Update patient address of room No in clinic", 
	   		   description = "Update patient address of room No in clinic")
	@ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Created"
										, content = @Content(schema = @Schema(implementation = Response.class), examples = {@ExampleObject(value = "{\"status\": 201, \"message\":\"Created\", \"success\": true}")})
						   ),@ApiResponse(responseCode = "400", description = "Bad Request"
						   				, content = @Content(schema = @Schema(implementation = Response.class), examples = {@ExampleObject(value = "{\"status\": 400, \"message\":\"Bad Request\", \"success\": false}")})
						   ),@ApiResponse(responseCode = "500", description = "Internal Server Error"
						   				, content = @Content(schema = @Schema(implementation = Response.class), examples = {@ExampleObject(value = "{\"status\": 500, \"message\":\"Internal Server Error\", \"success\": false}")})
						   )
				 })
	@PutMapping(path = "clinic/{roomNo}/{patientAddress}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SuccessResponse> updateClinicPatient(
			@Parameter(description = "the roomNo of the clinic", required = true) @PathVariable("roomNo") int roomNo, 
			@Parameter(description = "update the patient address, patientAddress set \"none\" when patient exit room", required = true) @PathVariable("patientAddress") String patientAddress) 
	{
		try {
			// get clinic patient and update patient
			Clinic clinic = clinicRepository.findById(roomNo);
			if(patientAddress.equals("none")) patientAddress = "";
			clinic.setPatient(patientAddress);
			clinicRepository.save(clinic);
			// set SuccessResponse and return SuccessResponse
			return new ResponseEntity<>(new SuccessResponse(HttpStatus.CREATED.value(), "Created", true), HttpStatus.CREATED);
		}
		catch (Exception e) {
        	System.out.println(e.toString());
        	return new ResponseEntity<>(new SuccessResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error", false), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
