package com.mediaSolutions.note.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mediaSolutions.note.model.Note;
import com.mediaSolutions.note.repository.NoteRepository;

@Service
public class NoteService {

  @Autowired
  NoteRepository noteRepository;

  public List<Note> findAll() {
    return noteRepository.findAll();
  }

  public Note findById(String id) {
    Optional<Note> optionalNote = noteRepository.findById(id);
    if (optionalNote.isPresent()) {
      return optionalNote.get();
    } else {
      return null;
    }
  }

  public Note saveNote(Note note) {
    note.setDate(getDate());
    return noteRepository.save(note);
  }

  public Note updateNote(String id, Note note) {
    Optional<Note> OptionalNote = noteRepository.findById(id);
    if (OptionalNote.isPresent()) {
      Note currentNote = OptionalNote.get();
      currentNote.setNote(note.getNote());
      currentNote.setDate(getDate());
      return noteRepository.save(currentNote);
    } else {
      return null;
    }
  }

  public void deleteById(String id) {
    noteRepository.deleteById(id);
  }

  public static String getDate() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}