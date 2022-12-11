package com.group3.DiagnosisSystemBackend;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

//import org.aspectj.lang.annotation.Before;
import org.junit.Before;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
//import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.group3.DiagnosisSystemBackend.blockchain.MedicalCertificate;
import com.group3.DiagnosisSystemBackend.controller.DoctorController;
import com.group3.DiagnosisSystemBackend.dao.Doctor;
import com.group3.DiagnosisSystemBackend.dao.PatientAddress;
import com.group3.DiagnosisSystemBackend.repository.DoctorRepository;
import com.group3.DiagnosisSystemBackend.repository.PatientAddressRepository;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DiagnosisSystemBackendApplication.class)
@WebAppConfiguration
public class DiagnosisSystemBackendApplicationTests {

	@Autowired
	private DoctorRepository doctorRepository;
	@Autowired
	private PatientAddressRepository patientAddressRepository;
	@Autowired
    private WebApplicationContext webApplicationContext;
	
	@Autowired
	ObjectMapper objectMapper;
	MockMvc mvc;
	Doctor doctor;
	PatientAddress patientAddress;
	MedicalCertificate medicalCertificate;
	
	//DoctorLoginTest
	@Before
	public void setup(){
		doctor = new Doctor();
		doctor.setAccount("test");
		doctor.setPassword("123");
		mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	
	}
	@Test
	public void contextLoads() throws Exception {
		String uri = "/api/login";
		try {
			MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri).content(objectMapper.writeValueAsString(doctor)).accept(MediaType.APPLICATION_JSON)).andReturn();
			int status = result.getResponse().getStatus();
			System.out.println(status);
			Assert.assertEquals("錯誤",200,status);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	//getPatientAddressTest
	@Before
	public void setup2() throws Exception{
		patientAddress = new PatientAddress();
		mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
	
	@Test
	public void contextLoads2() throws Exception {
		String uri = "/api/patientAddresses";
		try {
			//MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON)).andReturn();
			MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri).content(objectMapper.writeValueAsString(patientAddress)).accept(MediaType.APPLICATION_JSON)).andReturn();
			int status = result.getResponse().getStatus();
			System.out.println(status);
			Assert.assertEquals("錯誤",200,status);
		}catch(Exception ex){
			ex.printStackTrace();
		}}
	
	//getMedicalCertificateTest
		@Before
		public void setup3() throws Exception{
			medicalCertificate = new MedicalCertificate();
			mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		}
		
		@Test
		public void contextLoads3() throws Exception {
			String uri = "/api/medicalCertificates";
			try {
				//MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON)).andReturn();
				MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri).content(objectMapper.writeValueAsString(medicalCertificate)).accept(MediaType.APPLICATION_JSON)).andReturn();
				int status = result.getResponse().getStatus();
				System.out.println(status);
				Assert.assertEquals("錯誤",200,status);
			}catch(Exception ex){
				ex.printStackTrace();
			}
}}
