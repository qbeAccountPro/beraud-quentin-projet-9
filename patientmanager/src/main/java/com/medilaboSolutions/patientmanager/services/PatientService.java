package com.medilaboSolutions.patientmanager.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medilaboSolutions.patientmanager.modeles.Patient;
import com.medilaboSolutions.patientmanager.repositories.PatientRepository;

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
   * @return Optional containing the Patient object, or null if not found.
   */
  public Patient findById(int id) {
    Optional<Patient> optionalPatient = patientRepository.findById(id);
    if (optionalPatient.isPresent()) {
      return optionalPatient.get();
    } else {
      return null;
    }
  }

  /**
   * Some javadoc :
   * 
   * Updates a Patient entity identified by its ID.
   *
   * @param id      the ID of the Patient entity to update.
   * @param patient the updated Patient object.
   * @return the updated Patient object if it exists, otherwise null.
   */
  public Patient updatePatient(int id, Patient patient) {
    if (patientRepository.findById(id).isPresent()) {
      patient.setId(id);
      return patientRepository.save(patient);
    } else {
      return null;
    }
  }
}