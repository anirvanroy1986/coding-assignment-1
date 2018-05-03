/**
 * 
 */
package com.denverairport.baggage.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.denverairport.baggage.constants.Constants;
import com.denverairport.baggage.model.Baggage;
import com.denverairport.baggage.model.ConveyorNode;
import com.denverairport.baggage.model.Flight;

/**
 * @author anirvanroy
 *
 */
public class BaggageProcessor {
	
	private ConveyorGraph<String> graph = null;
	private Map<String, Baggage> bags;
	private Map<String, Flight> departureMap;
	 
	/**
	 * Parses the input file and creates undirected weighted Graph, Departure Map and
	 * Baggage map
	 * @param file
	 * @throws FileNotFoundException
	 */
	 public void init(String file) throws FileNotFoundException {
		 Scanner in = null;
		 try {
			 in = new Scanner(new FileInputStream(file));
			 String currentInputSection="";

		        while (in.hasNext()) {
		            String input = in.next();
		            if(input.startsWith(Constants.INPUT_CONVEYOR_SECTION) || 
		            		currentInputSection.equals("conveyor")) {
		            		currentInputSection = "conveyor";
		            		parseConveyorInput(input);
		   		 	}else if(input.startsWith(Constants.INPUT_DEPATURE_SECTION)) {
		   		 		currentInputSection = "departures";
		   		 	}else if(input.startsWith(Constants.INPUT_BAGS_SECTIOn)) {
		   		 		currentInputSection = "bags";
		   		 	}
		            
		        }
		 }catch(IOException ioE) {
			 System.out.println("Exception Occurred while parsing input file: "+ioE.getMessage());
		 }finally {
			 in.close();
		 }
		 
	 }
	 
	 /**
	  * Parses input file and creates a Conveyor map
	  * @param line
	  */
	 public void parseConveyorInput(String line) {
		 if(graph == null) {
			 graph = new ConveyorGraph<>();
		 }else {
			 String[] lineInputArray = line.split(" ");
			 if(lineInputArray.length != 3) {
				 throw new IllegalArgumentException("Invalid Input format provided for Conveyor section");
			 }else {
				 ConveyorNode<String> sourceNode = new ConveyorNode<>(lineInputArray[0]);
				 ConveyorNode<String> destNode = new ConveyorNode<>(lineInputArray[1]);
				 
				 graph.addVertex(sourceNode);
				 graph.addEdge(sourceNode, destNode, Integer.parseInt(lineInputArray[2])); 
				 
				 
			 }
		 }
		 
	 }
	 
	 /**
	  * Parses input file and creates a Departures Map
	  * @param line
	  */
	 public void parseDepartureInput(String line) {
		 if(departureMap == null) {
			 departureMap = new HashMap<>();
		 }else {
			 String[] lineInputArray = line.split(" ");
			 if(lineInputArray.length != 4) {
				 throw new IllegalArgumentException("Invalid Input format provided for Departure section");
			 }else {
				 Flight flight = new Flight(lineInputArray[0], lineInputArray[1], lineInputArray[2], lineInputArray[3]); 
				 departureMap.put(flight.getFlightNumber(), flight);
				 
			 }
		 }
		 
	 }
	 
	 /**
	  * Parses input file and creates a Baggage map
	  * @param line
	  */
	 public void parseBaggageInput(String line) {
		 if(bags == null) {
			 bags = new HashMap<>();
		 }else {
			 String[] lineInputArray = line.split(" ");
			 if(lineInputArray.length != 3) {
				 throw new IllegalArgumentException("Invalid Input format provided for Departure section");
			 }else {
				 Baggage baggage = new Baggage(lineInputArray[0], lineInputArray[1], lineInputArray[2]); 
				 bags.put(baggage.getBaggageId(), baggage);
				 
			 }
		 }
		 
	 }

}
