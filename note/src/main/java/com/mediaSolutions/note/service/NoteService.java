package com.mediaSolutions.note.service;

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
    return noteRepository.save(note);
  }

  public Note updateNote(String id, Note note) {
    if (noteRepository.findById(id).isPresent()) {
      note.setId(id);
      return noteRepository.save(note);
    } else {
      return null;
    }
  }

  public void deleteById(String id) {
    noteRepository.deleteById(id);
  }
}