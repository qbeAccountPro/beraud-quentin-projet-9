package com.mediaSolutions.diabetesAssessment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mediaSolutions.diabetesAssessment.bean.NoteBean;
import com.mediaSolutions.diabetesAssessment.bean.PatientBean;
import com.mediaSolutions.diabetesAssessment.proxy.NoteProxy;
import com.mediaSolutions.diabetesAssessment.proxy.PatientProxy;
import com.mediaSolutions.diabetesAssessment.service.DiabetesAssessmentService;

@RestController
@RequestMapping("/diabetesAssessment")
public class DiabetesAssessmentController {

  @Autowired
  DiabetesAssessmentService diabetesAssessmentService;

  private final PatientProxy patientProxy;
  private final NoteProxy noteProxy;

  DiabetesAssessmentController(PatientProxy patientProxy, NoteProxy noteProxy) {
    this.patientProxy = patientProxy;
    this.noteProxy = noteProxy;
  }

  @GetMapping("/patientid/{patientid}")
  public String getDiabetesAssessment(@PathVariable String patientid) {
    int patientID = Integer.parseInt(patientid);
    PatientBean patient = patientProxy.getPatientById(patientID).get();
    List<NoteBean> notes = noteProxy.getAllNote(patientID);

    return diabetesAssessmentService.getDiabetesAssessment(patient, notes);
  }
}