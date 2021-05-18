package main;

import java.awt.BorderLayout;
import java.util.HashMap;
import java.util.Set;
import javax.swing.*;

import main.Graph.Node;

public class Main {
	public static final int WINDOW_WIDTH = 600;
	public static final int WINDOW_HEIGHT = 600;

	public static void main(String[] args) {
		new Main();
	}
	
	public Main() {
		Graph g = new Graph();
		g.addNode("A", 41, 100);
		g.addNode("B", 41, 95);
		g.addNode("C", 38, 93);
		g.addNode("D", 39, 98);
		g.addNode("E", 37, 101);
		g.addNode("F", 40, 91);
		
		g.addNode("G", 2, 40);
		g.addNode("lhkj", 98, 35);
		

				
		g.printAllAdjNodes(g.nodes.get(0));
		g.printAllAdjNodes(g.nodes.get(1));
		g.printAllAdjNodes(g.nodes.get(2));
		g.printAllAdjNodes(g.nodes.get(3));
		g.printAllAdjNodes(g.nodes.get(4));
		g.printAllAdjNodes(g.nodes.get(5));
		
		//Starting GUI
		JFrame mapFrame = new JFrame();
		mapFrame.setTitle("Airports of the World!");
		mapFrame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		mapFrame.add(new MapComponent(g.nodes), BorderLayout.CENTER);
		
		mapFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mapFrame.setVisible(true);
		
//		Dijkstra dij = new Dijkstra();
//		HashMap<Graph.Node, Float> spt = dij.shortestPathTree(g, g.nodes.get(4));
//		Set<Graph.Node> keyset = spt.keySet();
//		Graph.Node[] sptNodes = (Graph.Node[]) keyset.toArray();
//		System.out.println("SPT of graph g is: ");
//		for(int i=0; i<sptNodes.length; i++) {
//			System.out.println(sptNodes[i].name);
//		}
	}
	
}
