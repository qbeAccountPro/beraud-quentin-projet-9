package com.medilaboSolutions.diabeteDetect.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.medilaboSolutions.diabeteDetect.modeles.Patient;
import com.medilaboSolutions.diabeteDetect.services.PatientService;

import jakarta.validation.Valid;

/**
 * Controller class for managing Patient entities.
 * 
 * Handles CRUD operations for Patient objects, including listing, adding,
 * updating, and deleting Patient.
 */
@Controller
public class PatientController {
  @Autowired
  PatientService patientService;

  /**
   * Some javadoc :
   * 
   * This method displays the list of all patients.
   *
   * @param model the model to which attributes are added for rendering in the
   *              view.
   * @return String representing the view name for displaying the patient list.
   */
  @RequestMapping("/patient/list")
  public String home(Model model) {
    model.addAttribute("authentication", SecurityContextHolder.getContext().getAuthentication());
    model.addAttribute("patients", patientService.findAll());
    return "patient/list";
  }

  /**
   * Some javadoc :
   * 
   * This method displays the form for adding a new patient.
   *
   * @param patient the patient object to be added.
   * @return String representing the view name for displaying the add patient
   *         form.
   */
  @GetMapping("/patient/add")
  public String addPatientForm(Patient patient) {
    return "patient/add";
  }

  /**
   * Some javadoc :
   * 
   * This method validates patient data, saves it to the database, and redirects
   * to
   * the patient list.
   *
   * @param patient the Patient object to be validated and saved.
   * @param result  the binding result for validation errors.
   * @param model   the model to which attributes are added for rendering in the
   *                view.
   * @return String representing the view name for redirecting to the patient list
   *         or
   *         displaying validation errors.
   */
  @PostMapping("/patient/validate")
  public String validate(@Valid Patient patient, BindingResult result, Model model) {
    if (result.hasErrors()) {
      model.addAttribute("result", result);
      return "patient/add";
    }
    patientService.savePatient(patient);
    return "redirect:/patient/list";
  }

  /**
   * Some javadoc :
   * 
   * This method displays the form for updating an existing patient.
   *
   * @param id    the ID of the patient to be updated.
   * @param model the model to which attributes are added for rendering in the
   *              view.
   * @return String representing the view name for displaying the update patient
   *         form.
   */
  @GetMapping("/patient/update/{id}")
  public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
    model.addAttribute("authentication", SecurityContextHolder.getContext().getAuthentication());
    Optional<Patient> optionalPatient = patientService.findById(id);
    if (optionalPatient.isPresent()) {
      model.addAttribute("patient", optionalPatient.get());
      return "patient/update";
    } else {
      return "404";
    }
  }

  /**
   * Some javadoc :
   * 
   * This method updates patient data, saves it to the database, and redirects to
   * the
   * patient list.
   *
   * @param id      the ID of the patient to be updated.
   * @param patient the Patient object with updated data.
   * @param result  the binding result for validation errors.
   * @param model   the model to which attributes are added for rendering in the
   *                view.
   * @return String representing the view name for redirecting to the patient list
   *         or
   *         displaying validation errors.
   */
  @PostMapping("/patient/update/{id}")
  public String updatePatient(@PathVariable("id") Integer id, @Valid Patient patient,
      BindingResult result, Model model) {
    if (result.hasErrors()) {
      model.addAttribute("result", result);
      return "patient/update";
    }
    patientService.savePatient(patient);
    return "redirect:/patient/list";
  }

  /**
   * Some javadoc :
   * 
   * This method deletes a patient by its ID and redirects to the patient list.
   *
   * @param id the ID of the patient to be deleted.
   * @return String representing the view name for redirecting to the patient
   *         list.
   */
  @GetMapping("/patient/delete/{id}")
  public String deletePatient(@PathVariable("id") Integer id) {
    patientService.deleteById(id);
    return "redirect:/patient/list";
  }
}