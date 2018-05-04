/**
 * 
 */
package com.denverairport.baggage.model;

import java.util.List;

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
	
	@Override
	public String toString() {
		StringBuilder output = new StringBuilder();
		List<ConveyorNode<String>> pathList = path.getNodes();
		
		if (!pathList.isEmpty()) {
    		output.append(this.baggageId + " ");
        output.append(path.getSourceNode() + " ");
        ConveyorNode<String> prevNode = pathList.get(0);
        output.append(prevNode.getNodeName() + " ");

        for (int i = 1; i < pathList.size(); i++) {
            ConveyorNode<String> current = pathList.get(i);
            prevNode = current;
            output.append(current.getNodeName() + " ");
        }
        output.append(": " + prevNode.getMinDistance());
        
		}
		
    return output.toString();
	}

}
