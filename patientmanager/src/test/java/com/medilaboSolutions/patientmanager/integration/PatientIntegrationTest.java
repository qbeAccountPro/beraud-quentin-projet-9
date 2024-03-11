package com.medilaboSolutions.patientmanager.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.medilaboSolutions.patientmanager.controller.PatientController;
import com.medilaboSolutions.patientmanager.model.Patient;
import com.medilaboSolutions.patientmanager.service.PatientService;

import org.springframework.http.MediaType;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.type.TypeReference;
import java.util.List;

import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
public class PatientIntegrationTest {
  @Autowired
  PatientService patientService;

  @Autowired
  PatientController patientController = new PatientController();

  private static Patient patientTest = new Patient();

  private MockMvc mvc;

  @BeforeEach
  void setup() {
    this.mvc = MockMvcBuilders
        .standaloneSetup(patientController)
        .setControllerAdvice()
        .build();
  }

  @BeforeAll
  public static void setUpGeneral() {
    // Arrange :
    patientTest.setFirstname("George");
    patientTest.setLastname("Wayne");
    patientTest.setGender("H");
    patientTest.setDateofbirth("1990-02-20");
    patientTest.setAddress("Nowhere");
    patientTest.setPhone("0655554488");
  }

  @Test
  int testAddPatient() {
    // Serialize
    ObjectMapper objectMapper = new ObjectMapper();

    // AddPatient :
    MvcResult result;
    Patient patient;
    try {
      String patientJson = objectMapper.writeValueAsString(patientTest);

      result = mvc.perform(MockMvcRequestBuilders.post("/patient")
          .content(patientJson)
          .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk()).andReturn();

      // Deserialize
      patient = objectMapper.readValue(result.getResponse().getContentAsString(), Patient.class);
    } catch (Exception e) {
      e.printStackTrace();
      patient = null;
    }

    int id = patient.getId();

    Patient savedPatient = patientService.findById(id);

    assertTrue(id != 0);
    assertEquals(patientTest.getFirstname(), savedPatient.getFirstname());
    assertEquals(patientTest.getLastname(), savedPatient.getLastname());
    assertEquals(patientTest.getGender(), savedPatient.getGender());
    assertEquals(patientTest.getDateofbirth(), savedPatient.getDateofbirth());
    assertEquals(patientTest.getAddress(), savedPatient.getAddress());
    assertEquals(patientTest.getPhone(), savedPatient.getPhone());

    return id;
  }

  @Test
  void testDeletePatient() throws Exception {
    // Call AddPatient to save one :
    int id = testAddPatient();

    // Delete the same patient previously added :
    mvc.perform(MockMvcRequestBuilders.delete("/patient/{id}", id)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andReturn();

    // Assert deletion :
    assertNull(patientService.findById(id));
  }

  @Test
  void testGetAllPatient() throws Exception {
    testAddPatient();
    testAddPatient();

    MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/patient")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andReturn();

    // Deserialize
    // Récupérer la liste de patients à partir de la réponse JSON
    String jsonResponse = result.getResponse().getContentAsString();
    ObjectMapper objectMapper = new ObjectMapper();
    List<Patient> patients = objectMapper.readValue(jsonResponse, new TypeReference<List<Patient>>() {
    });

    assertTrue(patients.size() >= 2);
  }

  @Test
  void testGetPatientById() throws Exception {
    // Call AddPatient to save one :
    int id = testAddPatient();

    // Delete the same patient previously added :
    MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/patient/{id}", id)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andReturn();

    // Deserialize
    ObjectMapper objectMapper = new ObjectMapper();
    Patient patient = objectMapper.readValue(result.getResponse().getContentAsString(), Patient.class);

    Patient savedPatient = patientService.findById(id);

    // Assert deletion :
    assertEquals(savedPatient, patient);
  }

  @Test
  void testUpdatePatient() throws Exception {
    // Call AddPatient to save one :
    int id = testAddPatient();

    Patient newPatient = patientTest;
    newPatient.setAddress("New Address");
    newPatient.setPhone("3633554455");
    newPatient.setId(id);

    // Serialize
    ObjectMapper objectMapper = new ObjectMapper();
    String newPatientJson = objectMapper.writeValueAsString(newPatient);

    // Update the same patient previously added :
    mvc.perform(MockMvcRequestBuilders.put("/patient/{id}", id)
        .content(newPatientJson)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andReturn();

    Patient savedPatient = patientService.findById(id);

    // Assert deletion :
    assertEquals(savedPatient, newPatient);
  }
}
