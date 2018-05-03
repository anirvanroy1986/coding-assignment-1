/**
 * 
 */
package com.denverairport.baggage.utils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.denverairport.baggage.model.ConveyorRoute;
import com.denverairport.baggage.model.ConveyorNode;

/**
 * @author anirvanroy
 *
 */
public class ConveyorGraph<T> {
	
	//Nodes represented by Adjacency List
	private Map<ConveyorNode<T>, Set<ConveyorRoute<T>>> adjacencyList;
	
	public ConveyorGraph() {
		this.adjacencyList = new HashMap<>();
	}
	
	/**
     * Add new vertex to the graph.
     * 
     * @param v The vertex object. 
     */
    public void addVertex(ConveyorNode<T> v) {
        if (!this.adjacencyList.containsKey(v)) {
        	this.adjacencyList.put(v, new HashSet<ConveyorRoute<T>>());
        }
        
    }
    
    /**
     * Adds an edge to the Graph vertex
     * @param start
     * @param dest
     * @param distance
     * @throws Exception
     */
    public void addEdge(ConveyorNode<T> start, ConveyorNode<T> dest, int distance) {
        if (!adjacencyList.containsKey(start)) {
        		addVertex(start);
        }
        	adjacencyList.get(start).add(new ConveyorRoute<T>(start, dest, distance));
     }
    
    /**
     * Checks if destination node is connected to the source node
     * @param start
     * @param dest
     * @return
     */
    public boolean isConnected(ConveyorNode<T> start, ConveyorNode<T> dest) {
    		Set<ConveyorRoute<T>> set = adjacencyList.get(start);
    		for(ConveyorRoute<T> route: set) {
    			if(route.getTo().getNodeName().equals(dest.getNodeName())) {
    				return true;
    			}
    		}
    		return false;
    }
    
    /**
     * Returns the distance between 2 nodes. If connecting node not found, 
     * returns -1
     * @param start
     * @param dest
     * @return
     */
    public int getDistanceBetweenNodes(ConveyorNode<T> start, ConveyorNode<T> dest) {
    	Set<ConveyorRoute<T>> set = adjacencyList.get(start);
		for(ConveyorRoute<T> route: set) {
			if(route.getTo().getNodeName().equals(dest.getNodeName())) {
				return route.getDistance();
			}
		}
		
		return -1;
    }

}
