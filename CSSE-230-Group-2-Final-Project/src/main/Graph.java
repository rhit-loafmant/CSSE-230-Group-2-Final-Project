package main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.HashMap;

public class Graph {
	public ArrayList<Node> nodes;
	public int size;
	private float maxNodeDist = 500f;

	public Graph() {
		this.nodes = new ArrayList<Node>();
	}

	public boolean addNode(String name, float latitude, float longitude) {
		Node newNode = new Node(name, latitude, longitude);
		for (int i = 0; i < nodes.size(); i++) {
			Node tempNode = nodes.get(i);
			float distance = distBetweenNodes(newNode, tempNode);
			if (distance < maxNodeDist) {
				newNode.adjacentNodes.add(tempNode);// not done
				tempNode.adjacentNodes.add(newNode);
				newNode.adjNodeDistances.put(tempNode, distance);
				tempNode.adjNodeDistances.put(newNode, distance);
			}
		}
		nodes.add(newNode);
		size++;
		return true;
	}
	
	public boolean addNode(Node newNode) {
		for (int i = 0; i < nodes.size(); i++) {
			Node tempNode = nodes.get(i);
			float distance = distBetweenNodes(newNode, tempNode);
			if (distance < maxNodeDist) {
				newNode.adjacentNodes.add(tempNode);// not done
				tempNode.adjacentNodes.add(newNode);
				newNode.adjNodeDistances.put(tempNode, distance);
				tempNode.adjNodeDistances.put(newNode, distance);
			}
		}
		nodes.add(newNode);
		size++;
		return true;
	}

	public float distBetweenNodes(Node nodeA, Node nodeB) {
		float latitude1 = nodeA.latitude;
		float longitude1 = nodeA.longitude;
		float latitude2 = nodeB.latitude;
		float longitude2 = nodeB.longitude;
		float radius = 6371f;
		float dLat = (float) Math.toRadians(latitude2 - latitude1);
		float dLon = (float) Math.toRadians(longitude2 - longitude1);
		float a = (float) (Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(Math.toRadians(latitude1))
				* Math.cos(Math.toRadians(latitude2)) * Math.sin(dLon / 2) * Math.sin(dLon / 2));
		float c = (float) (2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a)));
		float distance = radius * c;
		return distance;
	}
	
	public void printAllAdjNodes(Node n) {
		for(int i=0; i<n.adjacentNodes.size(); i++) {
			System.out.println(n.adjacentNodes.get(i).name + ", Distance: " + n.adjNodeDistances.get(n.adjacentNodes.get(i)) + " km");
		}
	}

	public class Node {
		public String name;
		public float longitude;
		public float latitude;
		public float distance;
		public Node parent;
		public ArrayList<Node> childNodes;
		public ArrayList<Node> adjacentNodes;
		public HashMap<Node,Float> adjNodeDistances;

		public Node(String name, float latitude, float longitude) {
			this.childNodes = new ArrayList<Node>();
			this.parent = null;
			this.name = name;
			this.longitude = longitude;
			this.latitude = latitude;
			this.adjacentNodes = new ArrayList<Node>();
			this.adjNodeDistances = new HashMap<Node,Float>();
			this.distance = Integer.MAX_VALUE;
		}
		//latitude = y, longitude = x
		public void drawNode(Graphics2D g2d, Color color, int radius, int x, int y) {
			Ellipse2D.Double nodeLocation = new Ellipse2D.Double(x, y, radius, radius);
			g2d.setColor(color);
//			g2d.translate(radius/2, radius/2);
			g2d.fill(nodeLocation);
		}
		public void drawLines(Graphics2D g2d, Color color, int x1, int y1, int x2, int y2) {
			
		}
		
	}
}

