package com.mediaSolutions.notemanager.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.mediaSolutions.notemanager.model.Note;

/**
 * Some Javadoc :
 * 
 * Repository inteface for Note entities.
 * Extends MongoRepository to provide basic CRUD operations for Note objects.
 */
@Repository
public interface NoteRepository extends MongoRepository<Note, String> {

  /**
   * Some Javadoc :
   * 
   * Retrieves a list of note by theirs patient id.
   * 
   * @param patientid of the patient for whom to retrieve the notes.
   * @return a list of note
   */
  List<Note> findByPatientid(String patientid);
}