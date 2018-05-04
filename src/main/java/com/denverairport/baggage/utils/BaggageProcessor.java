/**
 * 
 */
package com.denverairport.baggage.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.denverairport.baggage.constants.Constants;
import com.denverairport.baggage.model.Baggage;
import com.denverairport.baggage.model.ConveyorNode;
import com.denverairport.baggage.model.Flight;

/**
 * BaggageProcessor is used to parse the input provided in the text format and create
 * 1) Undirected weighted Graph
 * 2) Departure Map, mapping Flight number to Flight object
 * 3) Baggage Map (The actual input for processing)
 * 
 * @author anirvanroy
 *
 */

@Service
public class BaggageProcessor {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	
	private ConveyorGraph<String> graph = null;
	private Map<String, Baggage> bags = null;
	private Map<String, Flight> departureMap = null;

	/**
	 * @return the graph
	 */
	public ConveyorGraph<String> getGraph() {
		return graph;
	}

	/**
	 * @param graph
	 *            the graph to set
	 */
	public void setGraph(ConveyorGraph<String> graph) {
		this.graph = graph;
	}

	/**
	 * @return the bags
	 */
	public Map<String, Baggage> getBags() {
		return bags;
	}

	/**
	 * @param bags
	 *            the bags to set
	 */
	public void setBags(Map<String, Baggage> bags) {
		this.bags = bags;
	}

	/**
	 * @return the departureMap
	 */
	public Map<String, Flight> getDepartureMap() {
		return departureMap;
	}

	/**
	 * @param departureMap
	 *            the departureMap to set
	 */
	public void setDepartureMap(Map<String, Flight> departureMap) {
		this.departureMap = departureMap;
	}

	/**
	 * Parses the input file and creates undirected weighted Graph, Departure Map
	 * and Baggage map
	 * 
	 * @param file
	 * @throws FileNotFoundException
	 */
	public void init(String file) throws FileNotFoundException {
		LOGGER.info("Parsing Input File**** Starts");
		Scanner in = null;
		try {
			in = new Scanner(new FileInputStream(file));
			String currentInputSection = "";
		
			while (in.hasNextLine()) {
				String input = in.nextLine();
				if (input.startsWith(Constants.INPUT_CONVEYOR_SECTION)) {
					currentInputSection = Constants.CONVEYOR;
				} else if (input.startsWith(Constants.INPUT_DEPATURE_SECTION)) {
					currentInputSection = Constants.DEPARTURES;
				} else if (input.startsWith(Constants.INPUT_BAGS_SECTIOn)) {
					currentInputSection = Constants.BAGS;
				}

				if (currentInputSection.equals(Constants.CONVEYOR) || currentInputSection.equalsIgnoreCase("")) {
					currentInputSection = Constants.CONVEYOR;
					parseConveyorInput(input);
				} else if (currentInputSection.equals(Constants.DEPARTURES) || currentInputSection.equalsIgnoreCase("")) {
					currentInputSection = Constants.DEPARTURES;
					parseDepartureInput(input);
				} else if (currentInputSection.equals(Constants.BAGS) || currentInputSection.equalsIgnoreCase("")) {
					currentInputSection = Constants.BAGS;
					parseBaggageInput(input);
				}

			}
		} catch (IOException ioE) {
			LOGGER.error("Exception Occurred while parsing input file: " + ioE.getMessage());
		} finally {
			in.close();
		}
		LOGGER.info("Parsing Input File**** Ends");

	}

	/**
	 * Parses input file and creates an UnDirected weighted Graph
	 * Format: <Node 1> <Node 2> <travel_time>
	 * @param line
	 */
	public void parseConveyorInput(String line) {
		if (graph == null) {
			graph = new ConveyorGraph<>();
		} else {
			String[] lineInputArray = line.split(" ");
			if (lineInputArray.length != 3) {
				LOGGER.error("Invalid Input format provided for Conveyor section ");
				throw new IllegalArgumentException("Invalid Input format provided for Conveyor section");
			} else {
				ConveyorNode<String> sourceNode = new ConveyorNode<>(lineInputArray[0]);
				ConveyorNode<String> destNode = new ConveyorNode<>(lineInputArray[1]);
				
				graph.addVertex(sourceNode);
				graph.addEdge(sourceNode, destNode, Integer.parseInt(lineInputArray[2]));
				//Add Bi-Directional relation
				graph.addVertex(destNode);
				graph.addEdge(destNode, sourceNode, Integer.parseInt(lineInputArray[2]));

			}
		}

	}

	/**
	 * Parses input file and creates a Departures Map. 
	 * Format: <flight_id> <flight_gate> <destination> <flight_time>
	 * @param line
	 */
	public void parseDepartureInput(String line) {
		if (departureMap == null) {
			departureMap = new HashMap<>();
		} else {
			String[] lineInputArray = line.split(" ");
			if (lineInputArray.length != 4) {
				LOGGER.error("Invalid Input format provided for Departure section ");
				throw new IllegalArgumentException("Invalid Input format provided for Departure section");
			} else {
				Flight flight = new Flight(lineInputArray[0], lineInputArray[1], lineInputArray[2], lineInputArray[3]);
				departureMap.put(flight.getFlightNumber(), flight);

			}
		}

	}

	/**
	 * Parses input file and creates a Baggage map
	 * Format: <bag_number> <entry_point> <flight_id>
	 * @param line
	 */
	public void parseBaggageInput(String line) {
		if (bags == null) {
			bags = new LinkedHashMap<>();
		} else {
			String[] lineInputArray = line.split(" ");
			if (lineInputArray.length != 3) {
				LOGGER.error("Invalid Input format provided for Bags section ");
				throw new IllegalArgumentException("Invalid Input format provided for Bags section");
			} else {
				Baggage baggage = new Baggage(lineInputArray[0], lineInputArray[1], lineInputArray[2]);
				bags.put(baggage.getBaggageId(), baggage);

			}
		}

	}

}
