package com.mediaSolutions.diabetesAssessment.unit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mediaSolutions.diabetesAssessment.bean.NoteBean;
import com.mediaSolutions.diabetesAssessment.bean.PatientBean;
import com.mediaSolutions.diabetesAssessment.constant.AgeStates;
import com.mediaSolutions.diabetesAssessment.constant.DiabetesStates;
import com.mediaSolutions.diabetesAssessment.constant.TriggersTerms;
import com.mediaSolutions.diabetesAssessment.service.DiabetesAssessmentService;

@ExtendWith(MockitoExtension.class)
public class DiabetesAssessmentServiceTest {

  @InjectMocks
  DiabetesAssessmentService diabetesAssessmentService;

  @Test
  public void testGetTriggers() {
    // Arrange :
    NoteBean note1 = new NoteBean(), note2 = new NoteBean(), note3 = new NoteBean();
    note1.setNote("Hémoglobine A1C,bob, Microalbumine,Anticorps");
    note2.setNote("Taille,Poids,Fumeur,cake, Fumeuse,Réaction,");
    note3.setNote("Anormal,Cholestérol,Vertiges,Rechute,");

    List<NoteBean> notes = Arrays.asList(note1, note2, note3);
    // Act :
    int triggers = diabetesAssessmentService.getTriggers(notes);

    // Assert :
    assertEquals(triggers, TriggersTerms.TRIGGERS_TERMS.size());
  }

  @Test
  public void testGetDiabetesAssessmentFor_H_Young_Trigger2() {
    // Arrange :
    PatientBean patient = new PatientBean();
    patient.setGender("H");
    patient.setDateofbirth("2023-01-01");

    NoteBean note1 = new NoteBean();
    note1.setNote("Hémoglobine A1C,bob, Microalbumine");
    List<NoteBean> notes = Arrays.asList(note1);

    // Assert :
    assertEquals(2, diabetesAssessmentService.getTriggers(notes));
    assertEquals(AgeStates.YOUNG, diabetesAssessmentService.getAgeState(patient.getDateofbirth()));
    assertEquals(DiabetesStates.NONE, diabetesAssessmentService.getDiabetesAssessment(patient, notes));
  }

  @Test
  public void testGetDiabetesAssessmentFor_H_Young_Trigger3() {
    // Arrange :
    PatientBean patient = new PatientBean();
    patient.setGender("H");
    patient.setDateofbirth("2023-01-01");

    NoteBean note1 = new NoteBean();
    note1.setNote("Hémoglobine A1C,bob, Microalbumine, Fumeuse");
    List<NoteBean> notes = Arrays.asList(note1);

    // Assert :
    assertEquals(3, diabetesAssessmentService.getTriggers(notes));
    assertEquals(AgeStates.YOUNG, diabetesAssessmentService.getAgeState(patient.getDateofbirth()));
    assertEquals(DiabetesStates.IN_DANGER, diabetesAssessmentService.getDiabetesAssessment(patient, notes));
  }

  @Test
  public void testGetDiabetesAssessmentFor_H_Young_Trigger4() {
    // Arrange :
    PatientBean patient = new PatientBean();
    patient.setGender("H");
    patient.setDateofbirth("2023-01-01");

    NoteBean note1 = new NoteBean(), note2 = new NoteBean();
    note1.setNote("Hémoglobine A1C,bob, Microalbumine, Fumeuse");
    note2.setNote("Taille");

    List<NoteBean> notes = Arrays.asList(note1, note2);

    // Assert :
    assertEquals(4, diabetesAssessmentService.getTriggers(notes));
    assertEquals(AgeStates.YOUNG, diabetesAssessmentService.getAgeState(patient.getDateofbirth()));
    assertEquals(DiabetesStates.IN_DANGER, diabetesAssessmentService.getDiabetesAssessment(patient, notes));
  }

