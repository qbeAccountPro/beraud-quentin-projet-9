package com.medilaboSolutions.clientui.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.medilaboSolutions.clientui.bean.PatientBean;
import com.medilaboSolutions.clientui.proxies.PatientProxy;

/**
 * Some Javadoc :
 * 
 * Controller class responsible for handling requests related to "Patient"
 * operations.
 */
@Controller
public class PatientController {

  private final PatientProxy patientProxy;

  public PatientController(PatientProxy patientProxy) {
    this.patientProxy = patientProxy;
  }

  /**
   * Some Javadoc :
   * 
   * Methods to retrieve all patients.
   * 
   * @param model interface for holding attributes.
   * @return the view name for displaying the list of notes the specific patient.
   */
  @GetMapping("/patient/list")
  public String getListPatient(Model model) {
    List<PatientBean> patients = patientProxy.getAllPatient();
    model.addAttribute("patients", patients);
    return "patient/list";
  }

  /**
   * Some Javadoc :
   * 
   * Method for adding a patient.
   * 
   * @param patientBean the patient entity to be added.
   * @return the view name for displaying the list of notes the specific patient.
   */
  @GetMapping("/patient/add")
  public String addPatient(PatientBean patientBean) {
    return "patient/add";
  }

  /**
   * Some Javadoc :
   * 
   * Method for uppdating a patient.
   * 
   * @param patientid it's the ID of the desired patient.
   * @param model     interface for holding attributes.
   * @return the view name for displaying the list of notes the specific patient.
   */
  @GetMapping("/patient/{patientid}/update")
  public String updatePatient(@PathVariable("patientid") Integer patientid, Model model) {
    Optional<PatientBean> optionalPatient = patientProxy.getPatientById(patientid);
    if (optionalPatient.isPresent()) {
      model.addAttribute("patientBean", optionalPatient.get());
      return "patient/update";
    }
    return "404";
  }

  /**
   * Some Javadoc :
   * 
   * Method for deleting a patient.
   * 
   * @param patientid it's the ID of the desired patient.
   * @param model     interface for holding attributes.
   * @return the view name for displaying the list of notes the specific patient.
   */
  @GetMapping("/patient/{patientid}/delete")
  public ResponseEntity<Object> deletePatient(@PathVariable("patientid") Integer patientid, Model model) {
    patientProxy.deletePatient(patientid);
    return ResponseEntity.status(HttpStatus.FOUND).header("Location", "/patient/list").build();
  }

  /**
   * Some Javadoc :
   * 
   * Method for validating a patient data add.
   *
   * @param patientBean the patient entity to be added.
   * @param result      extends the Errors interface for error registration
   *                    capabilities or validation.
   * @param model       interface for holding attributes.
   * @return the view name for displaying the list of notes the specific patient.
   */
  @PostMapping("/patient/validate")
  public ResponseEntity<Object> validateAddPatient(@Valid PatientBean patientBean, BindingResult result, Model model) {
    if (result.hasErrors()) {
      model.addAttribute("result", result);
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result.getAllErrors());
    }
    patientProxy.savePatient(patientBean);
    return ResponseEntity.status(HttpStatus.FOUND).header("Location", "/patient/list").build();
  }

  /**
   * Some Javadoc :
   *
   * Method for validating a patient data update.
   * 
   * @param patientid   it's the ID of the desired patient.
   * @param patientBean the patient entity to be added.
   * @param result      extends the Errors interface for error registration
   *                    capabilities or validation.
   * @param model       interface for holding attributes.
   * @return the view name for displaying the list of notes the specific patient.
   */
  @PostMapping("/patient/{patientid}/update")
  public ResponseEntity<List<ObjectError>> validatieUpdatePatient(@PathVariable("patientid") Integer patientid,
      @Valid PatientBean patientBean,
      BindingResult result, Model model) {
    if (result.hasErrors()) {
      model.addAttribute("result", result);
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result.getAllErrors());
    }
    patientProxy.savePatient(patientBean);
    return ResponseEntity.status(HttpStatus.FOUND).header("Location", "/patient/list").build();
  }
}
