package com.mediaSolutions.note.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Some Javadoc :
 * 
 * This class represent the Note entity.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "notes")
public class Note {
  @Id
  private String id;
  private String patientid; 
  @NotBlank(message = "Note is mandatory")
  private String note;
  private String date; 
}