package com.denverairport.baggage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.denverairport.baggage.service.BaggageService;

@SpringBootApplication
public class CodingAssignment1Application {
	
	@Autowired
	BaggageService baggageService;

	public static void main(String[] args) {
		SpringApplication.run(CodingAssignment1Application.class, args);
	}
}
