package com.denverairport.baggage;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.denverairport.baggage.model.BaggageRoute;
import com.denverairport.baggage.service.BaggageService;
import com.denverairport.baggage.utils.BaggageProcessor;

@SpringBootApplication
public class Application implements CommandLineRunner{
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	BaggageService baggageService;
	
	@Autowired
	BaggageProcessor processor;
	
	public static void main(String[] args) throws Exception {
		SpringApplication.run(Application.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {

		if(args[0] == null) {
			LOGGER.error("No input file provided ");
			throw new Exception("No Input file provided for determining Baggage system");
		}else {
			LOGGER.info("Input file provided, path--> "+args[0]);
		}
		
		LOGGER.info("Processing input file ");
		processor.init(args[0]);
		List<BaggageRoute> baggageRouteList = baggageService.getBaggageRoutes(processor);
		
		LOGGER.info("*******Printing Optimized routes for a given Baggage - START *******");
		for(BaggageRoute route: baggageRouteList) {
			System.out.println(route.toString());
		}
		LOGGER.info("*******Printing Optimized routes for a given Baggage - END *******");
	}
	
}
