/**
 * 
 */
package com.denverairport.baggage.model;

/**
 * @author anirvanroy
 *
 */
public class BaggageRoute {
	
	private Path<String> path;
	private String baggageId;
	/**
	 * @return the path
	 */
	public Path<String> getPath() {
		return path;
	}
	/**
	 * @param path the path to set
	 */
	public void setPath(Path<String> path) {
		this.path = path;
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

}
