import javax.swing.*;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

public class SimTopologyView implements ViewStrategy{
	JFrame mainFrame;;
	private NodeDisplayPanel topologyCanvas;
	private List<Ellipse2D> nodes = new ArrayList<>();
	private List<String> nodeNames = new ArrayList<>();
	private List<Line2D> connections = new ArrayList<>();
	private int xval = 0;
	private int yval = 0;
	private final int radius = 40;
	
	public SimTopologyView() {
		initializeComponents();
	}
	private void initializeComponents() {
		mainFrame = new JFrame("Topology View");
		topologyCanvas = new NodeDisplayPanel();
		mainFrame.add(topologyCanvas);
		
		mainFrame.setSize(600, 500);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		mainFrame.setVisible(true);
	}
	
	
	public void addNode(Node n) {
		// Find where x, y should be.
		nodeNames.add(n.getName());
		
		int[] loc = findGoodXY();
		nodes.add(new Ellipse2D.Double(loc[0], loc[1], radius, radius));
	}
	public void removeNode(Node n) {
		int idx = -1;
		for (int i = 0; i < nodeNames.size(); i++) {
			if (nodeNames.get(i).equals(n.getName())) {
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
		if (mainFrame.getWidth() - xval < 150) {
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
