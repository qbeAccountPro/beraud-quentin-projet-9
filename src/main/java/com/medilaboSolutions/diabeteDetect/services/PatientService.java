package com.medilaboSolutions.diabeteDetect.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medilaboSolutions.diabeteDetect.modeles.Patient;
import com.medilaboSolutions.diabeteDetect.repositories.PatientRepository;

import jakarta.validation.Valid;

/**
 * Some Javadoc :
 * 
 * Service class for Pateint entities.
 * Provides methods to perform CRUD operations on BidList objects.
 */
@Service
public class PatientService {

  @Autowired
  PatientRepository patientRepository;

  /**
   * Some javadoc :
   * 
   * Saves or updates a Patient entity.
   *
   * @param patient the Patient object to save or update.
   * @return The saved or updated Patient object.
   */
  public Patient savePatient(@Valid Patient patient) {
    return patientRepository.save(patient);
  }

  /**
   * Some javadoc :
   * 
   * Retrieves a list of all Patient entities.
   *
   * @return List of Patient objects.
   */
  public List<Patient> findAll() {
    return patientRepository.findAll();
  }

  /**
   * Some javadoc :
   * 
   * Deletes a Patient entity by its ID.
   *
   * @param id the ID of the Patient entity to delete.
   */
  public void deleteById(int id) {
    patientRepository.deleteById(id);
  }

  /**
   * Some javadoc :
   * 
   * Retrieves a Patient entity by its ID.
   *
   * @param id the ID of the Patient entity to retrieve.
   * @return Optional containing the Patient object, or empty if not found.
   */
  public Optional<Patient> findById(int id) {
    return patientRepository.findById(id);
  }
}