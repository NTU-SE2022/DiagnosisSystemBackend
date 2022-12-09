package com.group3.DiagnosisSystemBackend.controller;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

import com.group3.DiagnosisSystemBackend.blockchain.MedicalCertificateContract;
import com.group3.DiagnosisSystemBackend.dto.Response;



@RestController
@RequestMapping("/api")
public class DoctorController {
	
	@Autowired
	private Web3j web3j;
	private String privatekey = "0xca10c417e24a246700e36ef9b4f3e3d30fe9926f9b714de50a99498c3ab0d1de";
	private String contractAddress = "0xe02401b8b4d84189d0c013e9e20b2c87a33a5881";
	// contract Address: aws - 0xe02401b8b4d84189d0c013e9e20b2c87a33a5881, local - 0xae65681993ad0b95540b743b1ea7995dd174b173
	// blockchain aws -  http://18.179.197.23:7000, local: http://127.0.0.1:7545
	// private key: aws - 0xca10c417e24a246700e36ef9b4f3e3d30fe9926f9b714de50a99498c3ab0d1de, local - d5f66c6ae911366a9e21819bc9734031fc984456c404ac37bfcd2ddbc42200c5
	
	@GetMapping("/deployAndLoadContract")
	public String testWallet() throws Exception {
		Credentials credentials = Credentials.create(privatekey);
		
		ContractGasProvider provider = new StaticGasProvider(BigInteger.valueOf(20000000000L), BigInteger.valueOf(6721975L));
		MedicalCertificateContract contract = MedicalCertificateContract.deploy(web3j, credentials, provider).send();
        String contractAddress = contract.getContractAddress();
        System.out.println(contractAddress);
        System.out.println(contract.owner().send());
        return contractAddress;
	}
	
	@PostMapping("/createCertificate")
	public Response createCertificate(@RequestBody String json) throws Exception {
		// Info for test
        // String local_patientAddress = "0x81025fA5928C40C679d914E4C43F5d3E38018b11";
		// String aws_patientAddress = "0x2c4AF1E09e27b7d24fE121a912Cf12b7b7582b96";
        // String symptoms = "Burning,cancer";
		// String levels = "0,0";
		Response response = new Response();
		try {
			
			//check request json
			JSONObject requestJson = new JSONObject(json);
			String patientAddress = requestJson.getString("patientAddress");
			String symptoms = requestJson.getString("symptoms");
			String levels = requestJson.getString("levels");
			System.out.println(patientAddress);
        	System.out.println(symptoms);
        	System.out.println(levels);
			
			//add MedicalCertificateContract to patientAddress
			Credentials credentials = Credentials.create(privatekey);
			ContractGasProvider provider = new StaticGasProvider(BigInteger.valueOf(20000000000L), BigInteger.valueOf(6721975L));
			MedicalCertificateContract contract = MedicalCertificateContract.load(contractAddress, web3j, credentials, provider);
	        System.out.println(contract.owner().send());
	        contract.addCertificate(symptoms, levels, patientAddress).send();
	        
	        //check MedicalCertificateContract exist patientAddress
	        List CertificateIds = contract.listCertificatesIdOfAddress(patientAddress).send();
	        System.out.println(CertificateIds);
	        String successSymtoms = contract.getSymptoms((BigInteger) CertificateIds.get(CertificateIds.size() - 1)).send();
	        String successLevels = contract.getLevels((BigInteger) CertificateIds.get(CertificateIds.size() - 1)).send();
	        if (successSymtoms.equals(symptoms) && successLevels.equals(levels)) {
	        	response.setStatus(HttpStatus.OK.value());
	        }
	        else {
	        	System.out.println(successSymtoms);
	        	System.out.println(successLevels);
	        	response.setStatus(HttpStatus.BAD_REQUEST.value());
	        }
		}
        catch (Exception e) {
        	System.out.println(e.toString());
        	response.setStatus(HttpStatus.BAD_REQUEST.value());
        }
		
        return response;
	}
	
    @RequestMapping("/test")
    public @ResponseBody String greeting() {
        return "Hello, World";
    }
}
