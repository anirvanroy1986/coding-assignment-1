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
     private Double minDistance = Double.POSITIVE_INFINITY;

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

     @Override
     public int compareTo(ConveyorNode<T> other) {
         return Double.compare(minDistance, other.minDistance);
     }

     @Override
     public String toString() {
         return nodeName.toString();
     }

	/**
	 * @return the minDistance
	 */
	public Double getMinDistance() {
		return minDistance;
	}

	/**
	 * @param minDistance the minDistance to set
	 */
	public void setMinDistance(Double minDistance) {
		this.minDistance = minDistance;
	}
	
	@Override
    public boolean equals(Object obj){
        if (this == obj){
            return true;
        }

        if (obj == null || !(obj instanceof ConveyorNode)){
            return false;
        }

        ConveyorNode<T> other = (ConveyorNode<T>) obj;

        return (this.nodeName.equals(other.nodeName));
    }
	
	@Override
    public int hashCode() {
        return nodeName.hashCode();
    }

	

}
