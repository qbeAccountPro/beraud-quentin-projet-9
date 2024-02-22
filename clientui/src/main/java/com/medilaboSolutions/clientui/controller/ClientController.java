package com.medilaboSolutions.clientui.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.medilaboSolutions.clientui.beans.PatientBean;
import com.medilaboSolutions.clientui.proxies.MicroservicePatientProxy;

import javax.validation.Valid;

@Controller
public class ClientController {

  private final MicroservicePatientProxy patientProxy;

  public ClientController(MicroservicePatientProxy patientProxy) {
    this.patientProxy = patientProxy;
  }

  // TODO : ajouter l'authentification au model pour récup ton pti nom
  @RequestMapping("/patient/list")
  public String listPatient(Model model) {
    List<PatientBean> patients = patientProxy.getAllPatients();
    model.addAttribute("patients", patients);
    return "patient/list";
  }

  @GetMapping("/patient/add")
  public String addPatient(PatientBean patientBean) {
    return "patient/add";
  }

  @GetMapping("/patient/update")
  public String updatePatient(PatientBean patientBean) {
    return "patient/update";
  }

  @PostMapping("/patient/validate")
  public String validatePatient(@Valid PatientBean patientBean, BindingResult result, Model model) {
    if (result.hasErrors()) {
      model.addAttribute("result", result);
      return "patient/add";
    }
    patientProxy.savePatient(patientBean);
    return "redirect:/patient/list";
  }

  @GetMapping("/patient/update/{id}")
  public String showUpdateFormPatient(@PathVariable("id") Integer id, Model model) {
    Optional<PatientBean> optionalPatient = patientProxy.getPatientById(id);
    if (optionalPatient.isPresent()) {
      model.addAttribute("patientBean", optionalPatient.get());
      return "patient/update";
    }
    return "404";
  }

  @PostMapping("/patient/update/{id}")
  public String updatePatient(@PathVariable("id") Integer id, @Valid PatientBean patientBean,
      BindingResult result, Model model) {
    if (result.hasErrors()) {
      model.addAttribute("result", result);
      return "patient/update";
    }
    patientProxy.savePatient(patientBean);
    return "redirect:/patient/list";
  }

  @GetMapping("/patient/delete/{id}")
  public String deletePatient(@PathVariable("id") Integer id) {
    patientProxy.deletePatient(id);
    return "redirect:/patient/list";
  }
}
