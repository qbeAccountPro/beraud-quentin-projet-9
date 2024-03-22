package com.medilaboSolutions.clientui.proxies;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.medilaboSolutions.clientui.bean.*;

import java.util.List;
import java.util.Optional;

/**
 * Some Javadoc :
 * 
 * Interface defining the Feign client for interacting with the PatientManager
 * microservice.
 */
@ComponentScan
@FeignClient(name = "microservice-patientmanager", url = "${microservice-patientmanager}")
public interface PatientProxy {

  /**
   * Some Javadoc :
   * 
   * Retrieves all patients.
   *
   * @return A List of PatientBean objects representing all patients.
   */
  @GetMapping(value = "/patient")
  List<PatientBean> getAllPatient();

  /**
   * Some Javadoc :
   * 
   * Retrieves a specific patient by its ID.
   *
   * @param id The ID of the patient to retrieve.
   * @return An Optional containing the PatientBean object representing the
   *         retrieved patient, if found.
   */
  @GetMapping(value = "/patient/{id}")
  Optional<PatientBean> getPatientById(@PathVariable("id") int id);

  /**
   * Some Javadoc :
   * 
   * Saves a new patient.
   *
   * @param patient The PatientBean object representing the patient to save.
   * @return The PatientBean object representing the saved patient.
   */
  @PostMapping(value = "/patient")
  @ResponseStatus(HttpStatus.CREATED)
  PatientBean savePatient(@RequestBody PatientBean patient);

  /**
   * Some Javadoc :
   * 
   * Deletes a patient by its ID.
   *
   * @param id The ID of the patient to delete.
   */
  @DeleteMapping(value = "/patient/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  void deletePatient(@PathVariable("id") int id);
}