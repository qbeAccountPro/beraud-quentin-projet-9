package com.mediaSolutions.notemanager.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;

import com.mediaSolutions.notemanager.bean.*;

import java.util.Optional;

/**
 * Some Javadoc :
 * 
 * This class allows access to the REST client Patient manager.
 */
@ComponentScan
@FeignClient(name = "microservice-patientmanager", url = "${microservice-patientmanager}")
public interface PatientProxy {

  /**
   * Some Javadoc :
   * Retrieves a specific patient by their ID.
   *
   * @param id of the patient to retrieve.
   * @return An Optional containing the PatientBean object representing the
   *         retrieved patient, if found.
   */
  @GetMapping(value = "/patient/{id}")
  Optional<PatientBean> getPatientById(@PathVariable("id") int id);
}
