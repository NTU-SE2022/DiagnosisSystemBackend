package com.group3.DiagnosisSystemBackend.controller;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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

import com.group3.DiagnosisSystemBackend.blockchain.MedicalCertificateContract;


@RestController
@RequestMapping("/api")
public class DoctorController {
	
	@Autowired
	private Web3j web3j;
	private String privatekey = "0xca10c417e24a246700e36ef9b4f3e3d30fe9926f9b714de50a99498c3ab0d1de";
	
	@GetMapping("/deployAndLoadContract")
	public String testWallet() throws Exception {
		Credentials credentials = Credentials.create(privatekey);
		
		ContractGasProvider provider = new StaticGasProvider(BigInteger.valueOf(20000000000L), BigInteger.valueOf(6721975L));
		MedicalCertificateContract contract = MedicalCertificateContract.deploy(web3j, credentials, provider).send();
        String contractAddress = contract.getContractAddress();
        System.out.println(contractAddress);
        
		//contract = MedicalCertificate.load(contractAddress, web3j, credentials, provider);
        System.out.println(contract.owner().send());
        return contractAddress;
	}
        @RequestMapping("/test")
        public @ResponseBody String greeting() {
                return "Hello, World";
        }
}
