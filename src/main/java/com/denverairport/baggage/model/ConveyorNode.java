/**
 * 
 */
package com.denverairport.baggage.model;

import java.util.HashMap;
import java.util.Map;

/**
 * @author anirvanroy
 *
 */
public class ConveyorNode<T> implements Comparable<ConveyorNode<T>>{
	
	 private T nodeName;
     private Integer distance;
     private ConveyorNode<T> previous;
     protected Map<T, Integer> neighbours;

     public ConveyorNode(T data) {
         this.nodeName = data;
         this.neighbours = new HashMap<T, Integer>();
     }

     public T getNodeName() {
         return nodeName;
     }

     public Map<T, Integer> getNeighbours() {
         return neighbours;
     }

     public int getDistance() {
         return distance;
     }

     public void setDistance(int distance) {
         this.distance = distance;
     }

     public ConveyorNode<T> getPrevious() {
         return previous;
     }

     public void setPrevious(ConveyorNode<T> previous) {
         this.previous = previous;
     }

     public int compareTo(ConveyorNode<T> other) {
         return distance.compareTo(other.getDistance());
     }

     @Override
     public String toString() {
         return nodeName.toString();
     }

	

}
