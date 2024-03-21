package com.medilaboSolutions.clientui.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.medilaboSolutions.clientui.beans.PatientBean;
import com.medilaboSolutions.clientui.proxies.PatientProxy;

import javax.validation.Valid;

@Controller
public class PatientController {

  private final PatientProxy patientProxy;

  public PatientController(PatientProxy patientProxy) {
    this.patientProxy = patientProxy;
  }

  @GetMapping("/patient/list")
  public String getListPatient(Model model) {
    List<PatientBean> patients = patientProxy.getAllPatient();
    model.addAttribute("patients", patients);
    return "patient/list";
  }

  @GetMapping("/patient/add")
  public String addPatient(PatientBean patientBean) {
    return "patient/add";
  }

  @GetMapping("/patient/{patientid}/update")
  public String updatePatient(@PathVariable("patientid") Integer patientid, Model model) {
    Optional<PatientBean> optionalPatient = patientProxy.getPatientById(patientid);
    if (optionalPatient.isPresent()) {
      model.addAttribute("patientBean", optionalPatient.get());
      return "patient/update";
    }
    return "404";
  }

  @GetMapping("/patient/{patientid}/delete")
  public ResponseEntity<Object> deletePatient(@PathVariable("patientid") Integer patientid, Model model) {
    patientProxy.deletePatient(patientid);
    return ResponseEntity.status(HttpStatus.FOUND).header("Location", "/patient/list").build();
  }

  @PostMapping("/patient/validate")
  public ResponseEntity<Object> validateAddPatient(@Valid PatientBean patientBean, BindingResult result, Model model) {
    if (result.hasErrors()) {
      model.addAttribute("result", result);
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result.getAllErrors());
    }
    patientProxy.savePatient(patientBean);
    return ResponseEntity.status(HttpStatus.FOUND).header("Location", "/patient/list").build();
  }

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
