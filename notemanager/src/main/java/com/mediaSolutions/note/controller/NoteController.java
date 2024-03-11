package com.mediaSolutions.note.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mediaSolutions.note.bean.PatientBean;
import com.mediaSolutions.note.model.Note;
import com.mediaSolutions.note.proxy.PatientProxy;
import com.mediaSolutions.note.service.NoteService;

@RestController
@RequestMapping("/note")
public class NoteController {

  private final PatientProxy patientProxy;

  public NoteController(PatientProxy patientProxy) {
    this.patientProxy = patientProxy;
  }

  @Autowired
  NoteService noteService;

  /**
   * Some javadoc :
   * 
   * This method retrieves a list of all note.
   *
   * @return a list of all note.
   */
  @GetMapping("")
  public List<Note> getAllNote() {
    return noteService.findAll();
  }

    /**
   * Some javadoc :
   * 
   * This method retrieves a list of all note.
   *
   * @return a list of all note.
   */
  @GetMapping("/patientid/{patientid}")
  public List<Note> getAllNoteFromPatientId(@PathVariable String patientid) {
    return noteService.findByPatientId(patientid);
  }

  /**
   * Some javadoc :
   * 
   * This method retrieves a note by its id.
   *
   * @return note if he is known from our database or null.
   */
  @GetMapping("/{id}")
  public Note getNoteById(@PathVariable String id) {
    return noteService.findById(id);
  }

  /**
   * Some javadoc :
   * 
   * Adds a new note to the database.
   *
   * @param note the note object to add.
   * @return the added note object.
   */
  @PostMapping
  public Note addNote(@RequestBody Note note) {
    Optional<PatientBean> optionalPatient = Optional.of(new PatientBean());
    try {
      optionalPatient = patientProxy.getPatientById(Integer.parseInt(note.getPatientid()));
    } catch (Exception e) {
      optionalPatient = Optional.empty();
      return null; // Case of the wrong value inside patient id
    }
    if (optionalPatient.isPresent()) {
      return noteService.saveNote(note);
    } else {
      return null; // Case of the non-existent patient
    }
  }

  /**
   * Some javadoc :
   * 
   * Updates an existing note in the database.
   *
   * @param id          the ID of the note to update.
   * @param noteDetails the updated note details.
   * @return the updated note object if successful, otherwise null.
   */
  @PutMapping("/{id}")
  public Note updateNote(@PathVariable String id, @RequestBody Note noteDetails) {
    return noteService.updateNote(id, noteDetails);
  }

  /**
   * Some javadoc :
   * 
   * Deletes a note from the database by ID.
   *
   * @param id the note IDto delete.
   * @return ResponseEntity indicating the success of the deletion operation.
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteNote(@PathVariable String id) {
    noteService.deleteById(id);
    return ResponseEntity.ok().build();
  }
}