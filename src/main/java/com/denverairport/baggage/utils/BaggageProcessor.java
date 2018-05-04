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

import org.springframework.stereotype.Service;

import com.denverairport.baggage.constants.Constants;
import com.denverairport.baggage.model.Baggage;
import com.denverairport.baggage.model.ConveyorNode;
import com.denverairport.baggage.model.Flight;

/**
 * @author anirvanroy
 *
 */

@Service
public class BaggageProcessor {

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
		Scanner in = null;
		try {
			in = new Scanner(new FileInputStream(file));
			String currentInputSection = "";

			while (in.hasNextLine()) {
				String input = in.nextLine();
				if (input.startsWith(Constants.INPUT_CONVEYOR_SECTION)) {
					currentInputSection = "conveyor";
				} else if (input.startsWith(Constants.INPUT_DEPATURE_SECTION)) {

					currentInputSection = "departures";

				} else if (input.startsWith(Constants.INPUT_BAGS_SECTIOn)) {

					currentInputSection = "bags";

				}

				if (currentInputSection.equals("conveyor") || currentInputSection.equalsIgnoreCase("")) {
					currentInputSection = "conveyor";
					parseConveyorInput(input);
				} else if (currentInputSection.equals("departures") || currentInputSection.equalsIgnoreCase("")) {
					currentInputSection = "departures";
					parseDepartureInput(input);
				} else if (currentInputSection.equals("bags") || currentInputSection.equalsIgnoreCase("")) {
					currentInputSection = "bags";
					parseBaggageInput(input);
				}

			}
		} catch (IOException ioE) {
			System.out.println("Exception Occurred while parsing input file: " + ioE.getMessage());
		} finally {
			in.close();
		}

	}

	/**
	 * Parses input file and creates a Conveyor map
	 * 
	 * @param line
	 */
	public void parseConveyorInput(String line) {
		if (graph == null) {
			graph = new ConveyorGraph<>();
		} else {
			String[] lineInputArray = line.split(" ");
			if (lineInputArray.length != 3) {
				throw new IllegalArgumentException("Invalid Input format provided for Conveyor section");
			} else {
				ConveyorNode<String> sourceNode = new ConveyorNode<>(lineInputArray[0]);
				ConveyorNode<String> destNode = new ConveyorNode<>(lineInputArray[1]);
				//sourceNode.setPrevious(destNode);
				graph.addVertex(sourceNode);
				graph.addEdge(sourceNode, destNode, Integer.parseInt(lineInputArray[2]));
				graph.addVertex(destNode);
				graph.addEdge(destNode, sourceNode, Integer.parseInt(lineInputArray[2]));

			}
		}

	}

	/**
	 * Parses input file and creates a Departures Map
	 * 
	 * @param line
	 */
	public void parseDepartureInput(String line) {
		if (departureMap == null) {
			departureMap = new HashMap<>();
		} else {
			String[] lineInputArray = line.split(" ");
			if (lineInputArray.length != 4) {
				throw new IllegalArgumentException("Invalid Input format provided for Departure section");
			} else {
				Flight flight = new Flight(lineInputArray[0], lineInputArray[1], lineInputArray[2], lineInputArray[3]);
				departureMap.put(flight.getFlightNumber(), flight);

			}
		}

	}

	/**
	 * Parses input file and creates a Baggage map
	 * 
	 * @param line
	 */
	public void parseBaggageInput(String line) {
		if (bags == null) {
			bags = new LinkedHashMap<>();
		} else {
			String[] lineInputArray = line.split(" ");
			if (lineInputArray.length != 3) {
				throw new IllegalArgumentException("Invalid Input format provided for Departure section");
			} else {
				Baggage baggage = new Baggage(lineInputArray[0], lineInputArray[1], lineInputArray[2]);
				bags.put(baggage.getBaggageId(), baggage);

			}
		}

	}

}