  @Test
  public void testGetDiabetesAssessmentFor_H_Young_Trigger5() {
    // Arrange :
    PatientBean patient = new PatientBean();
    patient.setGender("H");
    patient.setDateofbirth("2023-01-01");

    NoteBean note1 = new NoteBean(), note2 = new NoteBean();
    note1.setNote("Hémoglobine A1C,bob, Microalbumine, Fumeuse");
    note2.setNote("Taille,Poids");

    List<NoteBean> notes = Arrays.asList(note1, note2);

    // Assert :
    assertEquals(5, diabetesAssessmentService.getTriggers(notes));
    assertEquals(AgeStates.YOUNG, diabetesAssessmentService.getAgeState(patient.getDateofbirth()));
    assertEquals(DiabetesStates.EARLY_ONSET, diabetesAssessmentService.getDiabetesAssessment(patient, notes));
  }

  @Test
  public void testGetDiabetesAssessmentFor_F_Young_Trigger3() {
    // Arrange :
    PatientBean patient = new PatientBean();
    patient.setGender("F");
    patient.setDateofbirth("2023-01-01");

    NoteBean note1 = new NoteBean();
    note1.setNote("Hémoglobine A1C,bob, Microalbumine, Fumeuse");
    List<NoteBean> notes = Arrays.asList(note1);

    // Assert :
    assertEquals(3, diabetesAssessmentService.getTriggers(notes));
    assertEquals(AgeStates.YOUNG, diabetesAssessmentService.getAgeState(patient.getDateofbirth()));
    assertEquals(DiabetesStates.NONE, diabetesAssessmentService.getDiabetesAssessment(patient, notes));
  }

  @Test
  public void testGetDiabetesAssessmentFor_F_Young_Trigger4() {
    // Arrange :
    PatientBean patient = new PatientBean();
    patient.setGender("F");
    patient.setDateofbirth("2023-01-01");

    NoteBean note1 = new NoteBean(), note2 = new NoteBean();
    note1.setNote("Hémoglobine A1C,bob, Microalbumine, Fumeuse");
    note2.setNote("Taille");

    List<NoteBean> notes = Arrays.asList(note1, note2);

    // Assert :
    assertEquals(4, diabetesAssessmentService.getTriggers(notes));
    assertEquals(AgeStates.YOUNG, diabetesAssessmentService.getAgeState(patient.getDateofbirth()));
    assertEquals(DiabetesStates.IN_DANGER, diabetesAssessmentService.getDiabetesAssessment(patient, notes));
  }

  @Test
  public void testGetDiabetesAssessmentFor_F_Young_Trigger6() {
    // Arrange :
    PatientBean patient = new PatientBean();
    patient.setGender("F");
    patient.setDateofbirth("2023-01-01");

    NoteBean note1 = new NoteBean(), note2 = new NoteBean();
    note1.setNote("Hémoglobine A1C,bob, Microalbumine, Fumeuse");
    note2.setNote("Taille,Poids,Fumeur,cake");
    List<NoteBean> notes = Arrays.asList(note1, note2);

    // Assert :
    assertEquals(6, diabetesAssessmentService.getTriggers(notes));
    assertEquals(AgeStates.YOUNG, diabetesAssessmentService.getAgeState(patient.getDateofbirth()));
    assertEquals(DiabetesStates.IN_DANGER, diabetesAssessmentService.getDiabetesAssessment(patient, notes));
  }

  @Test
  public void testGetDiabetesAssessmentFor_F_Young_Trigger7() {
    // Arrange :
    PatientBean patient = new PatientBean();
    patient.setGender("F");
    patient.setDateofbirth("2023-01-01");

    NoteBean note1 = new NoteBean(), note2 = new NoteBean();
    note1.setNote("Hémoglobine A1C,bob, Microalbumine, Fumeuse");
    note2.setNote("Taille,Poids,Fumeur,cake, Réaction,");

    List<NoteBean> notes = Arrays.asList(note1, note2);

    // Assert :
    assertEquals(7, diabetesAssessmentService.getTriggers(notes));
    assertEquals(AgeStates.YOUNG, diabetesAssessmentService.getAgeState(patient.getDateofbirth()));
    assertEquals(DiabetesStates.EARLY_ONSET, diabetesAssessmentService.getDiabetesAssessment(patient, notes));
  }

