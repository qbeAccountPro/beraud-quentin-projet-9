package com.medilaboSolutions.patientmanager.unit.controller;

import org.mockito.InjectMocks;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.medilaboSolutions.patientmanager.controller.PatientController;
import com.medilaboSolutions.patientmanager.model.Patient;
import com.medilaboSolutions.patientmanager.service.PatientService;

@ExtendWith(MockitoExtension.class)
public class PatientControllerTest {
  @Mock
  PatientService patientServiceMock;

  @InjectMocks
  PatientController patientController = new PatientController();

  private static Patient patientTest = new Patient();

  @BeforeAll
  public static void setUpGeneral() {
    // Arrange :
    patientTest.setId(3);
    patientTest.setFirstname("George");
    patientTest.setLastname("Wayne");
    patientTest.setGender("M");
    patientTest.setDateofbirth("1990-02-20");
    patientTest.setAddress("Nowhere");
    patientTest.setPhone("0655554488");
  }

  @Test
  void testAddPatient() {
    // Act :
    patientController.addPatient(patientTest);

    // Assert :
    verify(patientServiceMock, times(1)).savePatient(patientTest);
  }

  @Test
  void testDeletePatient() {
    // Act :
    patientController.deletePatient(patientTest.getId());

    // Assert :
    verify(patientServiceMock, times(1)).deleteById(patientTest.getId());
  }

  @Test
  void testGetAllPatient() {
    // Act :
    patientController.getAllPatient();

    // Assert :
    verify(patientServiceMock, times(1)).findAll();
  }

  @Test
  void testGetPatientById() {
    // Act :
    patientController.getPatientById(patientTest.getId());

    // Assert :
    verify(patientServiceMock, times(1)).findById(patientTest.getId());
  }

  @Test
  void testUpdatePatient() {
    // Act :
    patientController.updatePatient(patientTest.getId(), patientTest);

    // Assert :
    verify(patientServiceMock, times(1)).updatePatient(patientTest.getId(), patientTest);
  }
}
