package main;

import java.util.ArrayList;

public class Graph {
	private ArrayList<Node> nodes;

	public Graph() {
		this.nodes = new ArrayList<Node>();
	}

	public boolean addNode(String name, float longitude, float latitude, ArrayList<Node> adjacentNodes) {
		Node n = new Node(name, longitude, latitude, adjacentNodes);
		nodes.add(n);
		for (int i = 0; i < adjacentNodes.size(); i++) {
			adjacentNodes.get(i).adjacentNodes.add(n);
		}
		return true;
	}

	public class Node {
		private String name;
		private float longitude;
		private float latitude;
		private ArrayList<Node> adjacentNodes;

		public Node(String name, float longitude, float latitude, ArrayList<Node> adjacentNodes) {
			this.name = name;
			this.longitude = longitude;
			this.latitude = latitude;
			this.adjacentNodes = new ArrayList<Node>();
		}
	}
}
