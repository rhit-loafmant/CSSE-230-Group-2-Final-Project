package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.util.ArrayList;

import main.Graph.Node;

public class ControlPanel extends JPanel {
	private JList airportSelector;
	private JButton selectButton;
	private JButton clearButton;
	private JButton routeButton;
	private JButton flightStats;
	private JButton destByDist;
	private JButton destByTime;
	private JTextField cDest;
	private JTextField searchBar;
	private JTable airport1Table;
	private JTable airport2Table;
	private Object[][] airport1Data;
	private Object[][] airport2Data;
	private Node airport1 = null;
	private Node airport2 = null;
	private Graph g;
	private Dijkstra dij;
	private MapComponent mapComp;
	public ArrayList<Node> sPTArray;
	public ArrayList<Node> searchList;
	public ArrayList<Node> nodes = new ArrayList<Node>();

	public ControlPanel(Graph g, MapComponent mapComp) {
		this.nodes = g.nodes;
		this.g = g;
		this.mapComp = mapComp;
		this.dij = new Dijkstra();
		this.setPreferredSize(new Dimension(1200, 200));
		setLayout(new BorderLayout());
		airport1Data = new Object[][] { { " ", " ", " ", " ", " " } };
		airport2Data = new Object[][] { { " ", " ", " ", " ", " " } };
		init();
	}

