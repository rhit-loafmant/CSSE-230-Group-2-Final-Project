package main;

import java.util.*;
import main.Graph.*;

public class Dijkstra {
	public void shortestPathTree(Graph graph, Node sourceVertex) {
		int index = 0;
//		PriorityQueue<Node> priorityQ = new PriorityQueue<Node>();
		HashMap<Node, Float> sPT = new HashMap<Node, Float>();
		ArrayList<Node> visitedNodes = new ArrayList<Node>();

		sourceVertex.parent = null;
		sourceVertex.distance = 0;
		visitedNodes.add(sourceVertex);
		sPT.put(sourceVertex, 0f);

		while (sPT.size() < graph.nodes.size()) {
			for (int i = 0; i < visitedNodes.get(index).adjacentNodes.size(); i++) {
				Node current = visitedNodes.get(index).adjacentNodes.get(i);
				if (!sPT.containsKey(current)) {
					current.distance = graph.distBetweenNodes(visitedNodes.get(index), current)
							+ visitedNodes.get(index).distance;
					visitedNodes.add(current);
					sPT.put(current, current.distance);
					visitedNodes.get(index).childNodes.add(current);
					current.parent = visitedNodes.get(index);
				} else {
					current.distance = graph.distBetweenNodes(visitedNodes.get(index), current)
							+ visitedNodes.get(index).distance;
					if (current.distance < sPT.get(current)) {
						sPT.put(current, current.distance);
						current.parent.childNodes.remove(current);
						visitedNodes.get(index).childNodes.add(current);
						current.parent = visitedNodes.get(index);
					} else {
						current.distance = sPT.get(current);
					}
				}
			}
			index++;
		}

//		while (!sPT.isEmpty()) {
//			priorityQ.add(visitedNodes.get(0));
//			sPT.remove(0);
//		}
//		return priorityQ;
//		return null;
	}
}
