package com.medilaboSolutions.clientui.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Some Javadoc :
 * 
 * This class represents a patient entity.
 * It contains basic information about the patient.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientBean {
  private int id;
  private String firstname;
  private String lastname;
  private String gender;
  private String dateofbirth;
  private String address;
  private String phone;
}
