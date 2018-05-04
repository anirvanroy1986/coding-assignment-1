package com.denverairport.baggage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.denverairport.baggage.service.BaggageService;
import com.denverairport.baggage.service.impl.BaggageServiceImpl;
import com.denverairport.baggage.utils.BaggageProcessor;

@SpringBootApplication
@EnableAutoConfiguration
public class CodingAssignment1Application {
	
	BaggageService baggageService;
	
	public static void main(String[] args) throws Exception {
		ApplicationContext ctx = SpringApplication.run(CodingAssignment1Application.class, args);
		
		CodingAssignment1Application app = new CodingAssignment1Application();
		//app.baggageService = (BaggageService)ctx.getBean("BaggageServiceImpl");
		app.execute(args[0]);
		
	}
	
	public void execute(String path) throws Exception {
		if(path == null) {
			throw new Exception("No Input file provided for determining Baggage system");
		}
		baggageService = new BaggageServiceImpl();
		BaggageProcessor processor = new BaggageProcessor();
		processor.init(path);
		//BaggageService baggageService = new BaggageServiceImpl();
		baggageService.getBaggageRoutes(processor);
	}

	/**
	 * @return the baggageService
	 */
	public BaggageService getBaggageService() {
		return baggageService;
	}

	/**
	 * @param baggageService the baggageService to set
	 */
	
	public void setBaggageService(BaggageService baggageService) {
		this.baggageService = baggageService;
	}
}
