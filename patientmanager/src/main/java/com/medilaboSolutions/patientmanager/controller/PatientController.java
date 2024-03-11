package com.medilaboSolutions.patientmanager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medilaboSolutions.patientmanager.model.Patient;
import com.medilaboSolutions.patientmanager.service.PatientService;

/**
 * Controller class for managing Patient entities.
 * 
 * Handles CRUD operations for Patient objects, including listing, adding,
 * updating, and deleting Patient.
 */
@RequestMapping("/patient")
@RestController
public class PatientController {
  @Autowired
  PatientService patientService;

  /**
   * Some javadoc :
   * 
   * This method retrieves a list of all patient.
   *
   * @return a list of all patient.
   */
  @GetMapping("")
  public List<Patient> getAllPatient() {
    return patientService.findAll();
  }

  /**
   * Some javadoc :
   * 
   * This method retrieves a patient by its id.
   *
   * @return patient if he is known from our database or null.
   */
  @GetMapping("/{id}")
  public Patient getPatientById(@PathVariable int id) {
    return patientService.findById(id);
  }

  /**
   * Some javadoc :
   * 
   * Adds a new patient to the database.
   *
   * @param patient the patient object to add.
   * @return the added patient object.
   */
  @PostMapping
  public Patient addPatient(@RequestBody Patient patient) {
    return patientService.savePatient(patient);
  }

  /**
   * Some javadoc :
   * 
   * Updates an existing patient in the database.
   *
   * @param id             the ID of the patient to update.
   * @param patientDetails the updated patient details.
   * @return the updated patient object if successful, otherwise null.
   */
  @PutMapping("/{id}")
  public Patient updatePatient(@PathVariable int id, @RequestBody Patient patientDetails) {
    return patientService.updatePatient(id, patientDetails);
  }

  /**
   * Some javadoc :
   * 
   * Deletes a patient from the database by ID.
   *
   * @param id the ID of the patient to delete.
   * @return ResponseEntity indicating the success of the deletion operation.
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<?> deletePatient(@PathVariable int id) {
    patientService.deleteById(id);
    return ResponseEntity.ok().build();
  }
}