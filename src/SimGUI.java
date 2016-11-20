import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.*;

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
		//Frame Icon (Kappa TM)
		ImageIcon img = new ImageIcon("src/218.png");
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
        JMenuItem refresh = new JMenuItem("Refresh");
        refresh.addActionListener(controller);
        JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener(controller);
        fileMenu.add(simulate);
        fileMenu.add(reset);
        fileMenu.add(refresh);
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
		alternate = false;
		xval = 0;
		yval = 0;
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
		private static final long serialVersionUID = 1L;
		JMenuItem createNode;
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
				
				//Adds node name as text on top of Node
				StringBuilder sb = new StringBuilder();
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
