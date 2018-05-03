/**
 * 
 */
package com.denverairport.baggage.service;

import java.util.List;

import com.denverairport.baggage.model.BaggageRoute;

/**
 * @author anirvanroy
 *
 */
public interface BaggageService {
	
	/**
	 * Method to return a list of optimal route for Baggages to be handled
	 * @return
	 */
	public List<BaggageRoute> getBaggageRoutes();

}
