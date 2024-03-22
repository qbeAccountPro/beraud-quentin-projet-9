package com.medilaboSolutions.clientui.proxies;

import java.util.List;
import java.util.Optional;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.medilaboSolutions.clientui.bean.NoteBean;

/**
 * Some Javadoc :
 * 
 * Interface defining the Feign client for interacting with the NoteManager
 * microservice.
 */
@ComponentScan
@FeignClient(name = "microservice-notemanager", url = "${microservice-notemanager}")
public interface NoteProxy {
  /**
   * Some Javadoc :
   * 
   * Retrieves all notes associated with a specific patient.
   *
   * @param patientid The ID of the patient for whom to retrieve the notes.
   * @return A List of NoteBean objects representing the notes associated with the
   *         specified patient.
   */
  @GetMapping(value = "/note/patientid/{patientid}")
  List<NoteBean> getAllNote(@PathVariable("patientid") int patientid);

  /**
   * Some Javadoc :
   * 
   * Retrieves a specific note by its id.
   *
   * @param id The ID of the note to retrieve.
   * @return An Optional containing the NoteBean object representing the retrieved
   *         note, if found.
   */
  @GetMapping(value = "/note/{id}")
  Optional<NoteBean> getNoteById(@PathVariable("id") String id);

  /**
   * Some Javadoc :
   * 
   * Saves a new note.
   *
   * @param note The NoteBean object representing the note to save.
   * @return The NoteBean object representing the saved note.
   */
  @PostMapping(value = "/note")
  @ResponseStatus(HttpStatus.CREATED)
  NoteBean saveNote(@RequestBody NoteBean note);

  /**
   * Some Javadoc :
   * 
   * Deletes a note by its ID.
   *
   * @param id The ID of the note to delete.
   */
  @DeleteMapping(value = "/note/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  void deletePatient(@PathVariable("id") String noteId);
}
