package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import javax.swing.JComponent;
import javax.swing.JPanel;

import main.Graph.Node;

public class MapComponent extends JPanel{
	public ArrayList<Node> nodes = new ArrayList<Node>();
	public MapComponent(ArrayList<Node> nodes) {
		this.nodes = nodes;
		this.setPreferredSize(new Dimension(1200, 600));
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.drawImage(Main.map, 0, 0, null);
		g2d.translate(300, Main.MAP_HEIGHT);
		
		//THIS CODE DRAWS 0, 0 CROSSHAIR
//		g2d.setColor(Color.RED);
//		g2d.drawLine(-10, 0, 10, 0);
//		g2d.drawLine(0, -10, 0, 10);
		
		
		double latMulti = Main.MAP_HEIGHT/180.00, lonMulti = Main.MAP_WIDTH/360.00;
		for(Node node : this.nodes) {
			int x1 = (int) Math.round(lonMulti*node.longitude);
			int y1 = -(int) Math.round(latMulti*node.latitude);
			Color c = Color.BLACK;
			
			//THIS CODE DRAWS LINES FOR EVERYTHING
			for(Node neighbor : node.adjacentNodes) {
				int x2 = (int) Math.round(lonMulti*neighbor.longitude);
				int y2 = -(int) Math.round(latMulti*neighbor.latitude);
				node.drawEdge(g2d, Color.BLUE, x1, y1, x2, y2);
			}
			node.drawNode(g2d, Color.BLUE, 6, x1, y1);
			
		}
	}	
	
}
