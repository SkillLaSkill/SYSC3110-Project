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

import javax.swing.*;

public class SimGUI extends JFrame
{
// <<< Class Variables >>>
	//Basic Window Construction
	//private NodeDisplayPanel topologyCanvas;
	private JPanel consolePanel;
	
	//References
	private SimController controller;
	private Simulation model;
	
	//Toplogy variables
	//private HashMap<String, Ellipse2D> graphNodes;
	private List<Ellipse2D> graphNodes;
	private List<Line2D> graphConnections;
	private List<String> nodeNames;
	private List<List<String>> nodeMessages = new ArrayList<>();
	//Topology values
	private final int radius = 40;
	private int xval;
	private int yval;
	private boolean alternate;
	
	public SimGUI()
	{
		//Variable initializations
		//graphNodes = new HashMap<String, Ellipse2D>();
		graphNodes = new ArrayList<Ellipse2D>();
		graphConnections = new ArrayList<Line2D>();
		nodeNames = new ArrayList<String>();
		alternate = false;
		xval = 0;
		yval = 0;
		
		//Initial settings
		this.setTitle("Topology View");
		this.setLayout(new GridBagLayout());
		this.model = new Simulation();
		this.controller = new SimController(this, model);
		
		//Panel setup (Want to add consolePanel as a display of console)
		
		//Right click menu
		this.addMouseListener(new RightClickListener());
		
		//Menu Bar
		//JMenuItems
		JMenu menu = new JMenu("File");
		JMenuBar bar = new JMenuBar();
		
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
		//this.getContentPane().add(topologyCanvas, c);
        
        //Final settings
      	this.setSize(500, 500);
      	this.setVisible(true);
      	this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
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
		
	}
	
	/*
	 * Gracefully closes the GUI
	 */
	public void close()
	{
		this.setVisible(false);
		this.dispose();
	}
	
	@Override
	public void reset() {
		graphNodes.clear();
		nodeNames.clear();
		nodeMessages.clear();
		graphConnections.clear();
		
		xval = 0;
		yval = 0;
		updateTopologyPanel();
	}
	
	/*
	 * Main of SimGUI, starts this whole monster!
	 */
	public static void main(String[] args)
	{
		new SimGUI();
	}
	
// <<< Mutators >>>
	
	/**
	 * Adds a node to the Topological View
	 */
	/*public void addNode(String n) {
		// Find where x, y should be.
		nodeNames.add(n);
		
		double[] loc = findGoodXY();
		graphNodes.add(new Ellipse2D.Double(loc[0], loc[1], radius, radius));
		nodeMessages.add(new ArrayList<String>());
		
		updateTopologyPanel();
	}*/
	
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
	/*private class NodeDisplayPanel extends JPanel {	
		private static final long serialVersionUID = 1L;

		@Override 
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g;
			g2d.setColor(Color.black);
			for (int i = 0; i < graphNodes.size(); i++) {
				g2d.draw(graphNodes.get(i));
				double nameX = graphNodes.get(i).getCenterX();
				double nameY = graphNodes.get(i).getCenterY();
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
			for (int i = 0; i < graphConnections.size(); i++) {
				Line2D l = graphConnections.get(i);
				g2d.setColor(connectionColors.get(i));
				g2d.draw(l);
			}
		}
	}*/
}
