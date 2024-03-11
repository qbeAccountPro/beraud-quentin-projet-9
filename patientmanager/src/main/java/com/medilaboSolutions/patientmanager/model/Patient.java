package com.medilaboSolutions.patientmanager.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Some Javadoc :
 * 
 * This class represent the patient entity.
 * 
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "patient")
public class Patient {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private int id;

  @Column(name = "first_name")
  @NotBlank(message = "Firstname is mandatory")
  @Pattern(regexp = "^[a-zA-Z'-]{1,30}$", message = "Invalid first name")
  private String firstname;

  @Column(name = "last_name")
  @NotBlank(message = "Lastname is mandatory")
  @Pattern(regexp = "^[a-zA-Z'-]{1,30}$", message = "Invalid last name")
  private String lastname;

  @Column(name = "gender")
  @NotBlank(message = "Gender is mandatory")
  @Pattern(regexp = "^(M|F)$", message = "Invalid gender, Only biological sex is agreed : F or M")
  private String gender;

  @Column(name = "date_of_birth")
  @NotBlank(message = "Date of birth is mandatory")
  @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "Invalid date format. Please use yyyy-mm-dd")
  private String dateofbirth;

  @Column(name = "address")
  @Pattern(regexp = "^[a-zA-Z0-9\\s,-]*$", message = "Invalid address")
  private String address;

  @Column(name = "phone")
  @Pattern(regexp = "^\\+?[0-9]{10,}$", message = "Invalid phone number")
  private String phone;
}