  @Test
  public void testGetDiabetesAssessmentFor_HF_Elderly_Trigger1() {
    // Arrange :
    PatientBean patientH = new PatientBean();
    patientH.setGender("H");
    patientH.setDateofbirth("1970-01-01");

    PatientBean patientF = new PatientBean();
    patientF.setGender("F");
    patientF.setDateofbirth("1970-01-01");

    NoteBean note1 = new NoteBean(), note2 = new NoteBean();
    note1.setNote(" A1C,bob, Microalbumine, RGB");
    note2.setNote("Azerty, qwerty");

    List<NoteBean> notes = Arrays.asList(note1, note2);

    // Assert :
    assertEquals(1, diabetesAssessmentService.getTriggers(notes));
    assertEquals(AgeStates.ELDERLY, diabetesAssessmentService.getAgeState(patientF.getDateofbirth()));
    assertEquals(AgeStates.ELDERLY, diabetesAssessmentService.getAgeState(patientH.getDateofbirth()));
    assertEquals(DiabetesStates.NONE, diabetesAssessmentService.getDiabetesAssessment(patientH, notes));
    assertEquals(DiabetesStates.NONE, diabetesAssessmentService.getDiabetesAssessment(patientF, notes));

  }

  @Test
  public void testGetDiabetesAssessmentFor_HF_Elderly_Trigger2() {

    // Arrange :
    PatientBean patientH = new PatientBean();
    patientH.setGender("H");
    patientH.setDateofbirth("1970-01-01");

    PatientBean patientF = new PatientBean();
    patientF.setGender("F");
    patientF.setDateofbirth("1970-01-01");

    NoteBean note1 = new NoteBean(), note2 = new NoteBean();
    note1.setNote(" A1C,bob, Microalbumine, RGB, Anticorps");
    note2.setNote("Azerty, qwerty");

    List<NoteBean> notes = Arrays.asList(note1, note2);

    // Assert :
    assertEquals(2, diabetesAssessmentService.getTriggers(notes));
    assertEquals(AgeStates.ELDERLY, diabetesAssessmentService.getAgeState(patientF.getDateofbirth()));
    assertEquals(AgeStates.ELDERLY, diabetesAssessmentService.getAgeState(patientH.getDateofbirth()));
    assertEquals(DiabetesStates.BORDERLINE, diabetesAssessmentService.getDiabetesAssessment(patientH, notes));
    assertEquals(DiabetesStates.BORDERLINE, diabetesAssessmentService.getDiabetesAssessment(patientF, notes));
  }

  @Test
  public void testGetDiabetesAssessmentFor_HF_Elderly_Trigger5() {

    // Arrange :
    PatientBean patientH = new PatientBean();
    patientH.setGender("H");
    patientH.setDateofbirth("1970-01-01");

    PatientBean patientF = new PatientBean();
    patientF.setGender("F");
    patientF.setDateofbirth("1970-01-01");

    NoteBean note1 = new NoteBean(), note2 = new NoteBean();
    note1.setNote(" A1C,bob, Microalbumine, RGB");
    note2.setNote("Anormal,Cholestérol,Vertiges,Rechute,");

    List<NoteBean> notes = Arrays.asList(note1, note2);

    // Assert :
    assertEquals(5, diabetesAssessmentService.getTriggers(notes));
    assertEquals(AgeStates.ELDERLY, diabetesAssessmentService.getAgeState(patientF.getDateofbirth()));
    assertEquals(AgeStates.ELDERLY, diabetesAssessmentService.getAgeState(patientH.getDateofbirth()));
    assertEquals(DiabetesStates.BORDERLINE, diabetesAssessmentService.getDiabetesAssessment(patientH, notes));
    assertEquals(DiabetesStates.BORDERLINE, diabetesAssessmentService.getDiabetesAssessment(patientF, notes));
  }

