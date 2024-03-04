package com.mediaSolutions.note;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class NoteApplication  {
	public static void main(String[] args) {
		SpringApplication.run(NoteApplication.class, args);
	}
}