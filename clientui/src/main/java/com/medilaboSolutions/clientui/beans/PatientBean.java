package com.medilaboSolutions.clientui.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** 
 *  Some Javadoc :
 * 
 * This class represent the bean patient entity.
 * 
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
