package com.mediaSolutions.notemanager.unit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mediaSolutions.notemanager.model.Note;
import com.mediaSolutions.notemanager.repository.NoteRepository;
import com.mediaSolutions.notemanager.service.NoteService;

@ExtendWith(MockitoExtension.class)
public class NoteServiceTest {
  @Mock
  NoteRepository noteRepository;

  @InjectMocks
  NoteService noteService = new NoteService();

  private static Note note = new Note();

  @BeforeAll
  public static void setUpGeneral() {
    // Arrange :
    note.setId("qdsqaz1c5dz");
    note.setDate("2024-03-26");
    note.setNote("Patient pas trop bavard");
    note.setPatientid("2");
  }

  @Test
  void testDeleteById() {
    // Act :
    noteService.deleteById(note.getId());

    // Assert :
    verify(noteRepository, times(1)).deleteById(note.getId());
  }

  @Test
  void testFindAll() {
    // Act :
    noteService.findAll();

    // Assert :
    verify(noteRepository, times(1)).findAll();
  }

  @Test
  void testFindByIdWithoutExistingNote() {
    // Arrange :
    when(noteRepository.findById(note.getId())).thenReturn(Optional.empty());
    Note expectedNote = noteService.findById(note.getId());

    // Act :
    verify(noteRepository, times(1)).findById(note.getId());

    // Assert :
    assertNull(expectedNote);
  }

  @Test
  void testFindByIdWithExistingNote() {
    // Arrange :
    when(noteRepository.findById(note.getId())).thenReturn(Optional.of(note));
    Note actualNote = noteService.findById(note.getId());

    // Act :
    verify(noteRepository, times(1)).findById(note.getId());

    // Assert :
    assertEquals(actualNote, note);
  }

  @Test
  void testFindByPatientId() {
    // Act :
    noteService.findByPatientId(note.getPatientid());

    // Assert :
    verify(noteRepository, times(1)).findByPatientid(note.getPatientid());
  }

  @Test
  void testSaveNote() {
    // Act :
    noteService.saveNote(note);

    // Assert :
    verify(noteRepository, times(1)).save(note);
  }

  @Test
  void testUpdateNoteWithExistingNote() {
    // Arrange :
    Note newNote = note;
    newNote.setNote("Jack wasnt here");
    when(noteRepository.findById(note.getId())).thenReturn(Optional.of(note));
    when(noteRepository.save(newNote)).thenReturn(newNote);

    // Act :
    Note actualNote = noteService.updateNote(note.getId(), newNote);

    // Assert :
    verify(noteRepository, times(1)).save(note);
    assertEquals(newNote, actualNote);
  }

  @Test
  void testUpdateNoteWithoutExistingNote() {
    // Arrange :
    when(noteRepository.findById(note.getId())).thenReturn(Optional.empty());

    // Act :
    Note actualNote = noteService.updateNote(note.getId(), note);

    // Assert :
    verify(noteRepository, times(0)).save(note);
    assertNull(actualNote);
  }
}
