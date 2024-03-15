package com.mediaSolutions.diabetesAssessment.proxy;

import com.mediaSolutions.diabetesAssessment.bean.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * Some Javadoc :
 * 
 * This class allows access to the REST client microservice noteManager.
 * 
 */
@ComponentScan
@FeignClient(name = "microservice-notemanager", url = "http://localhost:9003")
public interface NoteProxy {
  @GetMapping(value = "/note/patientid/{patientid}")
  List<NoteBean> getAllNote(@PathVariable("patientid") int patientid);
}
