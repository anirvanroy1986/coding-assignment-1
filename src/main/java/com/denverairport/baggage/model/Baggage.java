/**
 * 
 */
package com.denverairport.baggage.model;

/**
 * @author anirvanroy
 *
 */
public class Baggage {
	
	private String baggageId;
	private String sourceGate;
	private String destination;
	
	public Baggage(String id, String sourceGate, String dest) {
		this.baggageId = id;
		this.sourceGate = sourceGate;
		this.destination = dest;
	}
	
	/**
	 * @return the baggageId
	 */
	public String getBaggageId() {
		return baggageId;
	}
	/**
	 * @param baggageId the baggageId to set
	 */
	public void setBaggageId(String baggageId) {
		this.baggageId = baggageId;
	}
	/**
	 * @return the sourceGate
	 */
	public String getSourceGate() {
		return sourceGate;
	}
	/**
	 * @param sourceGate the sourceGate to set
	 */
	public void setSourceGate(String sourceGate) {
		this.sourceGate = sourceGate;
	}
	/**
	 * @return the destination
	 */
	public String getDestination() {
		return destination;
	}
	/**
	 * @param destination the destination to set
	 */
	public void setDestination(String destination) {
		this.destination = destination;
	}

}
