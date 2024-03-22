package com.mediaSolutions.diabetesAssessment.proxy;

import com.mediaSolutions.diabetesAssessment.bean.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import java.util.Optional;

/**
 * Some Javadoc :
 * 
 * Interface defining the Feign client for interacting with the PatientManager
 * microservice.
 */
@ComponentScan
@FeignClient(name = "microservice-patientmanager", url = "${microservice-patientmanager:9001}")
public interface PatientProxy {

  /**
   * Retrieves a specific patient by their ID.
   *
   * @param id The ID of the patient to retrieve.
   * @return An Optional containing the PatientBean object representing the
   *         retrieved patient, if found.
   */
  @GetMapping(value = "/patient/{id}")
  Optional<PatientBean> getPatientById(@PathVariable("id") int id);
}
