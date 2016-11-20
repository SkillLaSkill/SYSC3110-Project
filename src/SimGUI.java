import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.*;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.swing.*;

public class SimGUI extends JFrame
{
// <<< Class Variables >>>
	//Basic Window Construction
	private NodeDisplayPanel topologyCanvas;
	private JPanel consolePanel;
	
	//References
	private SimController controller;
	private Simulation model;
	private GUINodeList nodeList;
	
	//Topology values
	private final int radius = 40;
	private int xval;
	private int yval;
	private boolean alternate;
	
	public SimGUI()
	{
		//Variable initializations
		nodeList = new GUINodeList();
		//graphNodes = new HashMap<String, Ellipse2D>();
		//graphConnections = new ArrayList<Line2D>();
		alternate = false;
		xval = 0;
		yval = 0;
		this.model = new Simulation(this);
		this.controller = new SimController(this, model);
		
		//Initial settings
		this.setTitle("Topology View");
		this.setLayout(new GridBagLayout());

		
		//Panel setup (Want to add consolePanel as a display of console)
		topologyCanvas = new NodeDisplayPanel();
		JTextArea console = new JTextArea();
		console.setText("Whaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
		//this.add(topologyCanvas);
		//this.add(console);
		
		//Right click menu
		this.addMouseListener(new RightClickListener());
		
		
		//Menu Bar setup
		JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem simulate = new JMenuItem("Simulate");
        simulate.addActionListener(controller);
        JMenuItem reset = new JMenuItem("Reset");
        reset.addActionListener(controller);
        JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener(controller);
        fileMenu.add(simulate);
        fileMenu.add(reset);
        fileMenu.add(exit);
        menuBar.add(fileMenu);
        this.setJMenuBar(menuBar);
        
        //Topology View Setup
        GridBagConstraints c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 0;
		c.weightx = 1;
		c.weighty = 1;
		c.fill = GridBagConstraints.BOTH;
		this.add(topologyCanvas, c);
        
        //Final settings
      	this.setSize(500, 500);
      	this.setVisible(true);
      	this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	/*
	 * Finds a suitable X and Y co-ordinates for a new node
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
	 * Update the Topological View
	 */
	private void updateTopologyPanel() {
		topologyCanvas.validate();
		topologyCanvas.repaint();
	}
	
	/*
	 * Creates pop-up prompt with given text and returns the String entered
	 */
	public String createPrompt(String q)
	{
		return (String)JOptionPane.showInputDialog(
                this, q);
	}
	
	/*
	 * Updates all necessary GUI fields
	 */
	public void update()
	{
		reset();
		
		//Add all nodes to graph
		for(Node node: model.getGraph().getNodes())
		{
			double[] loc = findGoodXY();
			Ellipse2D gNode = new Ellipse2D.Double(loc[0], loc[1], radius, radius);
			nodeList.addGUINode(node.getName(),gNode);
			
		}
		
		//Add all connections
		for(Node node: model.getGraph().getNodes())
		{
			for(Node con: model.getGraph().getConnections(node))
			{
				Ellipse2D eA = nodeList.getEllipse(node.getName());
				Ellipse2D eB = nodeList.getEllipse(con.getName());
				
				// Calculate where the edge of the line should be so it goes from edge of Node A to edge of Node B
				double epX = eA.getCenterX() - eB.getCenterX();
				double epY = eA.getCenterY() - eB.getCenterY();
				
				double hyp = Math.sqrt(Math.pow(epX, 2) + Math.pow(epY, 2));
				
				double xPrime = (radius/2) * (epX / hyp);
				double yPrime = (radius/2) * (epY / hyp);
				
				nodeList.addConnection(new Line2D.Double(eA.getCenterX() - xPrime, eA.getCenterY() - yPrime, eB.getCenterX() + xPrime, eB.getCenterY() + yPrime));
			}
		}
		updateTopologyPanel();
		
	}
	
	/*
	 * Gracefully closes the GUI
	 */
	public void close()
	{
		this.setVisible(false);
		this.dispose();
	}
	
	/*
	 * Resets the GUI interface to a fully cleared state
	 */
	public void reset() {
		nodeList.clear();
		
		xval = 0;
		yval = 0;
		//updateTopologyPanel();
	}
	
	/*
	 * Main of SimGUI, starts this whole monster!
	 */
	public static void main(String[] args)
	{
		new SimGUI();
	}
	
// <<< Local classes >>>
	//Right click menu
	class RightClickMenu extends JPopupMenu {
	    JMenuItem createNode;
	    public RightClickMenu(){
	    	
	    	//Add Node
	        createNode = new JMenuItem("Create Node");
	        createNode.setActionCommand("Create Node");
	        createNode.addActionListener(controller);
	        this.add(createNode);
	        
	        //Remove Node
	        JMenuItem deleteNode = new JMenuItem("Delete Node");
	        deleteNode.setActionCommand("Delete Node");
	        deleteNode.addActionListener(controller);
	        this.add(deleteNode);
	        
	        //Add Connection
	        JMenuItem addConnection = new JMenuItem("Add Connection");
	        addConnection.setActionCommand("Add Connection");
	        addConnection.addActionListener(controller);
	        this.add(addConnection);
	        
	        //Delete Connection
	        JMenuItem deleteConnection = new JMenuItem("Delete Connection");
	        deleteConnection.setActionCommand("Delete Connection");
	        deleteConnection.addActionListener(controller);
	        this.add(deleteConnection);
	    }
	}
	
	//Right click listener
	class RightClickListener extends MouseAdapter {
	    public void mousePressed(MouseEvent e){
	        if (e.isPopupTrigger())
	            doPop(e);
	    }

	    public void mouseReleased(MouseEvent e){
	        if (e.isPopupTrigger())
	            doPop(e);
	    }

	    private void doPop(MouseEvent e){
	        RightClickMenu menu = new RightClickMenu();
	        menu.show(e.getComponent(), e.getX(), e.getY());
	    }
	}
	
	
	//Graph view class
	private class NodeDisplayPanel extends JPanel {	
		private static final long serialVersionUID = 1L;

		@Override 
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g;
			g2d.setColor(Color.black);
			
			for (String nodeName : nodeList) {
				Ellipse2D el = nodeList.getEllipse(nodeName);
				g2d.draw(el);
				
				double nameX = el.getCenterX();
				double nameY = el.getCenterY();
				
				g2d.drawString(nodeName, (int)nameX, (int)nameY);
				
				StringBuilder sb = new StringBuilder();
				/*
				for (String message : nodeList.getMessages(nodeName)) {
					sb.append(message + ", ");
				}*/
				
				if (sb.length() != 0) {
					sb.deleteCharAt(sb.length()-1);
					sb.deleteCharAt(sb.length()-1);
					g2d.drawString(sb.toString(), (int)nameX + radius/2, (int)nameY);
				}
				
				// Draw connections.
				for (Line2D conn : nodeList.getAllConnections()) {
					g2d.draw(conn);
				}
			}
			
			/*for (String key : graphNodes.keySet()) {
				g2d.draw(graphNodes.get(key));
				double nameX = graphNodes.get(key).getCenterX();
				double nameY = graphNodes.get(key).getCenterY();
				ArrayList<String> nodeNames = (ArrayList<String>) graphNodes.keySet();
				g2d.drawString(nodeNames.get(key), (int)nameX, (int)nameY);
				
				StringBuilder sb = new StringBuilder();
				for (int j = 0; j < nodeMessages.get(i).size(); j++) {
					sb.append(nodeMessages.get(key).get(j) + ", ");
				}
				if (sb.length() != 0) {
					sb.deleteCharAt(sb.length()-1);
					sb.deleteCharAt(sb.length()-1);
					g2d.drawString(sb.toString(), (int)nameX + radius/2, (int)nameY);
				}
			}

			for (int i = 0; i < graphConnections.size(); i++) {
				
			}*/
		}
	}
}