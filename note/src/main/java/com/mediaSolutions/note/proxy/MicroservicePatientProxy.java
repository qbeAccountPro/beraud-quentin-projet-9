package com.mediaSolutions.note.proxy;

import com.mediaSolutions.note.bean.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import java.util.Optional;

//  Check the case of the non-existent patient during the save Note.
@ComponentScan
@FeignClient(name = "microservice-patientmanager", url = "http://localhost:9001")
public interface MicroservicePatientProxy {
  @GetMapping(value = "/patient/{id}")
  Optional<PatientBean> getPatientById(@PathVariable("id") int id); }
