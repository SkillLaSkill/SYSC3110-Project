import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

/**
 * Creates the GUI in which the simulation will be displayed. This GUI will show the nodes
 * as well as lines connecting them together.
 * 
 * @author Team GetterDone
 * 
 */
public class SimGUI extends JFrame implements ViewStrategy
{
	private static final long serialVersionUID = 1L;
	//Basic Window Construction
	private NodeDisplayPanel topologyCanvas;
	private JPanel simulationControl;
	private JTextArea metrics;
	
	//References
	private SimController controller;
	private Simulation model;
	private GUINodeList nodeList;
	
	//Topology values
	private final int radius = 40;
	private int xval;
	private int yval;
	private boolean alternate;
	
	/**
	 * Creates the simulation GUI
	 */
	public SimGUI()
	{
	//Frame Icon (Kappa TM)
		ImageIcon img = new ImageIcon("src/pepe.jpg");
		this.setIconImage(img.getImage());
		
	//Variable initializations
		nodeList = new GUINodeList();
		alternate = false;
		xval = 0;
		yval = 0;
		this.model = new Simulation(this);
		this.controller = new SimController(this, model);
		
	//Initial settings
		this.setTitle("Topology View");
		this.setLayout(new GridBagLayout());
		
	//GridBag Layout
        GridBagConstraints a = new GridBagConstraints();
		a.gridx = 0;
		a.gridy = 0;
		a.weightx = 1;
		a.weighty = 1;
		a.fill = GridBagConstraints.BOTH;
		GridBagConstraints b = new GridBagConstraints();
		b.gridx = 0;
		b.gridy = 1;
		b.weightx = 0;
		b.weighty = 0;
		b.fill = GridBagConstraints.BOTH;
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 0;
		c.weightx = 0;
		c.weighty = 0;
		c.gridwidth = 200;
		c.fill = GridBagConstraints.BOTH;
		
	//Panel setup 
		topologyCanvas = new NodeDisplayPanel();
		//Sim control buttons
		simulationControl = new JPanel();
		JButton step = new JButton("Step Simulation");
		step.addActionListener(controller);
		JButton startSim = new JButton("Start Simulation");
		startSim.addActionListener(controller);
		JButton back = new JButton("Step Back");
		back.addActionListener(controller);
		simulationControl.add(step);
		simulationControl.add(back);
		simulationControl.add(startSim);
		//Text Area 
		metrics = new JTextArea("Metrics");
		JPanel display = new JPanel();
		metrics.setEditable(false);
		metrics.setText("Please build a graph and start a simulation");
		JScrollPane scroll = new JScrollPane(metrics);
		scroll.setPreferredSize(new Dimension(200, 350));
		display.add(scroll);
		this.add(topologyCanvas, a);
		this.add(simulationControl, b);
		this.add(display, c);
		
		
		
	//Right click menu
		topologyCanvas.addMouseListener(new RightClickListener());		
		
	//Menu Bar setup 
		JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenu textMenu = new JMenu("Edit");
        JMenuItem select = new JMenuItem("Select Algorithm");
        select.addActionListener(controller);
        JMenuItem reset = new JMenuItem("Reset");
        reset.addActionListener(controller);
        JMenuItem refresh = new JMenuItem("Refresh");
        refresh.addActionListener(controller);
        JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener(controller);
        JMenuItem saveState = new JMenuItem("Save State");
        saveState.addActionListener(controller);
        JMenuItem importState = new JMenuItem("Import State");
        importState.addActionListener(controller);
       
        fileMenu.add(select);
        fileMenu.add(reset);
        fileMenu.add(refresh);
        fileMenu.add(saveState);
        fileMenu.add(importState);
        fileMenu.add(exit);
  
        menuBar.add(fileMenu);
        JMenuItem clear = new JMenuItem("Clear Text");
        clear.addActionListener(controller);
        JMenuItem construct = new JMenuItem("Construct Graph");
        construct.addActionListener(controller);
        textMenu.add(clear);
        textMenu.add(construct);
        menuBar.add(textMenu);
        this.setJMenuBar(menuBar);
        
        
     //Final settings
      	this.setSize(750, 500);
      	this.setVisible(true);
      	this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	/**
	 * Finds a suitable X and Y co-ordinates for a new node
	 * 
	 * @return double[] - Array containing the X,Y coordinates
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
	 
	/**
	 * Appends text to the Text Window
	 * @param s
	 */
	public void addText(String s)
	{
		metrics.append(s);
	}
	
	/**
	 * Clears the Text Window
	 */
	public void clearText()
	{
		metrics.setText("");
	}
	
	/**
	 * Creates pop-up prompt with given text and returns the String entered
	 */
	public String createPrompt(String q)
	{
		return (String)JOptionPane.showInputDialog(
                this, q);
	}
	
	/**
	 * Create combo box prompt
	 */
	public String comboPrompt(String[] choices)
	{
		return (String)	JOptionPane.showInputDialog(null, "Choose algorithm", "Start Simulation", JOptionPane.QUESTION_MESSAGE
				, null, choices, choices[0]);
	}
	
	
	/**
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
			
			// Have GUINodeList contain messages within the node.
			List<String> messages = new ArrayList<>();
			for (Packet p : node.getPackets()) {
				messages.add(p.getMessage());
			}
			nodeList.addGUINode(node.getName(),gNode, messages);
		}
		
		//Add all connections
		for(Node node: model.getGraph().getNodes())
		{
			for(Node con: node.getConnections())
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
	
	/**
	 * Gracefully closes the GUI
	 */
	public void close()
	{
		this.setVisible(false);
		this.dispose();
	}
	
	/**
	 * Resets the GUI interface to a fully cleared state
	 */
	public void reset() {
		nodeList.clear();
		alternate = false;
		xval = 0;
		yval = 0;
		this.clearText();
	}
	
	/**
	 * Main of SimGUI, starts this whole monster!
	 */
	public static void main(String[] args)
	{
		new SimGUI();
	}
	
// <<< Local classes >>>
	/**
	 * Creates an instance of right click that will allow the user to do
	 * multiple actions
	 * 
	 * @author Team GetterDone
	 *
	 */
	class RightClickMenu extends JPopupMenu { 
		private static final long serialVersionUID = 1L;
		JMenuItem createNode;
		
		/**
		 * Right click menu for the graph
		 */
	    public RightClickMenu(){
	    	
	    	//Add Node
	        createNode = new JMenuItem("Create Nodes");
	        createNode.setActionCommand("Create Nodes");
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

	
	/**
	 * Creates a mouse listener for the right click menu
	 * 
	 * @author Team GetterDone
	 */
	class RightClickListener extends MouseAdapter {
	
		/**
		 * Does action for when the mouse is pressed
		 */
	    public void mousePressed(MouseEvent e){
	        if (e.isPopupTrigger())
	            doPop(e);
	    }

		/**
		 * Does action for when the mouse is released
		 */
	    public void mouseReleased(MouseEvent e){
	        if (e.isPopupTrigger())
	            doPop(e);
	    }

		/**
	     * Creates the menu at the mouses position
	     * 
	     * @param e (MouseEvent) - The mouse event that occurred 
	     */

	    private void doPop(MouseEvent e){
	        RightClickMenu menu = new RightClickMenu();
	        menu.show(e.getComponent(), e.getX(), e.getY());
	    }
	}
	
	
		/**
	 * Creates the node display panel which displays the nodes as well as connections.
	 * This is updated.
	 * 
	 * @author Team GetterDone
	 */
	private class NodeDisplayPanel extends JPanel {	
		private static final long serialVersionUID = 1L;

		/**
		 * Creates the node display
		 */
		@Override 
		public void paintComponent(Graphics g) 
		{
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g;
			g2d.setColor(Color.black);
			
			for (String nodeName : nodeList) 
			{
				//Makes the circle representation for the node
				Ellipse2D el = nodeList.getEllipse(nodeName);
				g2d.draw(el);
				double nameX = el.getCenterX();
				double nameY = el.getCenterY();
				g2d.drawString(nodeName, (int)nameX, (int)nameY);
				
				//Adds node messages as text on top of Node
				StringBuilder sb = new StringBuilder();
				for (String s : nodeList.getMessages(nodeName)) {
					sb.append(s + ", ");
				}
				if (sb.length() != 0) 
				{
					sb.deleteCharAt(sb.length()-1);
					sb.deleteCharAt(sb.length()-1);
					g2d.drawString(sb.toString(), (int)nameX + radius/2, (int)nameY);
				}
					
				
				// Draw connections via lines
				for (Line2D conn : nodeList.getAllConnections()) g2d.draw(conn);
			}

		}
	}
}
