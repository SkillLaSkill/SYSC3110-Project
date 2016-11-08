import javax.swing.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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
	private JPanel outputPanel;
	private List<Ellipse2D> nodes = new ArrayList<>();
	private List<String> nodeNames = new ArrayList<>();
	private List<List<String>> nodeMessages = new ArrayList<>();
	private List<Line2D> connections = new ArrayList<>();
	private List<Color> connectionColors = new ArrayList<>();
	private final int radius = 40;
	private int xval = 0;
	private int yval = 0;
	private boolean alternate = false;
	
	// Buttons used to create nodes and connections
	private JButton nameButton;
	private JButton conButton;
	private JButton nodeDeleteButton;
	private JButton conDeleteButton;
	private JButton simButton;
	
	// Buttons used to manage simulation
	private JButton simStepButton;
	
	private JMenuItem reset;
	
	private JComboBox<String> algorithmList;
	
	// Text fields used for creation of nodes/connections
	private JTextField tfNewNodeName;
	private JTextField tfNodeToConnect;
	private JTextField tfNewConnections;
	private JTextField tfDeleteNodeName;
	private JTextField tfDeleteConnection;
	private JTextField tfSendRate;
	private JTextField tfSimSteps;
	
	private JTextArea outputText;
	
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
		mainFrame.setLayout(new GridBagLayout());
		optionsPane = new JPanel();
		topologyCanvas = new NodeDisplayPanel();
		
		setupMenuBar();
		setupOptionsPane();
		setupNodeView();
		setupOutputView();
		
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setSize(1200, 600);
		mainFrame.setResizable(false);
		//mainFrame.pack();
		mainFrame.setVisible(true);
	}
	private void setupOutputView() {
		outputPanel = new JPanel();
		outputPanel.setLayout(new GridLayout(1,1));
		//outputPanel.setSize(200, 400);
		outputText = new JTextArea();
		outputText.setEditable(false);
		outputPanel.add(outputText);
		
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 1;
		c.weightx = 1;
		c.weighty = 1;
		c.gridwidth = 100;
		c.fill = GridBagConstraints.BOTH;
		
		mainFrame.getContentPane().add(outputPanel, c);
	}
	
	/**
	 * Sets up the node view area
	 */
	private void setupNodeView() {
		//topologyCanvas.setSize(400, 400);
		
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 0;
		c.weightx = 1;
		c.weighty = 1;
		c.fill = GridBagConstraints.BOTH;
		mainFrame.getContentPane().add(topologyCanvas, c);
	}
	
	
	/**
	 * Creates the menu options 
	 */
	private void setupOptionsPane() {
		optionsPane.setLayout(new GridLayout(14, 2));
		
		/* Managing Nodes/connections */
        // Creates Text Field and Button to Make New Node
		JLabel nameLabel = new JLabel("New node name: ");
		optionsPane.add(nameLabel);
	    tfNewNodeName = new JTextField(25);
	    optionsPane.add(tfNewNodeName);
		
		nameButton = new JButton("Create Node");
		optionsPane.add(new JLabel(""));
		optionsPane.add(nameButton);
		
		
		
		// Creates Text Fields and Button to Make Connection To a Node
		JLabel connectionLabel = new JLabel("Node to set connections:");
		optionsPane.add(connectionLabel);
		tfNodeToConnect = new JTextField(25);
		optionsPane.add(tfNodeToConnect);
				
		JLabel conLabel = new JLabel("List of nodes to connect: ");
		optionsPane.add(conLabel);
		tfNewConnections = new JTextField(25);
		optionsPane.add(tfNewConnections);
		
		conButton = new JButton("Establish Connections");
		optionsPane.add(new JLabel(""));
		optionsPane.add(conButton);
		
		tfDeleteNodeName = new JTextField();
		optionsPane.add(new JLabel("Node to delete:"));
		optionsPane.add(tfDeleteNodeName);
		
		nodeDeleteButton = new JButton("Delete Node");
		optionsPane.add(new JLabel(""));
		optionsPane.add(nodeDeleteButton);	
		
		optionsPane.add(new JLabel("Delete Connection: "));
		tfDeleteConnection = new JTextField();
		optionsPane.add(tfDeleteConnection);
		
		conDeleteButton = new JButton("Delete Connection");
		optionsPane.add(new JLabel(""));
		optionsPane.add(conDeleteButton);
		
		/* END Managing nodes/connections */
		
		/* Managing Simulation */
		optionsPane.add(new JLabel("<HTML><U>Simulation</U></HTML>"));
		
		optionsPane.add(new JLabel(""));
		// Later we might want to move this to the constructer so we can pass in the algorithms 
		String[] algorithms = { "Random" };
		algorithmList = new JComboBox<>(algorithms);
		
		algorithmList.setActionCommand("algorithm");
		optionsPane.add(new JLabel("Routing algorithm:"));
		optionsPane.add(algorithmList);
		
		
		tfSendRate = new JTextField();
		//setSendRateButton = new JButton("Set Sendrate");
		optionsPane.add(new JLabel("Send Rate"));
		optionsPane.add(tfSendRate);
		//optionsPane.add(setSendRateButton);
		
		simButton = new JButton("Simulate");
		tfSimSteps = new JTextField();
		optionsPane.add(new JLabel("Steps:"));
		optionsPane.add(tfSimSteps);
		
		//optionsPane.add(new JLabel(""));
		optionsPane.add(simButton);
		
		simStepButton = new JButton("Simulate Step");
		optionsPane.add(simStepButton);
		
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		c.weighty = 1;
		c.weightx = 0.1;
		mainFrame.getContentPane().add(optionsPane, c);
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
	
	@Override
	public void setActionListener(ActionListener listener) {
		nameButton.addActionListener(listener);
		conButton.addActionListener(listener);
		reset.addActionListener(listener);
		algorithmList.addActionListener(listener);
			
		nodeDeleteButton.addActionListener(listener);
		conDeleteButton.addActionListener(listener);
		simStepButton.addActionListener(listener);
		simButton.addActionListener(listener);
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
				break;
			}
		}
		if (idx != -1) {
			// Remove all connections associated with this node.
			for (String other : nodeNames) {
				if (!name.equals(other) && findConnection(name, other) != null) {
					removeConnection(name, other);
				}
			}
			
			nodes.remove(idx);
			nodeNames.remove(idx);
			nodeMessages.remove(idx);
			
		}
		// Need to delete connections connected to this node.
		updateTopologyPanel();
	}

	/**
	 * Adds a connection between two nodes in the Topological View
	 */
	public void addConnection(String A, String B) {
		Ellipse2D eA = findNode(A);
		Ellipse2D eB = findNode(B);
		
		// Calculate where the edge of the line should be so it goes from edge of Node A to edge of Node B
		double epX = eA.getCenterX() - eB.getCenterX();
		double epY = eA.getCenterY() - eB.getCenterY();
		
		double hyp = Math.sqrt(Math.pow(epX, 2) + Math.pow(epY, 2));
		
		double xPrime = (radius/2) * (epX / hyp);
		double yPrime = (radius/2) * (epY / hyp);
		
		connections.add(new Line2D.Double(eA.getCenterX() - xPrime, eA.getCenterY() - yPrime, eB.getCenterX() + xPrime, eB.getCenterY() + yPrime));
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
		int lim = radius * 3;
		xval += lim;
		
		if (topologyCanvas.getWidth() - xval < lim) {
			yval += lim + radius + 5; 
			xval = lim;
		}
		alternate = !alternate;
		if (alternate) {
			return new double[] {xval, yval};
		}
		else return new double[] {xval, yval + radius + 5};
		
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
		String s = tfNewConnections.getText();
		tfNewConnections.setText("");
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
		try {
			int x = Integer.parseInt(tfSimSteps.getText());
			x--;
			if (x < 0) x = 0;
			tfSimSteps.setText("" + x);
		} catch (NumberFormatException e) {
			tfSimSteps.setText("0");
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
		
		// Find connection by its line end points (which are at edge of ellipses).
		for (Line2D l : connections) {
			if (Math.abs(l.getX1() - x1) < radius + 1 &&
				Math.abs(l.getY1() - y1) < radius + 1 &&
				Math.abs(l.getX2() - x2) < radius + 1 &&
				Math.abs(l.getY2() - y2) < radius + 1) {
				return l;
			}
			else if (Math.abs(l.getX1() - x2) < radius + 1 &&
					Math.abs(l.getY1() - y2) < radius + 1 &&
					Math.abs(l.getX2() - x1) < radius + 1 &&
					Math.abs(l.getY2() - y1) < radius + 1) {
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
	public void reset() {
		nodes.clear();
		nodeNames.clear();
		nodeMessages.clear();
		connections.clear();
		connectionColors.clear();
		tfNewNodeName.setText("");;
		tfNodeToConnect.setText("");
		tfNewConnections.setText("");
		tfDeleteNodeName.setText("");
		tfDeleteConnection.setText("");
		
		xval = 0;
		yval = 0;
		updateTopologyPanel();
	}
	@Override
	public String getSelectedAlgorithm() {
		return (String)algorithmList.getSelectedItem();
	}

	@Override
	public String getNodeNameToDelete() {
		String s = tfDeleteNodeName.getText();
		tfDeleteNodeName.setText("");
		return s;
	}

	@Override
	public String getConnectionToDelete() {
		String s = tfDeleteConnection.getText();
		tfDeleteConnection.setText("");
		return s;
	}
	
	public static void main(String[] args) {
		
		SimTopologyView sView = new SimTopologyView();
		Simulation model = new Simulation();
		SimController c = new SimController(sView, model);
		
		sView.setActionListener(c);
	}

	@Override
	public int getSendRate() {
		try {
			int x = Integer.parseInt(tfSendRate.getText());
			return x;
		} catch (NumberFormatException e) { 
			return -1;
		}
		
	}

	@Override
	public int getSimSteps() {
		try {
			int x = Integer.parseInt(tfSimSteps.getText());
			return x;
		} catch (NumberFormatException e) {
			tfSimSteps.setText("0");
			return -1;	
		}
	}
	
	@Override
	public void setOutput(String text) {
		outputText.setText(text);
	}
}
