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
import com.group3.DiagnosisSystemBackend.dto.DoctorLoginResponse;
import com.group3.DiagnosisSystemBackend.dto.Patient;
import com.group3.DiagnosisSystemBackend.dto.PatientAddressResponse;
import com.group3.DiagnosisSystemBackend.dto.Response;
import com.group3.DiagnosisSystemBackend.repository.PatientAddressRepository;
import com.group3.DiagnosisSystemBackend.repository.ClinicRepository;
import com.group3.DiagnosisSystemBackend.repository.DoctorRepository;



@RestController
@RequestMapping("/api")
public class DoctorController {

	@Autowired
	private PatientAddressRepository patientAddressRepository;
	@Autowired
	private ClinicRepository clinicRepository;
	@Autowired
	private DoctorRepository doctorRepository;
	@Autowired
	private Web3j web3j;
//	private String hospitalPrivaeKey = "0xca10c417e24a246700e36ef9b4f3e3d30fe9926f9b714de50a99498c3ab0d1de";  // aws
//	private String medicalCertiticateContractAddress = "0xe02401b8b4d84189d0c013e9e20b2c87a33a5881";          // aws
	private String hospitalPrivaeKey = "d5f66c6ae911366a9e21819bc9734031fc984456c404ac37bfcd2ddbc42200c5";    // local
	private String medicalCertiticateContractAddress = "0xae65681993ad0b95540b743b1ea7995dd174b173";          // local
	
	@GetMapping("/deployAndLoadContract")
	public String testWallet() throws Exception {
		Credentials credentials = Credentials.create(hospitalPrivaeKey);
		
		ContractGasProvider provider = new StaticGasProvider(BigInteger.valueOf(20000000000L), BigInteger.valueOf(6721975L));
		MedicalCertificateContract contract = MedicalCertificateContract.deploy(web3j, credentials, provider).send();
        String contractAddress = contract.getContractAddress();
        System.out.println(contractAddress);
        System.out.println(contract.owner().send());
        return contractAddress;
	}
	
	@PostMapping("/createCertificate")
		// Info for test
        // String local_patientAddress = "0x81025fA5928C40C679d914E4C43F5d3E38018b11";
		// String aws_patientAddress = "0x2c4AF1E09e27b7d24fE121a912Cf12b7b7582b96";
        // String symptoms = "Burning,cancer";
		// String levels = "0,0";
	public ResponseEntity<Response> createCertificate(@RequestBody MedicalCertificate medicalCertificate) throws Exception {
			
		if (!medicalCertificate.checkNotNULL()) {
			Response response = new Response(HttpStatus.BAD_REQUEST.value(), false, "patientAddress / symptoms / levels has null value");
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
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
	        	Response response = new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), false, "Server Fail to create certificate to " + medicalCertificate.getPatientAddress());
				return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	        
	        //check to symtoms and levels in patient wallet match medicalCertificate from creator
	        int certificateId = medicalCertificateIds.size() - 1;
	        String patientWalletSymtoms = contract.getSymptoms((BigInteger) medicalCertificateIds.get(certificateId)).send();
	        String patientWalletLevels = contract.getLevels((BigInteger) medicalCertificateIds.get(certificateId)).send();
	        
	        if (patientWalletSymtoms.equals(medicalCertificate.getSymptoms()) && patientWalletLevels.equals(medicalCertificate.getLevels())) {
	        	Response response = new Response(HttpStatus.CREATED.value(), true, "Success to create and send certificate to " + medicalCertificate.getPatientAddress());
				return new ResponseEntity<>(response, HttpStatus.CREATED);
	        }
	        else {
	        	System.out.println(patientWalletSymtoms);
	        	System.out.println(patientWalletLevels);
	        	Response response = new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), false, "Server Fail to create certificate to " + medicalCertificate.getPatientAddress());
				return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	        }
		}
        catch (Exception e) {
        	System.out.println(e.toString());
        	Response response = new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), false, "Server Fail to create certificate to " + medicalCertificate.getPatientAddress());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
	}
	
    @RequestMapping("/test")
    public @ResponseBody String greeting() {
        return "Hello, World";
    }
    
	@GetMapping("patientAddresses")
	public PatientAddressResponse getAllPatientAddresses() {
		PatientAddressResponse response = new PatientAddressResponse();
		com.group3.DiagnosisSystemBackend.dto.Patient patientAddress = new com.group3.DiagnosisSystemBackend.dto.Patient();
		patientAddress.setPatient(patientAddressRepository.findAll());
		if(patientAddress.getPatient() != null) {
			response.setStatus(HttpStatus.OK.value());
		} else {
			patientAddress.setPatient(new ArrayList<com.group3.DiagnosisSystemBackend.dao.PatientAddress>());
			response.setStatus(HttpStatus.BAD_REQUEST.value());
		}
		response.setResponse(patientAddress);
		return response;
	}

	@GetMapping("patientAddresses/{address}")
	public PatientAddress newPatientAddresses(@PathVariable String address) {
		PatientAddress newPatientAddress = new PatientAddress();
		newPatientAddress.setAddress(address);
		
		return patientAddressRepository.save(newPatientAddress);
	}

	@GetMapping("clinic/{roomNo}")
	public ClinicResponse getClinic(@PathVariable int roomNo) {
		//get clinic from db
		Clinic clinic = clinicRepository.findById(roomNo);
		
		//set clinicResponse and return clinicResponse
		ClinicResponse response = new ClinicResponse();
		if(clinic != null) {
			response.setStatus(HttpStatus.OK.value());
		} else {
			response.setStatus(HttpStatus.BAD_REQUEST.value());
		}
		response.setResponse(clinic);
		return response;
	}
	
	@PutMapping("clinic/{roomNo}/{patient}")
	public Response replaceClinic(@PathVariable int roomNo, @PathVariable String patient) {
		
		// get clinic patient and update patient
		Clinic clinic = clinicRepository.findById(roomNo);
		if(patient.equals("none")) patient = "";
		clinic.setPatient(patient);
		clinicRepository.save(clinic);
		
		// set ClinicResponse and return ClinicResponse
		Response response = new Response(HttpStatus.OK.value(), true, "Success to update patient on room " + roomNo);
		//response.setStatus(HttpStatus.OK.value());
		return response;
	}
	
	@GetMapping("doctor")
	public List<Doctor> getAllDoctors() {
		return doctorRepository.findAll();
	}


	@PostMapping("doctor")
	public Doctor newDoctor(@RequestBody Doctor newDoctor) {
		String pw_hash = BCrypt.hashpw(newDoctor.getPassword(), BCrypt.gensalt());
		newDoctor.setPassword(pw_hash);
		
		return doctorRepository.save(newDoctor);
	}

	@PostMapping("login")
	public DoctorLoginResponse login(@RequestBody Doctor doctor) {
		System.out.println(doctor.getAccount() + "  " + doctor.getPassword());
		String dbPassword = doctorRepository.findPasswordByAccount(doctor.getAccount());
		DoctorLoginResponse response = new DoctorLoginResponse();
		if(dbPassword == null) return null;
		if (BCrypt.checkpw(doctor.getPassword(), dbPassword))
			response.setStatus(HttpStatus.OK.value());
		else
			response.setStatus(HttpStatus.BAD_REQUEST.value());
		
		return response;
	}
	
}
