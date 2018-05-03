/**
 * 
 */
package com.denverairport.baggage.model;

import java.util.HashSet;
import java.util.Set;

/**
 * @author anirvanroy
 *
 */
public class Path<T> {
	

    private final String PATH_SEPARATOR = " ";
    private Set<ConveyorNode<T>> nodes;
    private int distance;

    /**
     * Creates a new empty {@code ConveyorPath}.
     */
    public Path() {
        this.nodes = new HashSet<>();
        this.distance = 0;
    }

    /**
     * Creates a new {@code ConveyorPath} with the provided nodes and length.
     *
     * @param nodes  {@code List} of nodes that make up the path.
     * @param length length of path.
     */
    public Path(Set<ConveyorNode<T>> nodes, int length) {
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
    public Set<ConveyorNode<T>> getNodes() {
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


}
