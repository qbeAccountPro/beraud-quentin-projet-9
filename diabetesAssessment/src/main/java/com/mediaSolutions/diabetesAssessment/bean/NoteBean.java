package com.mediaSolutions.diabetesAssessment.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Some Javadoc :
 * 
 * This class represent the bean note entity.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoteBean {
  private String id;
  private String note;
  private String date;
  private String patientid;
}
