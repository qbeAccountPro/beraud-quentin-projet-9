package com.medilaboSolutions.clientui.proxies;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Some Javadoc :
 * 
 * Interface defining the Feign client for accessing the Diabetes Assessment
 * microservice.
 */
@ComponentScan
@FeignClient(name = "microservice-diabetesassessment", url = "${microservice-diabetesassessment}")
public interface DiabetesAssessmentProxy {

  /**
   * Some Javadoc :
   * 
   * Retrieves the diabetes assessment for a specific patient.
   *
   * @param patientid The ID of the patient for whom to retrieve the diabetes
   *                  assessment.
   * @return A String representing the diabetes assessment for the specified
   *         patient.
   */
  @GetMapping(value = "/diabetesAssessment/patientid/{patientid}")
  String getDiabetesAssessment(@PathVariable("patientid") int patientid);
}