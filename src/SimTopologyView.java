import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

public class SimTopologyView implements ViewStrategy {
	// All variables needed to create basic window
	private JFrame mainFrame;
	private JPanel optionsPane;
	private NodeDisplayPanel topologyCanvas;
	private List<Ellipse2D> nodes = new ArrayList<>();
	private List<String> nodeNames = new ArrayList<>();
	private List<List<String>> nodeMessages = new ArrayList<>();
	private List<Line2D> connections = new ArrayList<>();
	private List<Color> connectionColors = new ArrayList<>();
	private int xval = 0;
	private int yval = 0;
	private final int radius = 40;
	
	// Buttons used to create nodes and connections
	private JButton nameButton;
	private JButton conButton;
	
	private JMenuItem reset;
	
	private JComboBox<String> algorithmList;
	
	// Text fields used for creation of nodes/connections
	private JTextField tfNewNodeName;
	private JTextField tfNodeToConnect;
	private JTextField tfNewConnectionsTF;
	
	/**
	 * Creates the Topological Simulation View
	 */
	public SimTopologyView() {
		initializeComponents();
	}
	
	/**
	 * Initializes all components 
	 */
	private void initializeComponents() {
		mainFrame = new JFrame("Topology View");
		optionsPane = new JPanel();
		topologyCanvas = new NodeDisplayPanel();
		
		setupMenuBar();
		setupOptionsPane();
		setupNodeView();
		
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setSize(1200, 400);
		mainFrame.setResizable(false);
		//mainFrame.pack();
		mainFrame.setVisible(true);
	}
	
	/**
	 * Sets up the node view area
	 */
	private void setupNodeView() {
		topologyCanvas.setSize(400, 400);
		mainFrame.getContentPane().add(topologyCanvas, BorderLayout.CENTER);
	}
	
	/**
	 * Creates the menu bar
	 */
	private void setupMenuBar() {
		JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        reset = new JMenuItem("Reset");
        
        fileMenu.add(reset);
        menuBar.add(fileMenu);
           
        mainFrame.setJMenuBar(menuBar);
	}
	
	/**
	 * Creates the menu options 
	 */
	private void setupOptionsPane() {
		mainFrame.getContentPane().add(optionsPane, BorderLayout.LINE_START);
		
		optionsPane.setLayout(new GridLayout(6, 2));
		
        // Creates Text Field and Button to Make New Node
		JLabel nameLabel = new JLabel("New node name: ");
		optionsPane.add(nameLabel);
	    tfNewNodeName = new JTextField(25);
	    optionsPane.add(tfNewNodeName);
		
		nameButton = new JButton("Create Node");
		optionsPane.add(nameButton);
		
		JLabel tmp1 = new JLabel("");
		optionsPane.add(tmp1);
		
		// Creates Text Fields and Button to Make Connection To a Node
		JLabel connectionLabel = new JLabel("Node to set connections:");
		optionsPane.add(connectionLabel);
		tfNodeToConnect = new JTextField(25);
		optionsPane.add(tfNodeToConnect);
				
		JLabel conLabel = new JLabel("List of connections: ");
		optionsPane.add(conLabel);
		tfNewConnectionsTF = new JTextField(25);
		optionsPane.add(tfNewConnectionsTF);
		
		conButton = new JButton("Establish Connections");
		optionsPane.add(conButton);
		JLabel tmp2 = new JLabel("");	
		optionsPane.add(tmp2);
		
		// Later we might want to move this to the constructer so we can pass in the algorithms 
		String[] algorithms = { "Random" };
		algorithmList = new JComboBox<>(algorithms);
		
		algorithmList.setActionCommand("algorithm");
		optionsPane.add(new JLabel("Routing algorithm:"));
		optionsPane.add(algorithmList);
		
		
	}
	
	/**
	 * Update the Topological View
	 */
	private void updateTopologyPanel() {
		topologyCanvas.validate();
		topologyCanvas.repaint();
	}
	
	/**
	 * Adds a node to the Topological View
	 */
	public void addNode(String n) {
		// Find where x, y should be.
		nodeNames.add(n);
		
		double[] loc = findGoodXY();
		nodes.add(new Ellipse2D.Double(loc[0], loc[1], radius, radius));
		nodeMessages.add(new ArrayList<String>());
		
		updateTopologyPanel();
	}
	
	/**
	 * Removes a node to the Topological View
	 */
	public void removeNode(String name) {
		int idx = -1;
		for (int i = 0; i < nodeNames.size(); i++) {
			if (nodeNames.get(i).equals(name)) {
				idx = i;
				nodeNames.remove(idx);
				break;
			}
		}
		if (idx != -1) {
			nodes.remove(idx);
			nodeMessages.remove(idx);
		}
		
		updateTopologyPanel();
	}

	/**
	 * Adds a connection between two nodes in the Topological View
	 */
	public void addConnection(String A, String B) {
		Ellipse2D eA = findNode(A);
		Ellipse2D eB = findNode(B);
		
		connections.add(new Line2D.Double(eA.getCenterX(), eA.getCenterY(), eB.getCenterX(), eB.getCenterY()));
		connectionColors.add(Color.BLACK);
		
		updateTopologyPanel();
	}
	
	/**
	 * Removes a connection between two nodes in the Topological View
	 */
	public void removeConnection(String A, String B) {
		Line2D line = findConnection(A, B);
		
		connectionColors.remove(connections.indexOf(line));
		connections.remove(line);
		
		updateTopologyPanel();
	}
	
