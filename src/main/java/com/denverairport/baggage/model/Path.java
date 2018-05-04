/**
 * 
 */
package com.denverairport.baggage.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author anirvanroy
 *
 */
public class Path<T> {
	

    private final String PATH_SEPARATOR = " ";
    private List<ConveyorNode<T>> nodes;
    private int distance;
    private String sourceNode;

    /**
     * Creates a new empty {@code ConveyorPath}.
     */
    public Path() {
        this.nodes = new ArrayList<>();
        this.distance = 0;
    }

    /**
     * Creates a new {@code ConveyorPath} with the provided nodes and length.
     *
     * @param nodes  {@code List} of nodes that make up the path.
     * @param length length of path.
     */
    public Path(List<ConveyorNode<T>> nodes, int length) {
        this.nodes = nodes;
        this.distance = length;
    }

   

    /**
     * Append node to the end of the path.
     *
     * @param node   node to append.
     * @param weight weight of edge that leads to the node.
     * @return true if this {@code ConveyorPath} changed as a result of the call. {@code false} otherwise.
     */
    public boolean appendNode(ConveyorNode<T> node, int weight) {
        distance += weight;
        return this.nodes.add(node);
    }

    /**
     * {@code List} of nodes.
     *
     * @return {@code List} of nodes.
     */
    public List<ConveyorNode<T>> getNodes() {
        return nodes;
    }

    
    /**
     * Total length of path. This is the sum of edge weights.
     *
     * @return the length of the path.
     */
    public int distance() {
        return distance;
    }

    

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(ConveyorNode<T> node : nodes) {
            sb.append(node.getNodeName() + PATH_SEPARATOR);
        }
        
        return sb.toString();
    }

	/**
	 * @return the distance
	 */
	public int getDistance() {
		return distance;
	}

	/**
	 * @param distance the distance to set
	 */
	public void setDistance(int distance) {
		this.distance = distance;
	}

	/**
	 * @param shortestPathList the nodes to set
	 */
	public void setNodes(List<ConveyorNode<T>> shortestPathList) {
		this.nodes = shortestPathList;
	}

	/**
	 * @return the sourceNode
	 */
	public String getSourceNode() {
		return sourceNode;
	}

	/**
	 * @param sourceNode the sourceNode to set
	 */
	public void setSourceNode(String sourceNode) {
		this.sourceNode = sourceNode;
	}


}
