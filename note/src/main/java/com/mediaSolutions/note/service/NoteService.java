package com.mediaSolutions.note.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mediaSolutions.note.model.Note;
import com.mediaSolutions.note.repository.NoteRepository;

/**
 * Some Javadoc :
 * 
 * Service class for Note entities.
 * Provides methods to perform CRUD operations on Note objects.
 */
@Service
public class NoteService {

  @Autowired
  NoteRepository noteRepository;

  /**
   * Some Javadoc :
   * 
   * Retrieves a list of all Note entities.
   * 
   * @return the list of all Note.
   */
  public List<Note> findAll() {
    return noteRepository.findAll();
  }

  /**
   * Some Javadoc :
   * 
   * This class retrieves a Note entity by its ID.
   * 
   * @param id of the note.
   * @return the note.
   */
  public Note findById(String id) {
    Optional<Note> optionalNote = noteRepository.findById(id);
    if (optionalNote.isPresent()) {
      return optionalNote.get();
    } else {
      return null;
    }
  }

  /**
   * Some Javadoc :
   * 
   * This class allow to save a note.
   * 
   * @param note to save.
   * @return the coresponding note with its ID from the database.
   */
  public Note saveNote(Note note) {
    note.setDate(getDate());
    return noteRepository.save(note);
  }

  /**
   * Some Javadoc :
   * 
   * This class allow to update a note.
   * 
   * @param id   of the specific note to update.
   * @param note with the previous values.
   * @return note with the new values.
   */
  public Note updateNote(String id, Note note) {
    Optional<Note> OptionalNote = noteRepository.findById(id);
    if (OptionalNote.isPresent()) {
      Note currentNote = OptionalNote.get();
      currentNote.setNote(note.getNote());
      currentNote.setDate(getDate());
      return noteRepository.save(currentNote);
    } else {
      return null;
    }
  }

  /**
   * Some Javadoc :
   * 
   * This class allow the deletion of a specific note from this ID.
   * 
   * @param id of the specific note.
   */
  public void deleteById(String id) {
    noteRepository.deleteById(id);
  }

  /**
   * Some Javadoc :
   * 
   * This method get the current date with this format "YYYY-MM-DD"
   * 
   * @return the corresponding date.
   */
  private static String getDate() {
    return LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
  }

  /**
   * Some Javadoc :
   * 
   * This method retrieves a list of note from their patient ID.
   * 
   * @param patientid
   * @return a list of note
   */
  public List<Note> findByPatientId(String patientid) {
    return noteRepository.findByPatientid(patientid);
  }
}