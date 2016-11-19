import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

public class SimGUI extends JFrame
{
// <<< Class Variables >>>
	//Basic Window Construction
	private JPanel consolePanel;
	
	//References
	private SimController controller;
	private Simulation model;
	
	//Toplogy variables
	private NodeDisplayPanel topologyCanvas;
	private List<Ellipse2D> graphNodes;
	private List<Line2D> graphConnections;
	private List<String> nodeNames;
		//Topology values
	private final int radius = 40;
	private int xval = 0;
	private int yval = 0;
	private boolean alternate = false;
	
	public SimGUI()
	{
		//Initial settings
		this.setTitle("Topology View");
		this.setLayout(new GridBagLayout());
		this.model = new Simulation();
		this.controller = new SimController(this, model);
		
		//Panel setup
		consolePanel = new JPanel();
		topologyCanvas = new NodeDisplayPanel();
		
		//Right click menu
		this.addMouseListener(new RightClickListener());
		
		//Menu Bar
		JMenuItems
		JMenu menu = new JMenu("File");
		JMenuBar bar = new JMenuBar();
		//Final settings
		this.setSize(500, 500);
		this.setVisible(true);
	}
	
	/*
	 * Creates pop-up prompt with given text and returns the String entered
	 */
	public String createPrompt(String q)
	{
		return (String)JOptionPane.showInputDialog(
                this, q);
	}
	
	
	public static void main(String[] args)
	{
		new SimGUI();
	}
	
// <<< Mutators >>>
	
	/**
	 * Adds a node to the Topological View
	 */
	public void addNode(String n) {
		// Find where x, y should be.
		nodeNames.add(n);
		
		double[] loc = findGoodXY();
		graphNodes.add(new Ellipse2D.Double(loc[0], loc[1], radius, radius));
		nodeMessages.add(new ArrayList<String>());
		
		updateTopologyPanel();
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
			for (int i = 0; i < graphNodes.size(); i++) {
				g2d.draw(graphNodes.get(i));
				double nameX = graphNodes.get(i).getCenterX();
				double nameY = graphNodes.get(i).getCenterY();
				g2d.drawString(nodeNames.get(i), (int)nameX, (int)nameY);
				
				/*StringBuilder sb = new StringBuilder();
				for (int j = 0; j < nodeMessages.get(i).size(); j++) {
					sb.append(nodeMessages.get(i).get(j) + ", ");
				}*/
				if (sb.length() != 0) {
					sb.deleteCharAt(sb.length()-1);
					sb.deleteCharAt(sb.length()-1);
					g2d.drawString(sb.toString(), (int)nameX + radius/2, (int)nameY);
				}			
			}
			for (int i = 0; i < graphConnections.size(); i++) {
				Line2D l = graphConnections.get(i);
				//g2d.setColor(connectionColors.get(i));
				g2d.draw(l);
			}
		}
	}
}
