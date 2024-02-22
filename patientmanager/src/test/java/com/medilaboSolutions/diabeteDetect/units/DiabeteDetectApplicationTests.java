package com.medilaboSolutions.diabeteDetect.units;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.medilaboSolutions.diabeteDetect.DiabeteDetectApplication;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = DiabeteDetectApplication.class)
class DiabeteDetectApplicationTests {

	@Autowired
	private DiabeteDetectApplication application;

	@Test
	void contextLoads() {
		assertNotNull(application, "L'application n'est pas chargée.");
	}
}
