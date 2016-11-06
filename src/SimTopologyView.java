import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

public class SimTopologyView implements ViewStrategy{
	private JFrame mainFrame;
	private JPanel optionsPane;
	private NodeDisplayPanel topologyCanvas;
	private List<Ellipse2D> nodes = new ArrayList<>();
	private List<String> nodeNames = new ArrayList<>();
	private List<Line2D> connections = new ArrayList<>();
	private int xval = 0;
	private int yval = 0;
	private final int radius = 40;
	
	// Text fields used for creation of nodes/connections
	private JTextField newNodeNameTF;
	private JTextField nodeToConnectTF;
	private JTextField newConnectionsTF;
	
	
	public SimTopologyView() {
		initializeComponents();
	}
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
	
	private void setupNodeView() {
		topologyCanvas.setSize(400, 400);
		mainFrame.getContentPane().add(topologyCanvas, BorderLayout.CENTER);
	}
	private void setupMenuBar() {
		JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        
        menuBar.add(fileMenu);
           
        mainFrame.setJMenuBar(menuBar);
	}
	private void setupOptionsPane() {
		mainFrame.getContentPane().add(optionsPane, BorderLayout.LINE_START);	
		
		optionsPane.setLayout(new GridLayout(5, 2));
		
        // Creates Text Field and Button to Make New Node
		JLabel nameLabel = new JLabel("New node name: ");
		optionsPane.add(nameLabel);
		
	    newNodeNameTF = new JTextField(25);
	    optionsPane.add(newNodeNameTF);
		
		JButton nameButton = new JButton("Create Node");
		//nameButton.addActionListener(this);
		optionsPane.add(nameButton);
		
		JLabel tmp1 = new JLabel("");
		optionsPane.add(tmp1);
		
		// Creates Text Fields and Button to Make Connection To a Node
		JLabel connectionLabel = new JLabel("Node to set connections:");
		optionsPane.add(connectionLabel);
		nodeToConnectTF = new JTextField(25);
		optionsPane.add(nodeToConnectTF);
				
		JLabel conLabel = new JLabel("List of connections: ");
		optionsPane.add(conLabel);
		newConnectionsTF = new JTextField(25);
		optionsPane.add(newConnectionsTF);
		
		JButton conButton = new JButton("Establish Connections");
		//conButton.addActionListener(this);
		optionsPane.add(conButton);
		JLabel tmp2 = new JLabel("");	
		optionsPane.add(tmp2);
		
		
	}
	
	
	public void addNode(Node n) {
		// Find where x, y should be.
		nodeNames.add(n.getName());
		
		int[] loc = findGoodXY();
		nodes.add(new Ellipse2D.Double(loc[0], loc[1], radius, radius));
	}
	public void removeNode(String name) {
		int idx = -1;
		for (int i = 0; i < nodeNames.size(); i++) {
			if (nodeNames.get(i).equals(name)) {
				idx = i;
				nodeNames.remove(idx);
				break;
			}
		}
		if (idx != -1) nodes.remove(idx);
	}

	public void addConnection(Node A, Node B) {
		double[] nodeACoords = getEllipseCoords(A);
		double[] nodeBCoords = getEllipseCoords(B);
	
		//System.out.println("x1y1: " + nodeACoords[0] + " " + nodeACoords[1]);
		//System.out.println("x2y2: " + nodeBCoords[0] + " " + nodeBCoords[1]);
		
		connections.add(new Line2D.Double(nodeACoords[0], nodeACoords[1], nodeBCoords[0], nodeBCoords[1]));
	}
	public void removeConnection(Node A, Node B) {
		double[] nodeACoords = getEllipseCoords(A);
		double[] nodeBCoords = getEllipseCoords(B);
		
		for (Line2D line : connections) {
			if (line.getX1() == nodeACoords[0] &&
				line.getX2() == nodeBCoords[0] &&
				line.getY1() == nodeACoords[1] &&
				line.getY2() == nodeBCoords[1]) {
				connections.remove(line);
				break;
			}
		}
	}
	
	private double[] getEllipseCoords(Node n) {
		int idx = -1;
		
		for (int i = 0; i < nodeNames.size(); i++) {
			if (nodeNames.get(i).equals(n.getName())) {
				idx = i;
				break;
			}
			
		}
		if (idx != -1) {
			double x = nodes.get(idx).getCenterX();
			double y = nodes.get(idx).getCenterY();
			return new double[] {x, y};
		}
		else return null;
	}
	
	private int[] findGoodXY() {
		// Later make sure not on / inbetween nodes 
		xval += 150;
		if (topologyCanvas.getWidth() - xval < 150) {
			yval += 150; 
			xval = 150;
		}
		
		return new int[] {xval, yval};
	}
	
	
	
	public static void main(String[] args) {
		SimTopologyView sView = new SimTopologyView();
		Node A = new Node("A");
		Node B = new Node("B");
		Node C = new Node("C");
		Node D = new Node("D");
		
		sView.addNode(A);
		sView.addNode(B);
		sView.addNode(C);
		sView.addNode(D);
		sView.addConnection(A, B);
		sView.addConnection(A, D);
		sView.addConnection(C, D);

		
	}

	private class NodeDisplayPanel extends JPanel {	
		
		@Override 
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g;
			for (int i = 0; i < nodes.size(); i++) {
				g2d.draw(nodes.get(i));
				double nameX = nodes.get(i).getCenterX();
				double nameY = nodes.get(i).getCenterY();
				g2d.drawString(nodeNames.get(i), (int)nameX, (int)nameY);
			}
			
			for (Line2D l : connections) {
				g2d.draw(l);
			}
		}
	}
}
