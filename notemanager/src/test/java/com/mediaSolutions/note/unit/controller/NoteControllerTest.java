package com.mediaSolutions.note.unit.controller;

import org.mockito.InjectMocks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.mediaSolutions.note.bean.PatientBean;
import com.mediaSolutions.note.controller.NoteController;
import com.mediaSolutions.note.model.Note;
import com.mediaSolutions.note.proxy.PatientProxy;
import com.mediaSolutions.note.service.NoteService;

@ExtendWith(MockitoExtension.class)
public class NoteControllerTest {
  @Mock
  NoteService noteService;

  PatientProxy patientProxy = Mockito.mock(PatientProxy.class);

  @InjectMocks
  NoteController noteController = new NoteController(patientProxy);

  private static Note note = new Note();
  private static PatientBean patientTest = new PatientBean();

  @BeforeAll
  public static void setUpGeneral() {
    // Arrange :
    note.setId("qdsqaz1c5dz");
    note.setDate("2024-03-26");
    note.setNote("Patient pas trop bavard");
    note.setPatientid("2");

    // Arrange :
    patientTest.setId(2);
    patientTest.setFirstname("George");
    patientTest.setLastname("Wayne");
    patientTest.setGender("H");
    patientTest.setDateofbirth("1990-02-20");
    patientTest.setAddress("Nowhere");
    patientTest.setPhone("0655554488");
  }

  @Test
  void testAddNote() {
    // Arrange:
    when(patientProxy.getPatientById(Integer.parseInt(note.getPatientid()))).thenReturn(Optional.of(patientTest));
    when(noteService.saveNote(note)).thenReturn(note);
    // Act :
    Note actualNote = noteController.addNote(note);

    // Assert :
    assertEquals(note, actualNote);
  }

  @Test
  void testAddNoteWithExceptionOnProxy() {
    // Arrange:
    when(patientProxy.getPatientById(Integer.parseInt(note.getPatientid())))
        .thenThrow(new RuntimeException("Simulated Exception"));

    // Act :
    Note actualNote = noteController.addNote(note);

    // Assert :
    assertNotEquals(note, actualNote);
    assertNull(actualNote);
  }

  @Test
  void testAddNoteWithInexistingPatient() {
    // Arrange:
    when(patientProxy.getPatientById(Integer.parseInt(note.getPatientid()))).thenReturn(Optional.empty());

    // Act :
    Note actualNote = noteController.addNote(note);

    // Assert :
    assertNotEquals(note, actualNote);
    assertNull(actualNote);
  }

  @Test
  void testDeleteNote() {
    // Arrange:
    doNothing().when(noteService).deleteById(note.getId());

    // Act :
    ResponseEntity<?> responseEntity = noteController.deleteNote(note.getId());

    // Assert :
    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
  }

  @Test
  void testGetAllNote() {
    // Act :
    noteController.getAllNote();

    // Assert :
    verify(noteService, times(1)).findAll();
  }

  @Test
  void testGetAllNoteFromPatientId() {
    // Act :
    noteController.getAllNoteFromPatientId(note.getPatientid());

    // Assert :
    verify(noteService, times(1)).findByPatientId(note.getPatientid());
  }

  @Test
  void testGetNoteById() {
    // Act :
    noteController.getNoteById(note.getId());

    // Assert :
    verify(noteService, times(1)).findById(note.getId());
  }

  @Test
  void testUpdateNote() {
    // Arrange:
    when(noteService.updateNote(note.getId(), note)).thenReturn(note);

    // Act :
    Note actualNote = noteController.updateNote(note.getId(), note);

    // Assert :
    assertEquals(note, actualNote);
  }
}
