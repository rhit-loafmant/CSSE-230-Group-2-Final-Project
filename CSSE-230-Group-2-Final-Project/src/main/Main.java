package main;

public class Main {

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
		

				
		System.out.println("all nodes adjacent to A");
		g.printAllAdjNodes(g.nodes.get(0));
		System.out.println("all nodes adjacent to B");
		g.printAllAdjNodes(g.nodes.get(1));
		System.out.println("all nodes adjacent to C");
		g.printAllAdjNodes(g.nodes.get(2));
		System.out.println("all nodes adjacent to D");
		g.printAllAdjNodes(g.nodes.get(3));
		System.out.println("all nodes adjacent to E");
		g.printAllAdjNodes(g.nodes.get(4));
		System.out.println("all nodes adjacent to F");
		g.printAllAdjNodes(g.nodes.get(5));
	}
	
}
