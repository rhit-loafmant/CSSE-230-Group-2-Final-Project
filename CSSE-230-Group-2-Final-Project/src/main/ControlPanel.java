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
	private JButton clearButton;
	private JButton routeButton;
	private JTable airport1Table;
	private JTable airport2Table;
	private Object[][] airport1Data;
	private Object[][] airport2Data;
	private Node airport1 = null;
	private Node airport2 = null;
	private Graph g;
	private Dijkstra dij;
	private MapComponent map;

	public ArrayList<Node> nodes = new ArrayList<Node>();

	public ControlPanel(Graph g, MapComponent map) {
		this.nodes = g.nodes;
		this.g = g;
		this.map =map;
		this.dij = new Dijkstra();
		this.setPreferredSize(new Dimension(1200, 200));
		setLayout(new FlowLayout());
		airport1Data = new Object[][] { { " ", " ", " ", " ", " " } };
		airport2Data = new Object[][] { { " ", " ", " ", " ", " " } };
		init();
	}

	private void init() {

		DefaultListModel model = new DefaultListModel();
		for (int i = 0; i < nodes.size(); i++) {
			model.addElement(nodes.get(i).name);
		}
		airportSelector = new JList(model);
		airportSelector.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		airportSelector.setLayoutOrientation(JList.VERTICAL);
		airportSelector.setVisibleRowCount(10);
		add(airportSelector);
		JScrollPane listScroller = new JScrollPane(airportSelector);
		listScroller.setPreferredSize(new Dimension(300, 180));
		add(listScroller);

		selectButton = new JButton("Select");
		selectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (airportSelector.getSelectedIndex() != -1) {
					selectAirport(airportSelector.getSelectedIndex());
					airportSelector.clearSelection();
				}
			}
		});
		add(selectButton);

		String[] columnNames = { "Name", "Latitude", "Longitude", "Continent", "Country" };

		airport1Table = new JTable(airport1Data, columnNames);
		airport1Table.setEnabled(false);
		JScrollPane table1Scroller = new JScrollPane(airport1Table);
		table1Scroller.setPreferredSize(new Dimension(300, 40));
		add(table1Scroller);

		airport2Table = new JTable(airport2Data, columnNames);
		airport2Table.setEnabled(false);
		JScrollPane table2Scroller = new JScrollPane(airport2Table);
		table2Scroller.setPreferredSize(new Dimension(300, 40));
		add(table2Scroller);

		clearButton = new JButton("Clear");
		clearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearAirports();
			}
		});
		add(clearButton);

		routeButton = new JButton("Route Course");
		routeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (airport1 != null && airport2 != null) {
					createRoute();
				}
			}
		});
		add(routeButton);

	}

	public void selectAirport(int index) {
		String[] columnNames = { "Name", "Latitude", "Longitude", "Continent", "Country" };
		if (airport1 == null) {
			airport1 = nodes.get(index);
			Object[] arr = { airport1.name, airport1.latitude, airport1.longitude, airport1.continent,
					airport1.country };
			airport1Data[0] = arr;
			airport1Table = new JTable(airport1Data, columnNames);
		} else if (airport2 == null) {
			if (nodes.get(index) != airport1) {
				airport2 = nodes.get(index);
				Object[] arr = { airport2.name, airport2.latitude, airport2.longitude, airport2.continent,
						airport2.country };
				airport2Data[0] = arr;
				airport2Table = new JTable(airport1Data, columnNames);
			}
		}
	}

	public void clearAirports() {
		String[] columnNames = { "Name", "Latitude", "Longitude", "Continent", "Country" };
		Object[] arr = { " ", " ", " ", " ", " " };
		airport1 = null;
		airport2 = null;
		airport1Data[0] = arr;
		airport1Table = new JTable(airport1Data, columnNames);
		airport2Data[0] = arr;
		airport2Table = new JTable(airport1Data, columnNames);

	}

	public void createRoute() {
//		Graph sPT = dij.shortestPathTree(g, airport1);
		ArrayList<Node> flightRoute = dij.pathFinder(g, airport1, airport2);
		System.out.println(flightRoute.size());
//		for(int i=0; i<flightRoute.size(); i++) {
//			System.out.println(flightRoute.get(i).name);
//		}
	}

}
