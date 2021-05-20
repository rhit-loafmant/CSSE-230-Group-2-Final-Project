package main;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
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
	public static final int WINDOW_WIDTH = 1215, WINDOW_HEIGHT = 850, MAP_WIDTH = 1215, MAP_HEIGHT = 600;
	public static BufferedImage map;
	public Graph g;

	public static void main(String[] args) {
		new Main();
	}

	public Main() {
		g = new Graph();
		Reader reader = new Reader("src/main/nodes.xml");
		reader.readFile(g);
		try {
			map = ImageIO.read(new File("src/main/map.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Starting GUI
		MapFrame mapFrame = new MapFrame();

		mapFrame.setTitle("Airports of the World!");
		mapFrame.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
		mapFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Timer t = new Timer(16, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mapFrame.repaint();
			}
		});
		
		t.start();
		mapFrame.pack();
		mapFrame.setVisible(true);

	}

	private class Reader {
		String file;

		public Reader(String file) {
			this.file = file;
		}

		public void readFile(Graph g) {
			try { // XML Reader code from https://www.javatpoint.com/how-to-read-xml-file-in-java
					// creating a constructor of file class and parsing an XML file
				File file = new File(this.file);
				// an instance of factory that gives a document builder
				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				// an instance of builder to parse the specified xml file
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
						lon = Double.parseDouble(eElement.getElementsByTagName("longitude").item(0).getTextContent())
								+ 90;
						lat = Double.parseDouble(eElement.getElementsByTagName("latitude").item(0).getTextContent())
								+ 90;
						country = eElement.getElementsByTagName("country").item(0).getTextContent();
						continent = eElement.getElementsByTagName("continent").item(0).getTextContent();

						g.addNode(name, (float) lat, (float) lon, country, continent);

					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private class MapFrame extends JFrame{

		public MapFrame() {
			Container pane = getContentPane();			
			setResizable(false);
			MapComponent mapComp = new MapComponent(g.nodes);
			add(mapComp, BorderLayout.NORTH);
			
			ControlPanel controlPanel = new ControlPanel(g, mapComp);
			pane.add(controlPanel, BorderLayout.SOUTH);	
		}
	}
}