	private void init() {

		JPanel card1 = new JPanel();
		card1.setLayout(new BorderLayout());
		JPanel card2A = new JPanel();
		card2A.setLayout(new BorderLayout());
		JPanel card2B = new JPanel();
		card2B.add(card2A);
		JPanel card3 = new JPanel();
		card3.setLayout(new BorderLayout());

		airportSelector = new JList(filteredModel(""));
		airportSelector.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		airportSelector.setLayoutOrientation(JList.VERTICAL);
		airportSelector.setVisibleRowCount(10);
		JScrollPane listScroller = new JScrollPane(airportSelector);
		listScroller.setPreferredSize(new Dimension(300, 180));
		card1.add(listScroller, BorderLayout.WEST);
		selectButton = new JButton("Select");
		selectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (airportSelector.getSelectedIndex() != -1) {
					selectAirport(airportSelector.getSelectedIndex());
					airportSelector.clearSelection();
				}
			}
		});
		card1.add(selectButton);

		String[] columnNames = { "Name", "Latitude", "Longitude", "Continent", "Country" };

		airport1Table = new JTable(airport1Data, columnNames);
		airport1Table.setEnabled(false);
		JScrollPane table1Scroller = new JScrollPane(airport1Table);
		table1Scroller.setPreferredSize(new Dimension(500, 39));
		card2A.add(table1Scroller, BorderLayout.NORTH);

		airport2Table = new JTable(airport2Data, columnNames);
		airport2Table.setEnabled(false);
		JScrollPane table2Scroller = new JScrollPane(airport2Table);
		table2Scroller.setPreferredSize(new Dimension(500, 39));
		card2A.add(table2Scroller, BorderLayout.SOUTH);

		clearButton = new JButton("Clear");
		clearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearAirports();
			}
		});
		card1.add(clearButton, BorderLayout.EAST);

		routeButton = new JButton("Route Course");
		routeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (airport1 != null && airport2 != null) {
					createRoute();
				}
			}
		});
		card2B.add(routeButton);

		flightStats = new JButton("Flight Statistics");
		flightStats.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (airport1 != null && airport2 != null) {
					JFrame statisticsFrame = new JFrame();
					statisticsFrame.setTitle("Flight Statistics");
					statisticsFrame.setSize(new Dimension(500, 400));
					statisticsFrame.setResizable(false);

					String[] columnNames = { "Name", "Latitude", "Longitude", "Continent", "Country" };
					Object[][] data = new Object[sPTArray.size()][];
					for (int i = 0; i < sPTArray.size(); i++) {
						Object[] arr = { sPTArray.get(i).name, sPTArray.get(i).latitude, sPTArray.get(i).longitude,
								sPTArray.get(i).continent, sPTArray.get(i).country };
						data[i] = arr;
					}

					JTable statisticTable = new JTable(data, columnNames);
					statisticTable.setEnabled(false);
					JScrollPane statisticScroller = new JScrollPane(statisticTable);
					statisticScroller.setPreferredSize(new Dimension(500, (19 * sPTArray.size() + 19)));
					statisticsFrame.add(statisticScroller, BorderLayout.NORTH);

					String[] columnNames2 = { "Total Distance Travelled (km)", "Total Time Taken (hr)" };
					Object[][] data2 = { { g.sPTArray.flightDist, g.sPTArray.flightTime } };
					JTable distanceTable = new JTable(data2, columnNames2);
					distanceTable.setEnabled(false);
					JScrollPane distanceTableScroller = new JScrollPane(distanceTable);
					distanceTableScroller.setPreferredSize(new Dimension(500, 39));
					statisticsFrame.add(distanceTableScroller, BorderLayout.SOUTH);

					statisticsFrame.pack();
					statisticsFrame.setVisible(true);
				}
			}
		});
		card3.add(flightStats, BorderLayout.CENTER);

		destByDist = new JButton("Destinations Within Distance");
		destByDist.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (airport1 != null && !cDest.getText().isEmpty()) {
					Graph sPT = dij.shortestPathTree(g, airport1);
					int dist = Integer.parseInt(cDest.getText());
					possibleDestinationsByDistance(sPT, dist);
				}
			}
		});
		card2B.add(destByDist);

		destByTime = new JButton("Destinations Within Hours");
		destByTime.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (airport1 != null && !cDest.getText().isEmpty()) {
					Graph sPT = dij.shortestPathTree(g, airport1);
					int dist = Integer.parseInt(cDest.getText());
					possibleDestinationsByTime(sPT, dist);
				}
			}
		});
		card2B.add(destByTime);

		cDest = new JTextField();
		cDest.setPreferredSize(new Dimension(100, 20));
		card2B.add(cDest);

		searchBar = new JTextField();
		searchBar.setPreferredSize(new Dimension(300, 39));
		card1.add(searchBar, BorderLayout.NORTH);

		searchBar.addKeyListener(new KeyListener() {
			public void updateList() {
				if (searchBar.getText() != "")
					airportSelector.setModel(filteredModel(searchBar.getText()));
			}

			@Override
			public void keyTyped(KeyEvent e) {
				updateList();
				// TODO Auto-generated method stub

			}

			@Override
			public void keyPressed(KeyEvent e) {
				updateList();
				// TODO Auto-generated method stub

			}

			@Override
			public void keyReleased(KeyEvent e) {
				updateList();
				// TODO Auto-generated method stub

			}
		});

		add(card1, BorderLayout.WEST);
		add(card2B, BorderLayout.CENTER);
		add(card3, BorderLayout.EAST);
	}

	public void selectAirport(int index) {
		String[] columnNames = { "Name", "Latitude", "Longitude", "Continent", "Country" };
		if (airport1 == null) {
			airport1 = searchList.get(index);
			Object[] arr = { airport1.name, airport1.latitude, airport1.longitude, airport1.continent,
					airport1.country };
			airport1Data[0] = arr;
			airport1Table = new JTable(airport1Data, columnNames);
		} else if (airport2 == null) {
			if (searchList.get(index) != airport1) {
				airport2 = searchList.get(index);
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
		sPTArray = dij.pathFinder(g, airport1, airport2);
		mapComp.repaint();
	}

	public void possibleDestinationsByDistance(Graph sPT, int distance) {
		Graph pDBDT = dij.shortestPathTree(g, airport1);
		g.sPTArray.array = dij.possibleDestinationsByDistance(pDBDT, distance, airport1);
		mapComp.repaint();
	}

	public void possibleDestinationsByTime(Graph sPT, int hours) {
		Graph pDBTT = dij.shortestPathTree(g, airport1);
		g.sPTArray.array = dij.possibleDestinationsByTime(pDBTT, hours, airport1);
		mapComp.repaint();
	}

	public DefaultListModel filteredModel(String filter) {
		DefaultListModel model = new DefaultListModel();
		searchList = new ArrayList<Node>();
		int i = 0;

		for (Node node : nodes) {
			if (node.name.contains(filter)) {
				model.add(i++, node.name);
				searchList.add(node);
			}
		}
		return model;

	}

}
