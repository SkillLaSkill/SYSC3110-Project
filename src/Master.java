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
	private JTextField nameTextField, conNameTextField, conNodeTextField;
	// Generates the index of the next node
	private Random rand = new Random();
	//Counts the number of packets required to reach the end
	private int randomPackets = 0;
	
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
        
        // Adds Run Test Method to JMenuBar
        JMenuItem newItem = new JMenuItem("Run Test Method", 'R');
        newItem.addActionListener(this);
        fileMenu.add(newItem);
        
        // Adds Display Method to JMenuBar
        JMenuItem displayItem = new JMenuItem("Display Nodes and Connections", 'D');
        displayItem.addActionListener(this);
        fileMenu.add(displayItem);
        
        // Adds Random Search Method to JMenuBar
        JMenuItem randomItem = new JMenuItem("Random Search", 'R');
        randomItem.addActionListener(this);
        fileMenu.add(randomItem);
        
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);
		
        // Creates Text Field and Button to Make New Node
		JLabel nameLabel = new JLabel("Name: ");
		contentPane.add(nameLabel);
	    nameTextField = new JTextField(25);
		contentPane.add(nameTextField);
		
		JButton nameButton = new JButton("Create Node");
		nameButton.addActionListener(this);
		contentPane.add(nameButton);
		
		JLabel conLabel = new JLabel("");
		contentPane.add(conLabel);
		
		// Creates Text Fields and Button to Make Connection To a Node
		JLabel con1Label = new JLabel("		Given node name to: ");
		contentPane.add(con1Label);
		conNodeTextField = new JTextField(25);
		contentPane.add(conNodeTextField);
		
		JLabel con2Label = new JLabel("		Connected to: ");
		contentPane.add(con2Label);
	    conNameTextField = new JTextField(25);
		contentPane.add(conNameTextField);
		
		JButton conButton = new JButton("Create connections for node");
		conButton.addActionListener(this);
		contentPane.add(conButton);
		
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
	}
	
	/**
	 * Random routing simulator
	 * 
	 * @param node (Node)
	 * @param incomingMessage (String)
	 * @param destination (Node)
	 */
	public void receiveRandomMessage(Node node, String incomingMessage, Node destination) {
		// Sets the message and increaments the number of packets sent
		node.setMessage(incomingMessage);
		randomPackets++;
		
		// Delay to see things as they're happening.
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		// Get the next (random) node from node's list of connections
		List<String> cons = node.getConnections();
		int nextNodeIndex = rand.nextInt(cons.size());
		Node nextNode = getNode(cons.get(nextNodeIndex));
		
		// Prints the change name as well as the next node that the message will be sent to as long as it didn't reach the destinations
		System.out.println("Changed node: " + node.getName() + " with message: " + incomingMessage);
		if(node.equals(destination)) return;
		System.out.println("Next node: " + nextNode.getName());
		
		// Sends message to the next node
		receiveRandomMessage(nextNode, incomingMessage, destination);
	}

	/**
	 * Displays all nodes information
	 */
	public void displayNodes(){
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
			allNodes.add(new Node(nameTextField.getText()));
		}
		// User added connections to the node
		else if(actionCommand.equals("Create connections for node")) {
			Node n =  getNode(conNodeTextField.getText());
			nodeConnections(n, conNameTextField.getText());
		}
		// User runs test method that creates text nodes with connections
		else if(actionCommand.equals("Run Test Method")) {
			runTest();
		}
		// User wants to display all the nodes with their connections
		else if(actionCommand.equals("Display Nodes and Connections")) {
			displayNodes();
		}
		// User wants to run the random routing simulator
		else if(actionCommand.equals("Random Search")) {
			if (allNodes.size() == 0){
				System.out.println("Need to run tests first");
				return;
			}
			receiveRandomMessage(getNode("A"), "Test", getNode("E"));
			System.out.println("Total number of random packets sent: " + randomPackets);
		}
	}
}
