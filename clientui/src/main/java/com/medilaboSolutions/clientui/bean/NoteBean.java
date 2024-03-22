package com.medilaboSolutions.clientui.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Some Javadoc :
 * 
 * This class represents a note entity.
 * It contains basic information about the patient note.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoteBean {
  private String id;
  private String patientid;
  private String note;
  private String date;
}
