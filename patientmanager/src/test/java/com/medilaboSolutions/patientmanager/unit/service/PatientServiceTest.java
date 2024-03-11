package com.medilaboSolutions.patientmanager.unit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.medilaboSolutions.patientmanager.model.Patient;
import com.medilaboSolutions.patientmanager.repository.PatientRepository;
import com.medilaboSolutions.patientmanager.service.PatientService;

@ExtendWith(MockitoExtension.class)
public class PatientServiceTest {

  @Mock
  PatientRepository patientRepositoryMock;

  @InjectMocks
  PatientService patientService = new PatientService();

  private static Patient patientTest = new Patient();

  @BeforeAll
  public static void setUpGeneral() {
    // Arrange :
    patientTest.setId(3);
    patientTest.setFirstname("George");
    patientTest.setLastname("Wayne");
    patientTest.setGender("H");
    patientTest.setDateofbirth("1990-02-20");
    patientTest.setAddress("Nowhere");
    patientTest.setPhone("0655554488");
  }

  @Test
  void testDeleteById() {
    // Act :
    patientService.deleteById(patientTest.getId());

    // Assert :
    verify(patientRepositoryMock, times(1)).deleteById(patientTest.getId());
  }

  @Test
  void testFindAll() {
    // Act :
    patientService.findAll();

    // Assert :
    verify(patientRepositoryMock, times(1)).findAll();
  }

  @Test
  void testFindById() {
    // Act :
    patientService.findById(patientTest.getId());

    // Assert :
    verify(patientRepositoryMock, times(1)).findById(patientTest.getId());
  }

  @Test
  void testFindByIdWithExistingPatient() {
    // Arrange :
    when(patientRepositoryMock.findById(patientTest.getId())).thenReturn(Optional.of(patientTest));

    // Act :
    Patient result = patientService.findById(patientTest.getId());

    // Assert :
    verify(patientRepositoryMock, times(1)).findById(patientTest.getId());
    assertNotNull(result);
    assertEquals(patientTest, result);
  }

  @Test
  void testFindByIdWithoutExistingPatient() {
    // Arrange :
    when(patientRepositoryMock.findById(patientTest.getId())).thenReturn(Optional.empty());

    // Act :
    Patient result = patientService.findById(patientTest.getId());

    // Assert :
    verify(patientRepositoryMock, times(1)).findById(patientTest.getId());
    assertNull(result);
    assertNotEquals(patientTest, result);
  }

  @Test
  void testSavePatient() {
    // Arrange :
    when(patientRepositoryMock.save(patientTest)).thenReturn(patientTest);

    // Act :
    Patient result = patientService.savePatient(patientTest);

    // Assert :
    verify(patientRepositoryMock, times(1)).save(patientTest);
    assertNotNull(result);
    assertEquals(patientTest, result);
  }

  @Test
  void testUpdatePatientWithExistingPatient() {
    // Arrange :
    when(patientRepositoryMock.findById(patientTest.getId())).thenReturn(Optional.of(patientTest));
    when(patientRepositoryMock.save(patientTest)).thenReturn(patientTest);

    // Act :
    Patient result = patientService.updatePatient(patientTest.getId(), patientTest);

    // Assert :
    verify(patientRepositoryMock, times(1)).save(patientTest);
    assertNotNull(result);
    assertEquals(patientTest, result);
  }

  @Test
  void testUpdatePatientWithoutExistingPatient() {
    // Arrange :
    when(patientRepositoryMock.findById(patientTest.getId())).thenReturn(Optional.empty());

    // Act :
    Patient result = patientService.updatePatient(patientTest.getId(), patientTest);

    // Assert :
    verify(patientRepositoryMock, times(0)).save(patientTest);
    assertNull(result);
    assertNotEquals(patientTest, result);
  }
}
