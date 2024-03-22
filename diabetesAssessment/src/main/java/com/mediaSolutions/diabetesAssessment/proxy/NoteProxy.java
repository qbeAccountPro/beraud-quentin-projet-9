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
 */
@ComponentScan
@FeignClient(name = "microservice-notemanager", url = "${microservice-notemanager:9003}")
public interface NoteProxy {
  /**
   * Some Javadoc :
   * 
   * Retrieves all notes associated with a specific patient.
   *
   * @param patientid The ID of the patient for whom to retrieve the notes.
   * @return A List of Note objects representing the notes associated with the
   *         specified patient.
   */
  @GetMapping(value = "/note/patientid/{patientid}")
  List<NoteBean> getAllNote(@PathVariable("patientid") int patientid);
}
