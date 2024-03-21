package com.mediaSolutions.notemanager;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = NoteManagerApplication.class)
class NoteManagerApplicationTests {

	@Autowired
	private NoteManagerApplication app;

	@Test
	void contextLoads() {
		assertNotNull(app, "L'application n'est pas chargée.");
	}
}
