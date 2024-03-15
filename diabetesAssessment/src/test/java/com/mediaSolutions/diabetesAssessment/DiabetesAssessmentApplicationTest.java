package com.mediaSolutions.diabetesAssessment;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = DiabetesAssessmentApplication.class)
class DiabetesAssessmentApplicationTest {
	@Autowired
	private DiabetesAssessmentApplication app;

	@Test
	void contextLoads() {
		assertNotNull(app, "L'application n'est pas chargée.");
	}

}