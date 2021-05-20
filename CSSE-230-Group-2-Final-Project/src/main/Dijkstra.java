package main;

import java.util.*;
import main.Graph.*;

public class Dijkstra {
	
	public Dijkstra() {
		
	}
	/**
	 * This method generates a shortest path "tree" from a given graph and a given source node.
	 * We first initialize a new graph called tree to become our shortest path tree. The we 
	 * initialize an integer index to go through all nodes in the graph. We then create a 
	 * hashmap called distanceTracker to keep track of the changes in distance. The arraylist
	 * visitedNodes keeps track of nodes that we have already visited so we don't redundantly
	 * perform tasks on it.
	 * 
	 * The next step is to set the source node's parent to null, source node's distance to 0,
	 * add the source node to visitedNodes and put the source node into distanceTracker with a
	 * distance of 0.
	 * 
	 * The while loop happens when index less than or equal to all nodes in graph and not 
	 * visitedNodes.size(). Most of the while loop is taken up by the for loop which loops 
	 * through all adjacent nodes of visitedNodes.get(index) where index is incremented at the
	 * end of each while loop. We then set the Node current to one of the nodes in the
	 * adjacentNodes list. We then check if we have already worked on current. 
	 * 
	 * If no, then we calculate the distance between the parent node and the current node and 
	 * add the parent node's distance to the sum. This is current's distance from the source.
	 * We then add current's parent to the new graph's node list, add current to the
	 * adjacentNodes list of current's parent, and set current.parent to current's parent and 
	 * then add current to the new graph's node list.
	 * 
	 * If yes, then we check the distance between the current node and this hypothetical new 
	 * parent and add the hypothetical parent's distance. If this sum is greater than the old 
	 * distance value, we set current's distance back to what it was and do nothing more. If
	 * the sum is less than the old sum, we remove current from current.parent.adjacentNodes
	 * and add current to the hypothetical parent's adjacentNodes list. We then set current's
	 * parent to the hypothetical parent. Lastly we update the distanceTracker.
	 * 
	 * At the end each node can have many children but only one parent. This one parent
	 * property is what helps us determine the path later on.
	 * @param graph
	 * @param sourceVertex
	 * @return
	 */
	public Graph shortestPathTree(Graph graph, Node sourceVertex) {
		Graph tree = new Graph();
		int index = 0;
		HashMap<Graph.Node, Float> distanceTracker = new HashMap<Graph.Node, Float>();
		ArrayList<Graph.Node> visitedNodes = new ArrayList<Graph.Node>();

		sourceVertex.parent = null;
		sourceVertex.distance = 0;
		visitedNodes.add(sourceVertex);
		distanceTracker.put(sourceVertex, 0f);

		while (index <= graph.nodes.size()) {
			for (int i = 0; i < visitedNodes.get(index).adjacentNodes.size(); i++) {
				Node current = visitedNodes.get(index).adjacentNodes.get(i);
				if (!distanceTracker.containsKey(current)) {
					current.distance = graph.distBetweenNodes(visitedNodes.get(index), current)
							+ visitedNodes.get(index).distance;
					visitedNodes.add(current);
					distanceTracker.put(current, current.distance);
					tree.nodes.add(visitedNodes.get(index));
					tree.nodes.get(index).adjacentNodes.add(current);
					current.parent = tree.nodes.get(index);
					tree.nodes.add(current);
				} else {
					current.distance = graph.distBetweenNodes(visitedNodes.get(index), current)
							+ visitedNodes.get(index).distance;
					if (current.distance < distanceTracker.get(current)) {
						distanceTracker.put(current, current.distance);
						tree.nodes.get(tree.nodes.indexOf(current)).parent.adjacentNodes.remove(current);
						tree.nodes.get(tree.nodes.indexOf(visitedNodes.get(index))).adjacentNodes.add(current);
						tree.nodes.get(tree.nodes.indexOf(current)).parent = tree.nodes.get(tree.nodes.indexOf(visitedNodes.get(index)));
					} else {
						tree.nodes.get(tree.nodes.indexOf(current)).distance = distanceTracker.get(current);
					}
				}
			}
			index++;
		}
		
		return tree;
	}
	/**
	 * Since all nodes in the sPT have a parent pointer that points a direct path to the source
	 * node, all we have to do is go from destination node to parent to parent... and store the
	 * nodes in a stack. Then pop the stack into an arraylist.
	 * @param destination
	 * @return
	 */
	public ArrayList<Node> generatePath(Graph sPT, Node destination){
		ArrayList<Node> path = new ArrayList<Node>();
		Stack<Node> stack = new Stack<Node>();
		Node current = sPT.nodes.get(sPT.nodes.indexOf(destination));
		stack.push(current);
		while (current.parent != null) {
			stack.push(current.parent);
			current = current.parent;
		}
		while (!stack.isEmpty()) {
			path.add(stack.pop());
		}
		return path;
	}
	/**
	 * Since the sPT graph contains a field of all nodes in the graph and each node has a field 
	 * which stores the distance-from-source-node value. If the node.distance value is lesser or
	 * equal to the given max distance, the node is added to the returned arraylist.
	 * @param sPT
	 * @param distance
	 * @return
	 */
	public ArrayList<Node> possibleDestinationsByDistance(Graph sPT, float distance){
		ArrayList<Node> list = new ArrayList<Node>();
		while (sPT.nodes.size() != list.size()) {
			for (int i=0; i<sPT.nodes.size(); i++) {
				Node current = sPT.nodes.get(i);
				if (current.distance <= distance) {
					list.add(current);
				}
			}
		}
		return list;
	}
	/**
	 * Similar to the possibleDestinationsByDistance method except a time is passed in. We then
	 * multiply the time by the average cruise speed of a 737 in km/h which is 852.952 km/h to
	 * get the max distance.
	 * @param sPT
	 * @param hours
	 * @return
	 */
	public ArrayList<Node> possibleDestinationsByTime(Graph sPT, float hours){
		float distance = (float) (hours * 852.952);
		return possibleDestinationsByDistance(sPT, distance);
	}
}
