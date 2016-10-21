import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.*;
import javax.swing.*;

/**
 * This class creates the GUI that the user uses to create nodes,
 * create connections, and run the random routing simulator
 * 
 * @author Team GetterDone
 *
 */
public class Master extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	// Holds all nodes that are made
	public List<Node> allNodes = new ArrayList<Node>();	
	// Variables used to make GUI
	public static final int WIDTH = 400;
	public static final int HEIGHT = 250;
	private JTextField nameTextField, conNameTextField, conNodeTextField/*, srcNodeTextField, destNodeTextField*/;
	// Generates the index of the next node
	private Random rand = new Random();
	
	/**
	 * Generates the GUI
	 */
	public Master() {
		// Creates basic JFrame with container
		setSize(WIDTH,HEIGHT); 
		setTitle("Address Book");
		Container contentPane = getContentPane();
		contentPane.setBackground(Color.PINK);
		contentPane.setLayout(new GridLayout(8,2));
		
		// Creates JMenu bar
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        
        // Adds Display Method to JMenuBar
        JMenuItem displayItem = new JMenuItem("Display Nodes and Connections", 'D');
        displayItem.addActionListener(this);
        fileMenu.add(displayItem);
        
        // Adds Run Test Method to JMenuBar
        JMenuItem newItem = new JMenuItem("Run Test Method");
        newItem.addActionListener(this);
        fileMenu.add(newItem);
         
        
        // Adds Random Search Method to JMenuBar
        JMenuItem reset = new JMenuItem("Reset", 'R');
        reset.addActionListener(this);
        fileMenu.add(reset);
        
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);
		
        // Creates Text Field and Button to Make New Node
		JLabel nameLabel = new JLabel("New node name: ");
		contentPane.add(nameLabel);
	    nameTextField = new JTextField(25);
		contentPane.add(nameTextField);
		
		JButton nameButton = new JButton("Create Node");
		nameButton.addActionListener(this);
		contentPane.add(nameButton);
		
		JLabel conLabel = new JLabel("");
		contentPane.add(conLabel);
		
		// Creates Text Fields and Button to Make Connection To a Node
		JLabel con1Label = new JLabel("Node to set connections:");
		contentPane.add(con1Label);
		conNodeTextField = new JTextField(25);
		contentPane.add(conNodeTextField);
		
		JLabel con2Label = new JLabel("List of connections: ");
		contentPane.add(con2Label);
	    conNameTextField = new JTextField(25);
		contentPane.add(conNameTextField);
		
		JButton conButton = new JButton("Establish Connections");
		conButton.addActionListener(this);
		contentPane.add(conButton);
		
		JLabel con3Label = new JLabel("");
		contentPane.add(con3Label);
		
		JButton simStartButton = new JButton("Start Simulation");
		simStartButton.addActionListener(this);
		
		/*
		JLabel srcLabel = new JLabel("Source Node:");
		contentPane.add(srcLabel);
		srcNodeTextField = new JTextField();
		contentPane.add(srcNodeTextField);
		
		JLabel destLabel = new JLabel("Destination Node:");
		contentPane.add(destLabel);
		destNodeTextField = new JTextField();
		contentPane.add(destNodeTextField);*/
		
		contentPane.add(simStartButton);
		
		// Makes it so the program closes when you close the GUI
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	/**
	 * Creates a new node and adds it to the list of all nodes
	 * 
	 * @param nodeName (String)
	 * @return Node
	 */
	public Node newNode(String nodeName) {
		Node n = new Node(nodeName);
		allNodes.add(n);
		return n;
	}
	
	/**
	 * Gets a node given its name
	 * 
	 * @param name (String)
	 * @return Node
	 */
	public Node getNode(String name){
		// Goes through all nodes made
		for (int i = 0; i < allNodes.size(); i++){
			// Returns node with the given name
			if (allNodes.get(i).getName().equals(name)) {
				return allNodes.get(i);
			}
		}
		// Returns null if the node name doesn't exist
		return null;
	}
	
	/**
	 * Creates connections for the given node using given connections
	 * 
	 * @param node (Node)
	 * @param connections (String)
	 */
	public void nodeConnections(Node node, String connections) {
		// Separates all connections into individual strings
		String[] nodeConnections = connections.split(" ");
		for (String con : nodeConnections) {
			Node n = getNode(con);
			if (n == null) {
				System.out.println("There were non-existent nodes in the connections list.");
				return;
			}
			// To keep the graph undirected, need to make sure all nodes have connections backwards.
			n.addConnection(node.getName());
		}
		// Adds the connections to the node
		node.addConnections(nodeConnections);
	}
	
	/**
	 * Creates the nodes with connections as shown in the projects specifications (for test)
	 */
	public void runTest(){
		// Creates all nodes.
		Node A = newNode("A");
		Node B = newNode("B");
		Node C = newNode("C");
		Node D = newNode("D");
		Node E = newNode("E");
		
		// Adds all connections to the nodes.
		nodeConnections(A, "B C");
		nodeConnections(B, "A D E");
		nodeConnections(C, "A D");
		nodeConnections(D, "B C");
		nodeConnections(E, "A B");
		
		int hops = receiveRandomMessage(A, "Test", E);
		System.out.println("Total number of hops for node " + A.getName() + " to get to " + E.getName() + " was " + hops + ".");
	}
	

	/**
	 * Displays all nodes information
	 */
	public void displayNodes(){
		if (allNodes.size() == 0) {
			System.out.println("No nodes.");
		}
		for (Node n : allNodes){
			n.displayNode();
		} 
	}
	
	/**
	 * Creates new master program, making the GUI
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Master m = new Master();
		m.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		
		// User created new node in container(no connections yet)
		if(actionCommand.equals("Create Node")) {
			if (nameTextField.getText().isEmpty()) {
				return;
			}
			Node n = new Node(nameTextField.getText());
			
			if (allNodes.contains(n)) {
				System.out.println("Cannot add duplicate nodes.");
				return;
			}
			allNodes.add(n);
			nameTextField.setText("");
		}
		// User added connections to the node
		else if(actionCommand.equals("Establish Connections")) {
			String s = conNodeTextField.getText();
			if (s.isEmpty()) return;
			
			Node n =  getNode(s);
			if (n == null) {
				System.out.println("Node \"" + s + "\" does not exist.");
				return;
			}
			
			String connList = conNameTextField.getText();
			
			nodeConnections(n, connList);
			
			conNodeTextField.setText("");
			conNameTextField.setText("");
		}
		// User runs test method that creates text nodes with connections
		else if(actionCommand.equals("Run Test Method")) {
			runTest();
		}
		// User wants to display all the nodes with their connections
		else if(actionCommand.equals("Display Nodes and Connections")) {
			displayNodes();
		}
		
		else if(actionCommand.equals("Start Simulation")) {
			if (allNodes.size() == 0) {
				System.out.println("Need to set up nodes and connections.");
				return;
			}
			Simulation s = new Simulation(allNodes);
			s.start();
			
			while (true) {
				
				
				/*if (src == null || dest == null) {
					System.out.println("Source and Destination nodes must exist.");
					return;
				}*/
				
			}
			
		}
		else if (actionCommand.equals("Reset")) {
			allNodes.clear();
			nameTextField.setText("");
			conNameTextField.setText(""); 
			conNodeTextField.setText("");
			/*srcNodeTextField.setText("");
			destNodeTextField.setText("");*/
		}
	}
}
