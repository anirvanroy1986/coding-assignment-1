/**
 * 
 */
package com.denverairport.baggage.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.denverairport.baggage.model.Baggage;
import com.denverairport.baggage.model.BaggageRoute;
import com.denverairport.baggage.model.ConveyorNode;
import com.denverairport.baggage.model.Flight;
import com.denverairport.baggage.service.BaggageService;
import com.denverairport.baggage.utils.BaggageProcessor;
import com.denverairport.baggage.utils.ConveyorGraph;
import com.denverairport.baggage.utils.Dijkstras;

/**
 * @author anirvanroy
 *
 */

public class BaggageServiceImpl implements BaggageService{
	
	
	@Override
	public List<BaggageRoute> getBaggageRoutes(BaggageProcessor processor) {
		
		Map<String, Baggage> baggageMap = processor.getBags();
		ConveyorGraph<String> inputGraph = processor.getGraph();
		Map<String, Flight> flightMap = processor.getDepartureMap();

		
		baggageMap.entrySet().stream().forEachOrdered((entry) -> {
			
            String baggageId = entry.getKey();
            Baggage bagsInfo = entry.getValue();
            System.out.println("Processing Bag ID "+baggageId);
            //Create a Source node
            ConveyorNode<String> sourceNode = new ConveyorNode<String>(bagsInfo.getSourceGate());
            //Create a Destination node
            ConveyorNode<String> destNode = null;
            if(bagsInfo.getDestination().equals("ARRIVAL")) {
            		destNode = new ConveyorNode<String>("BaggageClaim");
            }else {
            		destNode = new ConveyorNode<String>(flightMap.get(bagsInfo.getDestination()).getDepartureGate());
            }
            
            /*Dijkstras<String> dikstra = new Dijkstras<String>(inputGraph);
            dikstra.execute(sourceNode);
            LinkedList<ConveyorNode<String>> path = dikstra.getPath(destNode);*/
            
            List<ConveyorNode<String>>nodeList = inputGraph.findShortestPath(sourceNode,destNode);
            StringBuilder output = new StringBuilder();
            if (!nodeList.isEmpty()) {
            		output.append(bagsInfo.getBaggageId() + " ");
                output.append(sourceNode.getNodeName() + " ");
                ConveyorNode<String> prevNode = nodeList.get(0);
                output.append(prevNode.getNodeName() + " ");

                for (int i = 1; i < nodeList.size(); i++) {
                    ConveyorNode<String> current = nodeList.get(i);
                    prevNode = current;
                    output.append(current.getNodeName() + " ");
                }
                output.append(": " + prevNode.getMinDistance());
                output.append(System.lineSeparator());
            }
            System.out.println(output.toString());
            
        });
		
		return null;
	}

}
