/**
 * 
 */
package com.denverairport.baggage.service;

import java.util.List;

import com.denverairport.baggage.model.BaggageRoute;
import com.denverairport.baggage.utils.BaggageProcessor;

/**
 * @author anirvanroy
 *
 */
public interface BaggageService {
	
	/**
	 * Method to process list of Bags by referring to a Undirected weighted Graph
	 * and calculate the optimum route between Airport gates
	 */
	public List<BaggageRoute> getBaggageRoutes(BaggageProcessor processor);

}
