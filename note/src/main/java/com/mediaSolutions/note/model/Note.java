package com.mediaSolutions.note.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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
  private String patientid; // TODO ne pas modifier sur la maj ou supprimer
  private String note; // NOT NULL mini 3 pour ras genre
  private Date date; //TODO set a normal way to defin
}