/**
 * 
 */
package com.denverairport.baggage.service.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.denverairport.baggage.constants.Constants;
import com.denverairport.baggage.model.Baggage;
import com.denverairport.baggage.model.BaggageRoute;
import com.denverairport.baggage.model.ConveyorNode;
import com.denverairport.baggage.model.Flight;
import com.denverairport.baggage.model.Path;
import com.denverairport.baggage.service.BaggageService;
import com.denverairport.baggage.utils.BaggageProcessor;
import com.denverairport.baggage.utils.ConveyorGraph;

/**
 * @author anirvanroy
 *
 */

@Service
public class BaggageServiceImpl implements BaggageService {

	@Override
	public List<BaggageRoute> getBaggageRoutes(BaggageProcessor processor) {

		Map<String, Baggage> baggageMap = processor.getBags();
		ConveyorGraph<String> inputGraph = processor.getGraph();
		Map<String, Flight> flightMap = processor.getDepartureMap();
		List<BaggageRoute> baggageRouteList = new ArrayList<>();

		baggageMap.entrySet().stream().forEachOrdered((entry) -> {

			Baggage bagsInfo = entry.getValue();

			// Create a Source node
			ConveyorNode<String> sourceNode = new ConveyorNode<String>(bagsInfo.getSourceGate());
			// Create a Destination node
			ConveyorNode<String> destNode = null;
			if (bagsInfo.getDestination().equals(Constants.ARRIVAL)) {
				destNode = new ConveyorNode<String>(Constants.BAGGAGE_CLAIM);
			} else {
				destNode = new ConveyorNode<String>(flightMap.get(bagsInfo.getDestination()).getDepartureGate());
			}
			//Run Dijkstras to compute paths and sum of distances
			List<ConveyorNode<String>> nodeList = inputGraph.findShortestPath(sourceNode, destNode);
			BaggageRoute route = getPath(nodeList, bagsInfo.getBaggageId(), sourceNode.getNodeName());

			baggageRouteList.add(route);

		});
		writeFile(baggageRouteList);
		return baggageRouteList;
	}

	/**
	 * Creates a Path object which contains list of nodes between source and target.
	 * Include Target node
	 * 
	 * @param nodeList
	 * @param baggageId
	 * @param nodeName
	 * @return
	 */
	public BaggageRoute getPath(List<ConveyorNode<String>> nodeList, String baggageId, String nodeName) {
		BaggageRoute route = new BaggageRoute();
		Path<String> path = new Path<>();
		if (!nodeList.isEmpty()) {

			ConveyorNode<String> prevNode = nodeList.get(0);
			path.getNodes().add(prevNode);
			for (int i = 1; i < nodeList.size(); i++) {
				ConveyorNode<String> current = nodeList.get(i);
				prevNode = current;
				path.getNodes().add(current);
			}
			path.setDistance(prevNode.getMinDistance().intValue());
			path.setSourceNode(nodeName);
		}
		route.setPath(path);
		route.setBaggageId(baggageId);
		return route;
	}

	public void writeFile(List<BaggageRoute> routeList) {
		// Write to a BaggageRouteOutputFile
		File file = new File("BaggageRouteOutput.txt");
		BufferedWriter bw = null;
		try {
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			bw = new BufferedWriter(fw);
			for (BaggageRoute route : routeList) {
				bw.write(route.toString());
				bw.write(System.lineSeparator());
			}
		} catch (IOException ioE) {
			System.out.println("Exception occurred while writing to a file");
		} finally {
			try {
				bw.close();
			} catch (IOException e) {
				System.out.println("Exception occurred while closing Buffered Writer");
			}
		}

	}

}
