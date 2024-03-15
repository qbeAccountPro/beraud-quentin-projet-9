package com.mediaSolutions.diabetesAssessment.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mediaSolutions.diabetesAssessment.bean.NoteBean;
import com.mediaSolutions.diabetesAssessment.bean.PatientBean;
import com.mediaSolutions.diabetesAssessment.constant.AgeStates;
import com.mediaSolutions.diabetesAssessment.constant.DiabetesStates;
import com.mediaSolutions.diabetesAssessment.constant.TriggersTerms;

/**
 * Some Javadoc:
 * 
 * Service class for performing diabetes assessment calculations.
 * 
 * This service class provides one method for calculating diabetes assessment
 * based on patient information and notes containing trigger terms.
 * 
 */
@Service
public class DiabetesAssessmentService {

  /**
   * Some Javadoc:
   * 
   * Calculates diabetes assessment based on patient information and notes.
   * 
   * @param patient The patient for whom to calculate diabetes assessment.
   * @param notes   The list of notes containing trigger terms.
   * @return The diabetes assessment result.
   */
  public String getDiabetesAssessment(PatientBean patient, List<NoteBean> notes) {

    String ageState = getAgeState(patient.getDateofbirth());
    int triggers = getTriggers(notes);
    if (notes.size() == 0) {
      return DiabetesStates.NONE;
    } else {
      switch (ageState) {
        case AgeStates.YOUNG:
          switch (patient.getGender()) {
            case "H":
              if (triggers < 3) {
                return DiabetesStates.NONE;
              } else if (triggers < 5) {
                return DiabetesStates.IN_DANGER;
              } else if (triggers >= 5) {
                return DiabetesStates.EARLY_ONSET;
              } else {
                return DiabetesStates.ERROR;
              }
            case "F":
              if (triggers < 4) {
                return DiabetesStates.NONE;
              } else if (triggers < 7) {
                return DiabetesStates.IN_DANGER;
              } else if (triggers >= 7) {
                return DiabetesStates.EARLY_ONSET;
              } else {
                return DiabetesStates.ERROR;
              }
            default:
              return DiabetesStates.ERROR;
          }
        case AgeStates.ELDERLY:
          if (triggers < 2) {
            return DiabetesStates.NONE;
          } else if (triggers < 6) {
            return DiabetesStates.BORDERLINE;
          } else if (triggers < 8) {
            return DiabetesStates.IN_DANGER;
          } else if (triggers >= 8) {
            return DiabetesStates.EARLY_ONSET;
          } else {
            return DiabetesStates.ERROR;
          }
        default:
          return DiabetesStates.ERROR;
      }
    }
  }

  /**
   * Some Javadoc:
   * 
   * Counts the number of trigger terms present in all notes.
   * 
   * @param notes The list of notes containing potentially trigger terms.
   * @return The count of trigger terms present in the notes.
   */
  public int getTriggers(List<NoteBean> notes) {
    int triggersCount = 0;
    List<String> triggersTerms = TriggersTerms.TRIGGERS_TERMS;
    for (String trigger : triggersTerms) {
      for (NoteBean note : notes) {
        if (note.getNote().contains(trigger)) {
          triggersCount++;
          break; // Quit the iteration if the trigger is present, An another note with the same
                 // disease or trigger term is usless for the assessment.
        }
      }
    }
    return triggersCount;
  }

  public String getAgeState(String dateOfBirthString) {
    int age = Period.between(LocalDate.parse(dateOfBirthString), LocalDate.now()).getYears();
    if (age <= 30) {
      return AgeStates.YOUNG;
    } else {
      return AgeStates.ELDERLY;
    }
  }
}
