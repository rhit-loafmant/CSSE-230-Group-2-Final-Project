package main;

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
		Graphics2D g2 = (Graphics2D) g;
		
		for(Node node: this.nodes) {
			node.drawOn(g2);
		}
	}	

}
