package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import javax.swing.JComponent;
import main.Graph.Node;

public class MapComponent extends JComponent{
	public ArrayList<Node> nodes = new ArrayList<Node>();
	
	public MapComponent(ArrayList<Node> nodes) {
		this.nodes = nodes;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.translate(Main.WINDOW_WIDTH/2, Main.WINDOW_HEIGHT/2);
		g2d.setColor(Color.RED);
		g2d.drawLine(-10, 0, 10, 0);
		g2d.drawLine(0, -10, 0, 10);
		for(Node node : this.nodes) {

			int x1 = Math.round((Main.WINDOW_HEIGHT/2) * (node.longitude/180));
			int y1 = Math.round((Main.WINDOW_WIDTH/2) * (node.latitude/360));
			
			for(Node neighbor : node.adjacentNodes) {
				int x2 = Math.round((Main.WINDOW_HEIGHT/2) * (neighbor.longitude/180));
				int y2 = Math.round((Main.WINDOW_WIDTH/2) * (neighbor.latitude/360));
				node.drawLines(g2d, Color.BLUE, x1, y1, x2, y2);
			}
			node.drawNode(g2d, Color.BLACK, 4, x1, y1);
			
		}
	}	

}
