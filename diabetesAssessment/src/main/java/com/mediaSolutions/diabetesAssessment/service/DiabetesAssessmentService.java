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

@Service
public class DiabetesAssessmentService {

  public String getDiabetesAssessment(PatientBean patient, List<NoteBean> notes) {

    int age = getAgeFromBirthDate(patient.getDateofbirth());
    String ageState = getAgeState(age);
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

  private int getTriggers(List<NoteBean> notes) {
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

  private int getAgeFromBirthDate(String dateOfBirthString) {
    return Period.between(LocalDate.parse(dateOfBirthString), LocalDate.now()).getYears();
  }

  private String getAgeState(int age) {
    if (age >= 30) {
      return AgeStates.YOUNG;
    } else {
      return AgeStates.ELDERLY;
    }
  }
}
