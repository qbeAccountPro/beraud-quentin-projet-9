package com.medilaboSolutions.clientui.proxies;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@ComponentScan
@FeignClient(name = "microservice-diabetesassessment", url = "http://localhost:9004")
public interface DiabetesAssessmentProxy {

  @GetMapping(value = "/diabetesAssessment/patientid/{patientid}")
  String getDiabetesAssessment(@PathVariable("patientid") int patientid);  
}