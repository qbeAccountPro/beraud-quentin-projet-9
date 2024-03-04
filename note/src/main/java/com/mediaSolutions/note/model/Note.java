package com.mediaSolutions.note.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "notes")
public class Note {
  @Id
  private String id;
  @NotBlank(message = "Patient id is mandatory")
  private String patientid; 
  @NotBlank(message = "Note is mandatory")
  private String note;
  private String date; 
}