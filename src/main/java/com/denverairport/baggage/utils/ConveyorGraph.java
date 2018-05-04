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

import com.denverairport.baggage.model.ConveyorRoute;
import com.denverairport.baggage.model.ConveyorNode;

/**
 * @author anirvanroy
 *
 */
public class ConveyorGraph<T> {
	
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
        	adjacencyList.get(start.getNodeName()).add(new ConveyorRoute<T>(start, dest, distance));
     }
    
    /**
     * Checks if destination node is connected to the source node
     * @param start
     * @param dest
     * @return
     */
    public boolean isConnected(ConveyorNode<T> start, ConveyorNode<T> dest) {
    		Set<ConveyorRoute<T>> set = adjacencyList.get(start.getNodeName());
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
    
    public Set<ConveyorNode<T>> getNodes(){
    		Set<ConveyorNode<T>> nodeSet = new HashSet<>();
    		for(ConveyorNode<T> node : nodes) {
    			nodeSet.add(new ConveyorNode<>(node.getNodeName()));
    		}
    		return nodeSet;
    }
    
    public Set<ConveyorRoute<T>> getEdges(){
    		Set<ConveyorRoute<T>> edges = new HashSet<>();
    		adjacencyList.entrySet().stream().forEachOrdered((entry) -> {
    			edges.addAll(entry.getValue());
    		});
    		return edges;
    }
    
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
    
    
    
    public List<ConveyorNode<T>> findShortestPath(ConveyorNode<T> source, ConveyorNode<T> target) {
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
                ConveyorNode<T> u = vertexQueue.poll();

                if (u.equals(target)) {
                    while (u.getPrevious() != null) {
                        shortestPath.add(u);
                        u = u.getPrevious();
                    }
                    break;
                }

                vertexQueue.remove(u);
                /*Set<ConveyorRoute<T>> edges = null;
                
                for (Map.Entry<ConveyorNode<T>,Set<ConveyorRoute<T>>> entry : adjacencyList.entrySet()) {
                		if(entry.getKey().getNodeName().equals(u.getNodeName())) {
                			 edges = entry.getValue();
                		}
                }*/
                
                
               
                Set<ConveyorRoute<T>> edges = listMap.get(u.getNodeName());
                if(edges != null) {
	                	for (ConveyorRoute<T> edge : edges) {
	                        ConveyorNode<T> v = edge.getTo();
	
	                        double weight = edge.getDistance();
	                        double distanceThroughU = u.getMinDistance()  + weight;
	
	                        if (distanceThroughU < (v.getMinDistance())) {
	                        	   //vertexQueue.remove(v);
	                            v.setMinDistance(distanceThroughU);
	                            v.setPrevious(u);
	                            vertexQueue.remove(v);
	                            vertexQueue.add(v);
	                        }
	                    }
                }
                
            }

        }catch(Exception e) {
        		e.printStackTrace();
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
