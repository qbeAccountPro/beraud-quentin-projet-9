package com.medilaboSolutions.diabeteDetect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients 
@SpringBootApplication
public class DiabeteDetectApplication {

	public static void main(String[] args) {
		SpringApplication.run(DiabeteDetectApplication.class, args);
	}
}