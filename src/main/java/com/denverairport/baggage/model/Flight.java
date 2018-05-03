/**
 * 
 */
package com.denverairport.baggage.model;

/**
 * @author anirvanroy
 *
 */
public class Flight {
	
	private String flightNumber;
	private String departureGate;
	private String destination;
	private String time;
	/**
	 * @return the flightNumber
	 */
	public String getFlightNumber() {
		return flightNumber;
	}
	/**
	 * @param flightNumber the flightNumber to set
	 */
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}
	/**
	 * @return the departureGate
	 */
	public String getDepartureGate() {
		return departureGate;
	}
	/**
	 * @param departureGate the departureGate to set
	 */
	public void setDepartureGate(String departureGate) {
		this.departureGate = departureGate;
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
	/**
	 * @return the time
	 */
	public String getTime() {
		return time;
	}
	/**
	 * @param time the time to set
	 */
	public void setTime(String time) {
		this.time = time;
	}

}