  @Test
  public void testGetDiabetesAssessmentFor_HF_Elderly_Trigger6() {

    // Arrange :
    PatientBean patientH = new PatientBean();
    patientH.setGender("H");
    patientH.setDateofbirth("1970-01-01");

    PatientBean patientF = new PatientBean();
    patientF.setGender("F");
    patientF.setDateofbirth("1970-01-01");

    NoteBean note1 = new NoteBean(), note2 = new NoteBean();
    note1.setNote(" A1C,bob, Microalbumine, RGB, Fumeuse");
    note2.setNote("Anormal,Cholestérol,Vertiges,Rechute,");

    List<NoteBean> notes = Arrays.asList(note1, note2);

    // Assert :
    assertEquals(6, diabetesAssessmentService.getTriggers(notes));
    assertEquals(AgeStates.ELDERLY, diabetesAssessmentService.getAgeState(patientF.getDateofbirth()));
    assertEquals(AgeStates.ELDERLY, diabetesAssessmentService.getAgeState(patientH.getDateofbirth()));
    assertEquals(DiabetesStates.IN_DANGER, diabetesAssessmentService.getDiabetesAssessment(patientH, notes));
    assertEquals(DiabetesStates.IN_DANGER, diabetesAssessmentService.getDiabetesAssessment(patientF, notes));
  }

  @Test
  public void testGetDiabetesAssessmentFor_HF_Elderly_Trigger7() {

    // Arrange :
    PatientBean patientH = new PatientBean();
    patientH.setGender("H");
    patientH.setDateofbirth("1970-01-01");

    PatientBean patientF = new PatientBean();
    patientF.setGender("F");
    patientF.setDateofbirth("1970-01-01");

    NoteBean note1 = new NoteBean(), note2 = new NoteBean();
    note1.setNote(" A1C,bob, Microalbumine, RGB, Fumeuse");
    note2.setNote("Anormal,Cholestérol,Vertiges,Rechute, Réaction");

    List<NoteBean> notes = Arrays.asList(note1, note2);

    // Assert :
    assertEquals(7, diabetesAssessmentService.getTriggers(notes));
    assertEquals(AgeStates.ELDERLY, diabetesAssessmentService.getAgeState(patientF.getDateofbirth()));
    assertEquals(AgeStates.ELDERLY, diabetesAssessmentService.getAgeState(patientH.getDateofbirth()));
    assertEquals(DiabetesStates.IN_DANGER, diabetesAssessmentService.getDiabetesAssessment(patientH, notes));
    assertEquals(DiabetesStates.IN_DANGER, diabetesAssessmentService.getDiabetesAssessment(patientF, notes));
  }

  @Test
  public void testGetDiabetesAssessmentFor_HF_Elderly_Trigger8() {

    // Arrange :
    PatientBean patientH = new PatientBean();
    patientH.setGender("H");
    patientH.setDateofbirth("1970-01-01");

    PatientBean patientF = new PatientBean();
    patientF.setGender("F");
    patientF.setDateofbirth("1970-01-01");

    NoteBean note1 = new NoteBean(), note2 = new NoteBean();
    note1.setNote(" A1C,bob, Microalbumine, RGB, Fumeuse, Anticorps");
    note2.setNote("Anormal,Cholestérol,Vertiges,Rechute, Réaction");

    List<NoteBean> notes = Arrays.asList(note1, note2);

    // Assert :
    assertEquals(8, diabetesAssessmentService.getTriggers(notes));
    assertEquals(AgeStates.ELDERLY, diabetesAssessmentService.getAgeState(patientF.getDateofbirth()));
    assertEquals(AgeStates.ELDERLY, diabetesAssessmentService.getAgeState(patientH.getDateofbirth()));
    assertEquals(DiabetesStates.EARLY_ONSET, diabetesAssessmentService.getDiabetesAssessment(patientH, notes));
    assertEquals(DiabetesStates.EARLY_ONSET, diabetesAssessmentService.getDiabetesAssessment(patientF, notes));
  }

}
