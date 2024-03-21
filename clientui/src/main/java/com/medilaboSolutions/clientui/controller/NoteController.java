package com.medilaboSolutions.clientui.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

import com.medilaboSolutions.clientui.beans.NoteBean;
import com.medilaboSolutions.clientui.beans.PatientBean;
import com.medilaboSolutions.clientui.proxies.DiabetesAssessmentProxy;
import com.medilaboSolutions.clientui.proxies.NoteProxy;
import com.medilaboSolutions.clientui.proxies.PatientProxy;

import javax.validation.Valid;

@Controller
public class NoteController {

  private final PatientProxy patientProxy;
  private final NoteProxy noteProxy;
  private final DiabetesAssessmentProxy diabetesAssessmentProxy;

  public NoteController(PatientProxy patientProxy, NoteProxy noteProxy,
      DiabetesAssessmentProxy diabetesAssessmentProxy) {
    this.patientProxy = patientProxy;
    this.noteProxy = noteProxy;
    this.diabetesAssessmentProxy = diabetesAssessmentProxy;
  }

  @GetMapping("/patient/{patientid}/note")
  public String getNotePatient(Model model, @PathVariable("patientid") Integer patientid) {
    Optional<PatientBean> patient = patientProxy.getPatientById(patientid);
    List<NoteBean> notes = noteProxy.getAllNote(patientid);
    if (patient.isPresent()) {
      model.addAttribute("patient", patient.get());
      model.addAttribute("notes", notes);
      model.addAttribute("diabetesAssessment", diabetesAssessmentProxy.getDiabetesAssessment(patient.get().getId()));
      return "note/list";
    } else {
      return "404";
    }
  }

  @GetMapping("patient/{patientid}/note/add")
  public String addNotePatient(NoteBean noteBean, Model model, @PathVariable("patientid") Integer patientid) {
    String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-dd-MM"));
    Optional<PatientBean> patient = patientProxy.getPatientById(patientid);
    if (patient.isPresent()) {
      model.addAttribute("patient", patient.get());
    } else {
      return "404";
    }
    model.addAttribute("bob", currentDate);
    return "note/add";
  }

  @PostMapping("/patient/{patientid}/note/validate")
  public ResponseEntity<List<ObjectError>> validateAddNote(@Valid NoteBean note,
      @PathVariable("patientid") Integer patientid,
      BindingResult result, Model model) {
    if (result.hasErrors()) {
      model.addAttribute("result", result);
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result.getAllErrors());
    } else {
      noteProxy.saveNote(note);
      return ResponseEntity.status(HttpStatus.FOUND).header("Location", "/patient/" + patientid.toString() + "/note").build();
    }
  }

  @GetMapping("/patient/{patientid}/note/{noteId}/delete")
  public ResponseEntity<Object> deleteNotePatient(Model model, @PathVariable("patientid") Integer patientid,
      @PathVariable("noteId") String noteId) {
    noteProxy.deletePatient(noteId);
    return ResponseEntity.status(HttpStatus.FOUND).header("Location", "/patient/" + patientid.toString() + "/note").build();
  }

  @GetMapping("/patient/{patientid}/note/{noteId}/update")
  public String updateNotePatient(Model model, @PathVariable("patientid") Integer patientid,
      @PathVariable("noteId") String noteId) {
    Optional<NoteBean> optionalNote = noteProxy.getNoteById(noteId);
    if (optionalNote.isPresent()) {
      model.addAttribute("patientid", patientid);
      NoteBean test = optionalNote.get();
      model.addAttribute("noteBean", test);
      return "note/update";
    } else {
      return "404";
    }
  }

  @PostMapping("/patient/{patientid}/note/{noteid}/validate")
  public ResponseEntity<Object> validateUpdateNote(@PathVariable("patientid") Integer patientid,
      @PathVariable("noteid") String noteid,
      @Valid NoteBean ntoeBean, BindingResult result, Model model) {
    System.out.println("entry controller test");
    if (result.hasErrors()) {
      model.addAttribute("result", result);
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result.getAllErrors());
    }
    noteProxy.saveNote(ntoeBean);
    return ResponseEntity.status(HttpStatus.FOUND).header("Location", "/patient/" + patientid.toString() + "/note")
        .build();
  }
}
