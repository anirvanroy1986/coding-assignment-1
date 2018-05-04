/**
 * 
 */
package com.denverairport.baggage.model;

/**
 * @author anirvanroy
 *
 */
public class ConveyorRoute<T> {
	
	private ConveyorNode<T> from;
	private ConveyorNode<T> to;
	private Integer distance;
	
	public ConveyorRoute(ConveyorNode<T> from, ConveyorNode<T> to, int distance) {
		this.from = from;
		this.to= to;
		this.distance = distance;		
	}

	/**
	 * @return the from
	 */
	public ConveyorNode<T> getFrom() {
		return from;
	}

	/**
	 * @param from the from to set
	 */
	public void setFrom(ConveyorNode<T> from) {
		this.from = from;
	}

	/**
	 * @return the to
	 */
	public ConveyorNode<T> getTo() {
		return to;
	}

	/**
	 * @param to the to to set
	 */
	public void setTo(ConveyorNode<T> to) {
		this.to = to;
	}

	/**
	 * @return the distance
	 */
	public Integer getDistance() {
		return distance;
	}

	/**
	 * @param distance the distance to set
	 */
	public void setDistance(Integer distance) {
		this.distance = distance;
	}
	
	@Override
    public String toString() {
        return "Route-----> <" + from.getNodeName() + "->" + to.getNodeName() + " : " + distance + ">";
    }
	

}
