package com.mediaSolutions.note.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mediaSolutions.note.bean.PatientBean;
import com.mediaSolutions.note.controller.NoteController;
import com.mediaSolutions.note.model.Note;
import com.mediaSolutions.note.proxy.PatientProxy;
import com.mediaSolutions.note.service.NoteService;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.event.TransactionalEventListener;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import org.springframework.http.MediaType;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.type.TypeReference;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import javax.swing.text.html.Option;

import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.Mock;
import org.mockito.Mockito;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
@TestPropertySource(properties = {
    "microservice-patientmanager=http://localhost:9001"
})
public class NoteIntegrationsTest {

  @Autowired
  NoteService noteService;

  @Autowired
  PatientProxy patientProxy;

  @Autowired
  NoteController noteController = new NoteController(patientProxy);

  private static Note note = new Note();
  private static PatientBean patient = new PatientBean();
  private String noteID;

  private MockMvc mvc;

  @BeforeEach
  void setup() {
    this.mvc = MockMvcBuilders
        .standaloneSetup(noteController)
        .setControllerAdvice()
        .build();
  }

  @BeforeAll
  public static void setUpGeneral() {
    // Arrange :
    patient.setFirstname("George");
    patient.setLastname("Wayne");
    patient.setGender("H");
    patient.setDateofbirth("1990-02-20");
    patient.setAddress("Nowhere");
    patient.setPhone("0655554488");

    note.setDate(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    note.setNote("Patient pas trop bavard");
    note.setPatientid("2");
  }

  @Test
  void testGetAllNote() throws Exception {
    // Arrange :
    testAddNote();
    testAddNote();

    // Act :
    MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/note")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andReturn();

    // Assert - Deserialize :
    String jsonResponse = result.getResponse().getContentAsString();
    ObjectMapper objectMapper = new ObjectMapper();
    List<Note> patients = objectMapper.readValue(jsonResponse, new TypeReference<List<Note>>() {
    });

    assertTrue(patients.size() >= 2);
  }

  @Test
  @TransactionalEventListener
  void testGetAllNoteFromPatientId() throws Exception {
    // Arrange :
    testAddNote();
    List<Note> expectedListNote = noteService.findByPatientId(note.getPatientid());

    // Act :
    MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/note/patientid/{patientid}", note.getPatientid())
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andReturn();

    // Assert :
    String jsonResponse = result.getResponse().getContentAsString();
    ObjectMapper objectMapper = new ObjectMapper();
    List<Note> actualListNote = objectMapper.readValue(jsonResponse, new TypeReference<List<Note>>() {
    });

    // Assert deletion :
    assertEquals(expectedListNote, actualListNote);
  }

  @Test
  void testGetNoteById() throws Exception {
    // Arrange :
    testAddNote();
    Note expectedNote = noteService.findById(noteID);

    // Act :
    MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/note/{noteID}", noteID)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andReturn();

    // Assert :
    ObjectMapper objectMapper = new ObjectMapper();
    Note actualNote = objectMapper.readValue(result.getResponse().getContentAsString(), Note.class);

    // Assert deletion :
    assertEquals(expectedNote, actualNote);
  }

  @Test
  public void testAddNote() throws Exception {
    // Arrange - Serialize :
    ObjectMapper objectMapper = new ObjectMapper();
    String noteJson = objectMapper.writeValueAsString(note);

    // Act - Request add:
    MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/note")
        .content(noteJson)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk()).andReturn();

    // Deserialize
    noteID = objectMapper.readValue(result.getResponse().getContentAsString(), Note.class).getId();

    Note noteSaved = noteService.findById(noteID);

    assertNotNull(noteID);
    assertEquals(note.getDate(), noteSaved.getDate());
    assertEquals(note.getNote(), noteSaved.getNote());
    assertEquals(note.getPatientid(), noteSaved.getPatientid());

    noteID = noteSaved.getId();
  }

  @Test
  void testUpdateNote() throws Exception {
    // Arrange :
    testAddNote();

    Note newNote = note;
    newNote.setId(noteID);
    newNote.setNote("Bob n'est pas venu");

    ObjectMapper objectMapper = new ObjectMapper();
    String newNoteJson = objectMapper.writeValueAsString(newNote);
    // Act :
    mvc.perform(MockMvcRequestBuilders.put("/note/{noteID}", noteID)
        .content(newNoteJson)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andReturn();

    Note noteSaved = noteService.findById(noteID);
    // Assert :
    assertEquals(noteSaved, newNote);
  }

  @Test
  void testDeleteNote() throws Exception {
    // Arrange - Call add note :
    testAddNote();

    // Act :
    mvc.perform(MockMvcRequestBuilders.delete("/note/{noteID}", noteID)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andReturn();

    // Assert :
    assertNull(noteService.findById(noteID));
  }

  @AfterAll
  void deleteAllNotes() {
    List<Note> notesToDelete = noteService.findByPatientId(note.getPatientid());
    for (Note noteToDelete : notesToDelete) {
      noteService.deleteById(noteToDelete.getId());
    }
  }
}
