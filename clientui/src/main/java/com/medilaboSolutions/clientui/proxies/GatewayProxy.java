package com.medilaboSolutions.clientui.proxies;

import java.util.List;
import java.util.Optional;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.FeignClientProperties.FeignClientConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.medilaboSolutions.clientui.bean.NoteBean;
import com.medilaboSolutions.clientui.bean.PatientBean;

/**
 * Some Javadoc :
 * 
 * Interface defining the Feign client for interacting with the Gateway
 * microservice.
 */
@ComponentScan
@FeignClient(name = "microservice-gateway", url = "${microservice-gateway}", configuration = FeignClientConfiguration.class)
public interface GatewayProxy {

  /**
   * Some Javadoc :
   * 
   * Sends a request to the Gateway microservice to perform a logout action.
   */
  @GetMapping(value = "/app-logout")
  void getLogout();

  /**
   * Some Javadoc :
   * 
   * Retrieves all patients.
   *
   * @return A List of PatientBean objects representing all patients.
   */
  @GetMapping(value = "/patientmanager")
  List<PatientBean> getAllPatient();

  /**
   * Some Javadoc :
   * 
   * Retrieves a specific patient by its ID.
   *
   * @param id The ID of the patient to retrieve.
   * @return An Optional containing the PatientBean object representing the
   *         retrieved patient, if found.
   */
  @GetMapping(value = "/patientmanagerbyid/{id}")
  Optional<PatientBean> getPatientById(@PathVariable("id") int id);

  /**
   * Some Javadoc :
   * 
   * Saves a new patient.
   *
   * @param patient The PatientBean object representing the patient to save.
   * @return The PatientBean object representing the saved patient.
   */
  @PostMapping(value = "/patientmanager")
  @ResponseStatus(HttpStatus.CREATED)
  PatientBean savePatient(@RequestBody PatientBean patient);

  /**
   * Some Javadoc :
   * 
   * Deletes a patient by its ID.
   *
   * @param id The ID of the patient to delete.
   */
  @DeleteMapping(value = "/patientmanagerbyid/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  void deletePatient(@PathVariable("id") int id);

  /**
   * Some Javadoc :
   * 
   * Retrieves all notes associated with a specific patient.
   *
   * @param patientid The ID of the patient for whom to retrieve the notes.
   * @return A List of NoteBean objects representing the notes associated with the
   *         specified patient.
   */
  @GetMapping(value = "/notemanagerall/patientid/{patientid}")
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
  @GetMapping(value = "/notemanagerall/{id}")
  Optional<NoteBean> getNoteById(@PathVariable("id") String id);

  /**
   * Some Javadoc :
   * 
   * Saves a new note.
   *
   * @param note The NoteBean object representing the note to save.
   * @return The NoteBean object representing the saved note.
   */
  @PostMapping(value = "/notemanagerone")
  @ResponseStatus(HttpStatus.CREATED)
  NoteBean saveNote(@RequestBody NoteBean note);

  /**
   * Some Javadoc :
   * 
   * Deletes a note by its ID.
   *
   * @param id The ID of the note to delete.
   */
  @DeleteMapping(value = "/notemanagerall/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  void deletePatient(@PathVariable("id") String noteId);

  /**
   * Some Javadoc :
   * 
   * Retrieves the diabetes assessment for a specific patient.
   *
   * @param patientid The ID of the patient for whom to retrieve the diabetes
   *                  assessment.
   * @return A String representing the diabetes assessment for the specified
   *         patient.
   */
  @GetMapping(value = "/diabetesAssessment/patientid/{patientid}")
  String getDiabetesAssessment(@PathVariable("patientid") int patientid);
}
