package com.medilaboSolutions.clientui.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.medilaboSolutions.clientui.beans.PatientBean;
import com.medilaboSolutions.clientui.proxies.patientProxy;

import javax.validation.Valid;

@Controller
public class PatientController {

  private final patientProxy patientProxy;

  public PatientController(patientProxy patientProxy) {
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
  public String deletePatient(@PathVariable("patientid") Integer patientid) {
    patientProxy.deletePatient(patientid);
    return "redirect:/patient/list";
  }
  @PostMapping("/patient/validate")
  public String validateAddPatient(@Valid PatientBean patientBean, BindingResult result, Model model) {
    if (result.hasErrors()) {
      model.addAttribute("result", result);
      return "patient/add";
    }
    patientProxy.savePatient(patientBean);
    return "redirect:/patient/list";
  }

  @PostMapping("/patient/{patientid}/update")
  public String validatieUpdatePatient(@PathVariable("patientid") Integer patientid, @Valid PatientBean patientBean,
      BindingResult result, Model model) {
    if (result.hasErrors()) {
      model.addAttribute("result", result);
      return "patient/update";
    }
    patientProxy.savePatient(patientBean);
    return "redirect:/patient/list";
  }


}
