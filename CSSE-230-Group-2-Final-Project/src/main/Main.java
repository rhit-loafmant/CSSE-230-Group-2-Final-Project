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
		
		Graph spt = getSPT(g, g.nodes.get(4));

				
//		System.out.println("all nodes adjacent to A");
//		g.printAllAdjNodes(g.nodes.get(0));
//		System.out.println("all nodes adjacent to B");
//		g.printAllAdjNodes(g.nodes.get(1));
//		System.out.println("all nodes adjacent to C");
//		g.printAllAdjNodes(g.nodes.get(2));
//		System.out.println("all nodes adjacent to D");
//		g.printAllAdjNodes(g.nodes.get(3));
//		System.out.println("all nodes adjacent to E");
//		g.printAllAdjNodes(g.nodes.get(4));
//		System.out.println("all nodes adjacent to F");
//		g.printAllAdjNodes(g.nodes.get(5));
	}
	
	public Graph getSPT(Graph g, Graph.Node rootNode) {
		Graph spt = new Graph();
		int i = 0;
		spt.addNode(rootNode);
		while(spt.size != g.size) {
			int shortestDist = Integer.MAX_VALUE;
			for(int k=0; k<spt.nodes.get(i).adjacentNodes.size(); i++) {
				Graph.Node temp = spt.nodes.get(i).adjacentNodes.get(k);
			}
		}
		return spt;
	}
	
}
