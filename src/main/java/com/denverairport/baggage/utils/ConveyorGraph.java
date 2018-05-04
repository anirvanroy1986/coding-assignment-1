/**
 * 
 */
package com.denverairport.baggage.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.denverairport.baggage.model.ConveyorRoute;
import com.denverairport.baggage.model.Path;
import com.denverairport.baggage.model.ConveyorNode;

/**
 * @author anirvanroy
 *
 */
public class ConveyorGraph<T> {
	
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	
	//Nodes represented by Adjacency List
	private Map<String, Set<ConveyorRoute<T>>> adjacencyList;
	private Set<ConveyorNode<T>> nodes = new HashSet<>();
	
	public ConveyorGraph() {
		this.adjacencyList = new HashMap<>();
	}
	
	/**
     * Add new vertex to the graph.
     * 
     * @param v The vertex object. 
     */
    public void addVertex(ConveyorNode<T> v) {
        if (!this.adjacencyList.containsKey(v.getNodeName())) {
        		this.adjacencyList.put((String) v.getNodeName(), new HashSet<ConveyorRoute<T>>());
        		nodes.add(v);
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
        if (!adjacencyList.containsKey(start.getNodeName())) {
        		addVertex(start);
        }
        
        if(!adjacencyList.containsKey(dest.getNodeName())) {
        		addVertex(dest);
        }
        	adjacencyList.get(start.getNodeName()).add(new ConveyorRoute<T>(start, dest, distance));
     }
    
    
    /**
     * Returns all available nodes present in the Graph. Returns a Deep Copy
     * @return
     */
    public Set<ConveyorNode<T>> getNodes(){
    		Set<ConveyorNode<T>> nodeSet = new HashSet<>();
    		for(ConveyorNode<T> node : nodes) {
    			nodeSet.add(new ConveyorNode<>(node.getNodeName()));
    		}
    		return nodeSet;
    }
    
    /**
     * Returns all available edges present for nodes in the Graph
     * @return
     */
    public Set<ConveyorRoute<T>> getEdges(){
    		Set<ConveyorRoute<T>> edges = new HashSet<>();
    		adjacencyList.entrySet().stream().forEachOrdered((entry) -> {
    			edges.addAll(entry.getValue());
    		});
    		return edges;
    }
    
    /**
     * Used to return a Deep copy of the Adjacency List of the Undirected Graph, so 
     * that dijkstras could be applied between source and destination nodes without any
     * issue 
     * @return
     */
    @SuppressWarnings("unchecked")
	public Map<String, Set<ConveyorRoute<T>>> copyAdjacencyList(){
    	
    		Map<String, Set<ConveyorRoute<T>>> newMap = new LinkedHashMap<>();
    		for (Map.Entry<String, Set<ConveyorRoute<T>>> entry : adjacencyList.entrySet())
    		{
    			Set<ConveyorRoute<T>> set = new HashSet<>();
    			for(ConveyorRoute<T> route : entry.getValue()) {
    				set.add(new ConveyorRoute<T>(new ConveyorNode<T>
    						(route.getFrom().getNodeName()), new ConveyorNode<T>(route.getTo().getNodeName()), 
    						route.getDistance()));
    			}
    		    newMap.put(entry.getKey(), set);
    			
    		}
    		return newMap;
    	
    }
    
    
    /**
     * Run Dijkstras between Source and Destination
     * Algorithm referred from Stack Overflow, Medium
     * @param source
     * @param target
     * @return
     */
    public List<ConveyorNode<T>> findShortestPath(ConveyorNode<T> source, ConveyorNode<T> target) {
    	LOGGER.info("Running Dijkstras between source node "+source.getNodeName()
    	+" and destination node "+target.getNodeName());
        List<ConveyorNode<T>> shortestPath = new ArrayList<>();
        try {
        	source.setMinDistance(0D);

            PriorityQueue<ConveyorNode<T>> vertexQueue = new PriorityQueue<>();

            for (ConveyorNode<T> vertex : getNodes()) {
                if (!vertex.getNodeName().equals(source.getNodeName())) {
                    vertex.setMinDistance(Double.POSITIVE_INFINITY);
                    vertex.setPrevious(null);
                } else {
                    vertex = source;
                }
                vertexQueue.add(vertex);
            }
            
            Map<String, Set<ConveyorRoute<T>>> listMap = copyAdjacencyList();

            while (!vertexQueue.isEmpty()) {
                ConveyorNode<T> node = vertexQueue.poll();

                if (node.equals(target)) {
                    while (node.getPrevious() != null) {
                        shortestPath.add(node);
                        node = node.getPrevious();
                    }
                    break;
                }

                vertexQueue.remove(node);
               
                Set<ConveyorRoute<T>> edges = listMap.get(node.getNodeName());
                if(edges != null) {
	                	for (ConveyorRoute<T> edge : edges) {
	                        ConveyorNode<T> v = edge.getTo();
	
	                        double weight = edge.getDistance();
	                        double distanceThroughU = node.getMinDistance()  + weight;
	
	                        if (distanceThroughU < (v.getMinDistance())) {
	                        	   vertexQueue.remove(v);
	                            v.setMinDistance(distanceThroughU);
	                            v.setPrevious(node);
	                            vertexQueue.add(v);
	                        }
	                    }
                }
                
            }

        }catch(Exception e) {
        	LOGGER.error("Exception Occurred while calculating Shortest path between nodes: "+e.getMessage());
        }
        
        Collections.reverse(shortestPath);
       return shortestPath;
    }
    
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        for (String node : adjacencyList.keySet()) {
            sb.append("\n    " + node+ " -> " + adjacencyList.get(node));
        }
        return sb.toString();
    }

}
