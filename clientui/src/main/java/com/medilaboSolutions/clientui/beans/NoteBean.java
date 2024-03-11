package com.medilaboSolutions.clientui.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoteBean {
  private String id;
  private String patientid;
  private String note;
  private String date;
}
