package com.medilaboSolutions.clientui.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.medilaboSolutions.clientui.beans.NoteBean;
import com.medilaboSolutions.clientui.beans.PatientBean;
import com.medilaboSolutions.clientui.proxies.NoteProxy;
import com.medilaboSolutions.clientui.proxies.patientProxy;

import javax.validation.Valid;

@Controller
public class NoteController {

  private final patientProxy patientProxy;
  private final NoteProxy noteProxy;

  public NoteController(patientProxy patientProxy, NoteProxy noteProxy) {
    this.patientProxy = patientProxy;
    this.noteProxy = noteProxy;
  }

  @GetMapping("/patient/{patientid}/note")
  public String getNotePatient(Model model, @PathVariable("patientid") Integer patientid) {
    Optional<PatientBean> patient = patientProxy.getPatientById(patientid);
    List<NoteBean> notes = noteProxy.getAllNote(patientid);
    if (patient.isPresent()) {
      model.addAttribute("patient", patient.get());
      model.addAttribute("notes", notes);
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
  public String validateAddNote(@Valid NoteBean note, @PathVariable("patientid") Integer patientid,
      BindingResult result, Model model) {
    if (result.hasErrors()) {
      model.addAttribute("result", result);
      return "note/add";
    } else {
      noteProxy.saveNote(note);
      return "redirect:/patient/{patientid}/note";
    }
  }

  @GetMapping("/patient/{patientid}/note/{noteId}/delete")
  public String deleteNotePatient(Model model, @PathVariable("patientid") Integer patientid,
      @PathVariable("noteId") String noteId) {
    noteProxy.deletePatient(noteId);
    return "redirect:/patient/{patientid}/note";
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
  public String validateUpdateNote(@PathVariable("patientid") Integer patientid, @PathVariable("noteid") String noteid,
      @Valid NoteBean ntoeBean, BindingResult result, Model model) {
        System.out.println("entry controller test");
    if (result.hasErrors()) {
      model.addAttribute("result", result);
      return "note/update";
    }
    noteProxy.saveNote(ntoeBean);
    return "redirect:/patient/{patientid}/note";

  }
}
