/**
 * 
 */
package com.denverairport.baggage.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.denverairport.baggage.model.ConveyorNode;

/**
 * @author anirvanroy
 *
 */
public class ConveyorGraphTest {
	
	ConveyorGraph<String> graph;
	
	@Before
    public void initObjects() {
        graph = new ConveyorGraph<>();
        
        //Add test nodes
        ConveyorNode<String> nodeA = new ConveyorNode<>("A1");
        ConveyorNode<String> nodeB = new ConveyorNode<>("A2");
        ConveyorNode<String> nodeC = new ConveyorNode<>("A3");
        ConveyorNode<String> nodeD = new ConveyorNode<>("A4");
        graph.addVertex(nodeA);
        graph.addVertex(nodeB);
        graph.addVertex(nodeC);
        graph.addVertex(nodeD);
        
        //Add test edges
        graph.addEdge(nodeA, nodeB, 3);
        graph.addEdge(nodeB, nodeA, 3);
        
        graph.addEdge(nodeA, nodeC, 2);
        graph.addEdge(nodeC, nodeA, 2);
        
        graph.addEdge(nodeC, nodeD, 5);
        graph.addEdge(nodeD, nodeC, 5);

       
    }
	
	@Test
	public void testShortestPathNotEmpty() {
		ConveyorNode<String> nodeA = new ConveyorNode<>("A1");
		ConveyorNode<String> nodeD = new ConveyorNode<>("A4");
		List<ConveyorNode<String>> shortestPathList = graph.findShortestPath(nodeA, nodeD);
		assertTrue(!shortestPathList.isEmpty());
	}
	
	@Test
	public void testShortestPathListSize() {
		ConveyorNode<String> nodeA = new ConveyorNode<>("A1");
		ConveyorNode<String> nodeD = new ConveyorNode<>("A4");
		List<ConveyorNode<String>> shortestPathList = graph.findShortestPath(nodeA, nodeD);
		assertEquals(2, shortestPathList.size());
	}
	
	@Test
	public void testShortestPathListDistance() {
		ConveyorNode<String> nodeA = new ConveyorNode<>("A1");
		ConveyorNode<String> nodeD = new ConveyorNode<>("A4");
		List<ConveyorNode<String>> shortestPathList = graph.findShortestPath(nodeA, nodeD);
		
		StringBuilder output = new StringBuilder();
        if (!shortestPathList.isEmpty()) {
        		
            ConveyorNode<String> prevNode = shortestPathList.get(0);
            output.append(prevNode.getNodeName() + " ");

            for (int i = 1; i < shortestPathList.size(); i++) {
                ConveyorNode<String> current = shortestPathList.get(i);
                prevNode = current;
                output.append(current.getNodeName() + " ");
            }
            output.append(": " + prevNode.getMinDistance());
            output.append(System.lineSeparator());
        }
        System.out.println(output.toString());
		
		ConveyorNode<String> prevNode = shortestPathList.get(0);
		assertEquals(7, prevNode.getMinDistance().intValue());
	}

}
