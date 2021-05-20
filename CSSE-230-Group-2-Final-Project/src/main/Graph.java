package main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.HashMap;

public class Graph {
	public ArrayList<Node> nodes;
	public HashMap<String, Node> adjNodeDistances;

	public int size;
	private float maxNodeDist = 500f;

	public Graph() {
		this.nodes = new ArrayList<Node>();
	}

	public boolean addNode(String name, float latitude, float longitude, String country, String continent) {
		Node newNode = new Node(name, latitude, longitude, country, continent);
		for (Node n : this.nodes) {
			float distance = distBetweenNodes(newNode, n);
			if (distance <= maxNodeDist) {
				newNode.adjacentNodes.add(n);// not done
				n.adjacentNodes.add(newNode);
				newNode.adjNodeDistances.put(n, distance);
				n.adjNodeDistances.put(newNode, distance);
			}
		}
		nodes.add(newNode);
		size++;
		return true;
	}
	
	public boolean addNode(Node newNode) {
		for (Node n : this.nodes) {
			float distance = distBetweenNodes(newNode, n);
			if (distance <= maxNodeDist) {
				newNode.adjacentNodes.add(n);// not done
				n.adjacentNodes.add(newNode);
				newNode.adjNodeDistances.put(n, distance);
				n.adjNodeDistances.put(newNode, distance);
			}
		}
		nodes.add(newNode);
		size++;
		return true;
	}

	public float distBetweenNodes(Node nodeA, Node nodeB) {
		float latA = nodeA.latitude;
		float lonA = nodeA.longitude;
		float latB = nodeB.latitude;
		float lonB = nodeB.longitude;
		float radius = 6371f;
		float dLat = (float) Math.toRadians(latB - latA);
		float dLon = (float) Math.toRadians(lonB - lonA);
		float a = (float) (Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(Math.toRadians(latA))
				* Math.cos(Math.toRadians(latB)) * Math.sin(dLon / 2) * Math.sin(dLon / 2));
		float c = (float) (2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a)));
		float distance = radius * c;
		return distance;
	}
	
	public void printAllAdjNodes(Node n) {
		System.out.println("All nodes adjacent to " + n.name + ":");
		for(int i=0; i<n.adjacentNodes.size(); i++) {
			System.out.println(n.adjacentNodes.get(i).name + ", Distance: " + n.adjNodeDistances.get(n.adjacentNodes.get(i)) + " km");
		}
	}

	public class Node {
		public String name, country, continent;
		public float longitude, latitude, distance;
		public Node parent;
		public ArrayList<Node> adjacentNodes;
		public HashMap<Node,Float> adjNodeDistances;

		public Node(String name, float latitude, float longitude, String country, String continent) {
			this.parent = null;
			this.name = name;
			this.longitude = longitude;
			this.latitude = latitude;
			this.country = country;
			this.continent = continent;
			this.adjacentNodes = new ArrayList<Node>();
			this.adjNodeDistances = new HashMap<Node,Float>();
			this.distance = Integer.MAX_VALUE;
		}
		//latitude = y, longitude = x
		public void drawNode(Graphics2D g2d, Color color, int radius, int x, int y) {
			Ellipse2D.Double nodeLocation = new Ellipse2D.Double(x -radius/2, y-radius/2, radius, radius);
			g2d.setColor(color);
			g2d.fill(nodeLocation);
//			System.out.println("placed " + this.name+ "at " +x+", "+y);
		}
		public void drawLines(Graphics2D g2d, Color color, int x1, int y1, int x2, int y2) {
			g2d.setColor(color);
//			int x, y;
//			x = (x1+x2)/2;
//			y = (y1+y2)/2;
//			g2d.drawArc(x, y, (x*2)/(y*2), 10, 5, 170);
			g2d.drawLine(x1, y1, x2, y2);
			
		}
		
	}
}