	/**
	 * Determines good placement
	 * 
	 * @return double[]
	 */
	private double[] findGoodXY() {
		// Later make sure not on / inbetween nodes 
		xval += 150;
		if (topologyCanvas.getWidth() - xval < 150) {
			yval += 150; 
			xval = 150;
		}
		
		return new double[] {xval, yval};
	}
	
	/**
	 * Gets the name of the new node
	 */
	public String getNewNodeName() {
		String s = tfNewNodeName.getText();
		tfNewNodeName.setText("");
		return s;
		
	}
	
	/**
	 * Gets the name of the new connection
	 */
	public String getNewConnectionNodeName() {
		String s = tfNodeToConnect.getText();
		tfNodeToConnect.setText("");
		return s;
	}
	
	/**
	 * Gets a list of the connections
	 */
	public String getConnectionList() {
		String s = tfNewConnectionsTF.getText();
		tfNewConnectionsTF.setText("");
		return s;
	}
	
	/**
	 * Adds a message to the node in the Topological View
	 */
	public void addMessage(String message, String node) {
		int idx = nodeNames.indexOf(node);
		nodeMessages.get(idx).add(message);
		
		updateTopologyPanel();
	}
	
	/**
	 * Removes the message of a node in the Topological View
	 */
	public void removeMessage(String message, String node) {
		int idx = nodeNames.indexOf(node);
		nodeMessages.get(idx).remove(message);
		
		updateTopologyPanel();
	}
	
	/**
	 * Updates the message of a node in the Topological View
	 */
	public void updateMessage(String message, String currentNode, String newNode) {
		removeMessage(message, currentNode);
		addMessage(message, newNode);
		Line2D connection = findConnection(currentNode, newNode);
		int idx = connections.indexOf(connection);
		connectionColors.set(idx, Color.RED);
		
		updateTopologyPanel();
	}
	
	/**
	 * Needs to be called at the end of a simulation step.
	 */
	public void simStepComplete() {
		for (int i = 0; i < connectionColors.size(); i++) {
			connectionColors.set(i, Color.BLACK);
		}
		
		updateTopologyPanel();
	}
	
	/**
	 * Finds the connection path between two nodes in the Topologoical View
	 * @param A
	 * @param B
	 * @return
	 */
	private Line2D findConnection(String A, String B) {
		Ellipse2D nA = findNode(A);
		Ellipse2D nB = findNode(B);
		double x1 = nA.getCenterX();
		double y1 = nA.getCenterY();
		double x2 = nB.getCenterX();
		double y2 = nB.getCenterY();
		
		// Find connection by its line end points.
		for (Line2D l : connections) {
			if (Math.abs(l.getX1() - x1) < 1 &&
				Math.abs(l.getY1() - y1) < 1 &&
				Math.abs(l.getX2() - x2) < 1 &&
				Math.abs(l.getY2() - y2) < 1) {
				return l;
			}
		}
		return null;
	}
	
	/**
	 * Finds the nodes location in the Topological View
	 * 
	 * @param node (String)
	 * 
	 * @return Ellipse2D
	 */
	private Ellipse2D findNode(String node) {
		for (int i = 0; i < nodeNames.size(); i++) {
			if (nodeNames.get(i).equals(node)) {
				return nodes.get(i);
			}
		}
		return null;
	}
	
	public static void main(String[] args) {
		
		SimTopologyView sView = new SimTopologyView();
		Simulation model = new Simulation();
		SimController c = new SimController(sView, model);
		
		sView.setActionListener(c);
		/*
		sView.addNode("A");
		sView.addNode("B");
		sView.addNode("C");
		sView.addNode("D");
		sView.addMessage("TEST", "A");
		sView.addMessage("ABC", "A");
		sView.addMessage("CAT", "D");
		sView.addConnection("A", "B");
		sView.addConnection("A", "D");
		sView.addConnection("C", "D");
		sView.updateMessage("TEST", "A", "D");
		//sView.simStepComplete();
		 * 
		 */
	}

	
	
	private class NodeDisplayPanel extends JPanel {	
		private static final long serialVersionUID = 1L;

		@Override 
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g;
			g2d.setColor(Color.black);
			for (int i = 0; i < nodes.size(); i++) {
				g2d.draw(nodes.get(i));
				double nameX = nodes.get(i).getCenterX();
				double nameY = nodes.get(i).getCenterY();
				g2d.drawString(nodeNames.get(i), (int)nameX, (int)nameY);
				
				StringBuilder sb = new StringBuilder();
				for (int j = 0; j < nodeMessages.get(i).size(); j++) {
					sb.append(nodeMessages.get(i).get(j) + ", ");
				}
				if (sb.length() != 0) {
					sb.deleteCharAt(sb.length()-1);
					sb.deleteCharAt(sb.length()-1);
					g2d.drawString(sb.toString(), (int)nameX + radius/2, (int)nameY);
				}			
			}
			for (int i = 0; i < connections.size(); i++) {
				Line2D l = connections.get(i);
				g2d.setColor(connectionColors.get(i));
				g2d.draw(l);
			}
		}
	}

	@Override
	public void setActionListener(ActionListener listener) {
		nameButton.addActionListener(listener);
		conButton.addActionListener(listener);
		reset.addActionListener(listener);
		algorithmList.addActionListener(listener);
		
	}
	@Override
	public void reset() {
		nodes.clear();
		nodeNames.clear();
		nodeMessages.clear();
		connections.clear();
		connectionColors.clear();
		tfNewNodeName.setText("");;
		tfNodeToConnect.setText("");
		tfNewConnectionsTF.setText("");
		
		xval = 0;
		yval = 0;
		updateTopologyPanel();
	}
	@Override
	public String getSelectedAlgorithm() {
		return (String)algorithmList.getSelectedItem();
	}
}
