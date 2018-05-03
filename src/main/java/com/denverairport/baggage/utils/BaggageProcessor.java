/**
 * 
 */
package com.denverairport.baggage.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Scanner;

import com.denverairport.baggage.model.Baggage;
import com.denverairport.baggage.model.Flight;

/**
 * @author anirvanroy
 *
 */
public class BaggageProcessor {
	
	private ConveyorGraph<String> graph;
	private Map<String, Baggage> bags;
	private Map<String, Flight> departureMap;
	 
	 public void init(String file) throws FileNotFoundException {
		 Scanner in = new Scanner(new FileInputStream(file));

	        while (in.hasNext()) {
	            String input = in.next();
	            parseInput(input);
	        }
	 }
	 
	 public void parseInput(String line) {
		 
	 }

}
