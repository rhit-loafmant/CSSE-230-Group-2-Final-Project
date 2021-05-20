package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.util.ArrayList;

import main.Graph.Node;

public class ControlPanel extends JPanel {
	private int maxAdjNodeDist;
	private JList airportSelector;
	private JButton selectButton;
	private JTable airport1Table;
	private JTable airport2Table;
	private Object[][] airport1Data;
	private Object[][] airport2Data;
	private Node airport1 = null;
	private Node airport2 = null;

	public ArrayList<Node> nodes = new ArrayList<Node>();

	public ControlPanel(Graph g) {
		this.nodes = g.nodes;
		this.setPreferredSize(new Dimension(1200, 200));
		setLayout(new FlowLayout());
		airport1Data = new Object[][]{{" "," "," "," "," "}};
		airport2Data = new Object[][]{{" "," "," "," "," "}};
		init();
	}
	
	private void init() {
		DefaultListModel model = new DefaultListModel();
		
		for(int i=0; i<nodes.size(); i++) {
			model.addElement(nodes.get(i).name);
		}
		
		airportSelector = new JList(model);
		airportSelector.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		airportSelector.setLayoutOrientation(JList.VERTICAL);
		airportSelector.setVisibleRowCount(10);
		add(airportSelector);
		
		selectButton = new JButton("Select");
		selectButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
					selectAirport(airportSelector.getSelectedIndex());
					airportSelector.clearSelection();
				}	
			});
		add(selectButton);
		
		JScrollPane listScroller = new JScrollPane(airportSelector);
		listScroller.setPreferredSize(new Dimension(300, 180));
		add(listScroller);
		
		String[] columnNames = {"Name", "Latitude", "Longitude", "Continent", "Country"};
		
		airport1Table = new JTable(airport1Data, columnNames);
		JScrollPane table1Scroller = new JScrollPane(airport1Table);
		table1Scroller.setPreferredSize(new Dimension(300, 180));
		add(table1Scroller);
		
		airport2Table = new JTable(airport2Data, columnNames);
		JScrollPane table2Scroller = new JScrollPane(airport2Table);
		table2Scroller.setPreferredSize(new Dimension(300, 180));
		add(table2Scroller);
		
		
	}
	
	public void selectAirport(int index) {
		String[] columnNames = {"Name", "Latitude", "Longitude", "Continent", "Country"};
		if(airport1 == null) {
			airport1 = nodes.get(index);
			Object[] arr = {airport1.name, airport1.latitude, airport1.longitude, "NA", "NA"};
			airport1Data[0] = arr;
			airport1Table = new JTable(airport1Data, columnNames);
		}else if(airport2 == null) {
			airport2 = nodes.get(index);
			Object[] arr = {airport2.name, airport2.latitude, airport2.longitude, "NA", "NA"};
			airport2Data[0] = arr;
			airport2Table = new JTable(airport1Data, columnNames);
		}
	}
	
	public void clearAirports() {
		airport1 = null;
		airport2 = null;
		airport1Data = new Object[][]{{" "," "," "," "," "}};
		airport2Data = new Object[][]{{" "," "," "," "," "}};

	}

}
