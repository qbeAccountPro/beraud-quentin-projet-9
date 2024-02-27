package com.medilaboSolutions.clientui.proxies;

import com.medilaboSolutions.clientui.beans.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.Optional;       

@ComponentScan
@FeignClient(name = "microservice-patientmanager", url = "http://localhost:9001")
public interface MicroservicePatientProxy {
  @GetMapping(value = "/patient")
  List<PatientBean> getAllPatients();

  @GetMapping(value = "/patient/{id}")
  Optional<PatientBean> getPatientById(@PathVariable("id") int id);

  @PostMapping(value = "/patient")
  @ResponseStatus(HttpStatus.CREATED)
  PatientBean savePatient(@RequestBody PatientBean patient);

  @DeleteMapping(value = "/patient/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  void deletePatient(@PathVariable("id") int id);
}