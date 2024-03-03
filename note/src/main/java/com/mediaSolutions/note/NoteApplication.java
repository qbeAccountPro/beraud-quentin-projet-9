package com.mediaSolutions.note;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.mediaSolutions.note.model.Note;
import com.mediaSolutions.note.repository.NoteRepository;

@SpringBootApplication
public class NoteApplication implements CommandLineRunner {

	private final Logger logger = LoggerFactory.getLogger(NoteApplication.class);

	@Autowired
	private NoteRepository noteRepository;

	public static void main(String[] args) {
		SpringApplication.run(NoteApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Optional<Note> p = noteRepository.findById("6177a31824f1d205e0b0692d");
		if (p.isPresent()) {
			logger.info(p.get().getNote());
		} else {
			logger.info("Post not found");
		}
	}

}
