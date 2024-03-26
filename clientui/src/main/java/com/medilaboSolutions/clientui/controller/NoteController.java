package com.medilaboSolutions.clientui.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

import com.medilaboSolutions.clientui.bean.NoteBean;
import com.medilaboSolutions.clientui.bean.PatientBean;
import com.medilaboSolutions.clientui.proxies.GatewayProxy;

/**
 * Some Javadoc :
 * 
 * Controller class responsible for handling requests related to "Note"
 * operations.
 */
@Controller
public class NoteController {

  private final GatewayProxy gatewayProxy;

  public NoteController(GatewayProxy gatewayProxy) {
    this.gatewayProxy = gatewayProxy;
  }

  /**
   * Some Javadoc :
   * 
   * Method to loading all notes of one patient.
   * 
   * @param model     interface for holding attributes.
   * @param patientid it's the ID of the desired patient.
   * @return the view name for displaying the list of notes the specific patient.
   */
  @GetMapping("/patient/{patientid}/note")
  public String getNotePatient(Model model, @PathVariable("patientid") Integer patientid) {
    Optional<PatientBean> patient = gatewayProxy.getPatientById(patientid);
    List<NoteBean> notes = gatewayProxy.getAllNote(patientid);
    if (patient.isPresent()) {
      model.addAttribute("patient", patient.get());
      model.addAttribute("notes", notes);
      model.addAttribute("diabetesAssessment", gatewayProxy.getDiabetesAssessment(patient.get().getId()));
      return "note/list";
    } else {
      return "404";
    }
  }

  /**
   * Some Javadoc :
   * 
   * Method to adding a note at your specific patient.
   * 
   * @param noteBean  the note entity to be added.
   * @param model     interface for holding attributes.
   * @param patientid it's the ID of the desired patient.
   * @return the view name for displaying the list of notes the specific patient.
   */
  @GetMapping("patient/{patientid}/note/add")
  public String addNotePatient(NoteBean noteBean, Model model, @PathVariable("patientid") Integer patientid) {
    String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-dd-MM"));
    Optional<PatientBean> patient = gatewayProxy.getPatientById(patientid);
    if (patient.isPresent()) {
      model.addAttribute("patient", patient.get());
    } else {
      return "404";
    }
    model.addAttribute("bob", currentDate);
    return "note/add";
  }

  /**
   * Some Javadoc :
   * 
   * Method to validate the addition of one note.
   * 
   * @param note      the note entity to be added.
   * @param patientid it's the ID of the desired patient.
   * @param result    extends the Errors interface for error registration
   *                  capabilities or validation.
   * @param model     interface for holding attributes.
   * @return the responseEntity holding the view name for displaying the list of
   *         notes the specific patient.
   */
  @PostMapping("/patient/{patientid}/note/validate")
  public ResponseEntity<List<ObjectError>> validateAddNote(@Valid NoteBean note,
      @PathVariable("patientid") Integer patientid,
      BindingResult result, Model model) {
    if (result.hasErrors()) {
      model.addAttribute("result", result);
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result.getAllErrors());
    } else {
      gatewayProxy.saveNote(note);
      return ResponseEntity.status(HttpStatus.FOUND).header("Location", "/patient/" + patientid.toString() + "/note")
          .build();
    }
  }

  /**
   * Some Javadoc :
   * 
   * Method to delete a specific note.
   * 
   * @param model     interface for holding attributes.
   * @param patientid it's the ID of the desired patient.
   * @param noteId    it's the ID of the desired note.
   * @return the responseEntity holding the view name for displaying the list of
   *         notes the specific patient.
   */
  @GetMapping("/patient/{patientid}/note/{noteId}/delete")
  public ResponseEntity<Object> deleteNotePatient(Model model, @PathVariable("patientid") Integer patientid,
      @PathVariable("noteId") String noteId) {
    gatewayProxy.deletePatient(noteId);
    return ResponseEntity.status(HttpStatus.FOUND).header("Location", "/patient/" + patientid.toString() + "/note")
        .build();
  }

  /**
   * Some Javadoc :
   * 
   * Method to update a specific note.
   * 
   * @param model     interface for holding attributes.
   * @param patientid it's the ID of the desired patient.
   * @param noteId    it's the ID of the desired note.
   * @return the responseEntity holding the view name for displaying the list of
   *         notes the specific patient.
   */
  @GetMapping("/patient/{patientid}/note/{noteId}/update")
  public String updateNotePatient(Model model, @PathVariable("patientid") Integer patientid,
      @PathVariable("noteId") String noteId) {
    Optional<NoteBean> optionalNote = gatewayProxy.getNoteById(noteId);
    if (optionalNote.isPresent()) {
      model.addAttribute("patientid", patientid);
      NoteBean test = optionalNote.get();
      model.addAttribute("noteBean", test);
      return "note/update";
    } else {
      return "404";
    }
  }

  /**
   * Some Javadoc :
   * 
   * Method to validate the update of a specific note.
   * 
   * @param patientid it's the ID of the desired patient.
   * @param noteid    it's the ID of the desired note.
   * @param noteBean  the note entity to be updated.
   * @param result    extends the Errors interface for error registration
   *                  capabilities or validation.
   * @param model     interface for holding attributes.
   * @return the responseEntity holding the view name for displaying the list of
   *         notes the specific patient.
   */
  @PostMapping("/patient/{patientid}/note/{noteid}/validate")
  public ResponseEntity<Object> validateUpdateNote(@PathVariable("patientid") Integer patientid,
      @PathVariable("noteid") String noteid,
      @Valid NoteBean noteBean, BindingResult result, Model model) {
    if (result.hasErrors()) {
      model.addAttribute("result", result);
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result.getAllErrors());
    }
    gatewayProxy.saveNote(noteBean);
    return ResponseEntity.status(HttpStatus.FOUND).header("Location", "/patient/" + patientid.toString() + "/note")
        .build();
  }
}