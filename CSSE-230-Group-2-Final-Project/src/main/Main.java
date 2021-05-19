package main;

import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.xml.parsers.DocumentBuilderFactory;  
import javax.xml.parsers.DocumentBuilder;  
import org.w3c.dom.Document;  
import org.w3c.dom.NodeList;  
import org.w3c.dom.Node;  
import org.w3c.dom.Element;  
import java.io.File;
import java.io.IOException;  

public class Main {
	public static final int WINDOW_WIDTH = 1200, WINDOW_HEIGHT = 800, MAP_WIDTH = 1200, MAP_HEIGHT = 600;
	public static BufferedImage map;


	public static void main(String[] args) {
		new Main();
	}
	
	public Main() {
		Graph g = new Graph();
		Reader reader = new Reader("src/main/nodes.xml");
		reader.readFile(g);
		try {
			map = ImageIO.read(new File("src/main/map.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		
//		g.addNode("A", 41, 100);
//		g.addNode("B", 41, 95);
//		g.addNode("C", 38, 93);
//		g.addNode("D", 39, 98);
//		g.addNode("E", 37, 101);
//		g.addNode("F", 40, 91);
//		
//		g.addNode("G", 2, 40);
//		g.addNode("lhkj", 98, 35);
//		g.addNode("325hrer", 2, 40);
//		g.addNode("ewerdsf", -70, -170);

				
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
	private class Reader {
		String file;
		public Reader(String file) {
			this.file = file;
		}
		public void readFile(Graph g) {
			try {  //XML Reader code from https://www.javatpoint.com/how-to-read-xml-file-in-java
				//creating a constructor of file class and parsing an XML file  
				File file = new File(this.file);  
				//an instance of factory that gives a document builder  
				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();  
				//an instance of builder to parse the specified xml file  
				DocumentBuilder db = dbf.newDocumentBuilder();  
				Document doc = db.parse(file);  
				doc.getDocumentElement().normalize();  
				System.out.println("Root element: " + doc.getDocumentElement().getNodeName());  
				NodeList nodeList = doc.getElementsByTagName("node");  
				// nodeList is not iterable, so we are using for loop  
				for (int i = 0; i < nodeList.getLength(); i++) {  
					Node node = nodeList.item(i);  
					if (node.getNodeType() == Node.ELEMENT_NODE) {  
						Element eElement = (Element) node;
						double lon, lat;
						String name, continent, country;
						name = eElement.getElementsByTagName("name").item(0).getTextContent();
						lon = Double.parseDouble(eElement.getElementsByTagName("longitude").item(0).getTextContent())+90;
						lat = Double.parseDouble(eElement.getElementsByTagName("latitude").item(0).getTextContent())+90;
						country = eElement.getElementsByTagName("latitude").item(0).getTextContent();
						continent = eElement.getElementsByTagName("country").item(0).getTextContent();
						
						g.addNode(name, (float)lat, (float)lon, country, continent);

					}  
				}  
			} catch (Exception e) {  
				e.printStackTrace();  
			}    
		}
	}
	
}