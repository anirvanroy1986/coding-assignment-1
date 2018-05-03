package com.denverairport.baggage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.denverairport.baggage.service.BaggageService;
import com.denverairport.baggage.utils.BaggageProcessor;

@SpringBootApplication
public class CodingAssignment1Application {
	
	@Autowired
	BaggageService baggageService;

	public static void main(String[] args) throws Exception {
		SpringApplication.run(CodingAssignment1Application.class, args);
		
		String inputFile = args[1];
		
		if(inputFile == null) {
			throw new Exception("No Input file provided for determining Baggage system");
		}
		
		BaggageProcessor processor = new BaggageProcessor();
		processor.init(inputFile);
	}
}
