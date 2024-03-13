package com.mediaSolutions.diabetesAssessment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class DiabetesAssessmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(DiabetesAssessmentApplication.class, args);
	}

}
