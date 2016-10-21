import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.*;
import javax.swing.*;

public class Master extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	public List<Node> allNodes = new ArrayList<Node>();
	public static final int WIDTH = 400;
	public static final int HEIGHT = 250;
	private JTextField nameTextField, conNameTextField, conNodeTextField;
	private Random rand = new Random();
	
	public Master() {
		setSize(WIDTH,HEIGHT);
		setTitle("Address Book");
		Container contentPane = getContentPane();
		contentPane.setBackground(Color.PINK);
		contentPane.setLayout(new GridLayout(8,2));
		
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem newItem = new JMenuItem("Run Test Method", 'R');
        newItem.addActionListener(this);
        fileMenu.add(newItem);
        
        JMenuItem displayItem = new JMenuItem("Display Nodes and Connections", 'D');
        displayItem.addActionListener(this);
        fileMenu.add(displayItem);
        
        JMenuItem randomItem = new JMenuItem("Random Search", 'R');
        randomItem.addActionListener(this);
        fileMenu.add(randomItem);
        
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);
		
		JLabel nameLabel = new JLabel("Name: ");
		contentPane.add(nameLabel);
	    nameTextField = new JTextField(25);
		contentPane.add(nameTextField);
		
		JButton nameButton = new JButton("Create Node");
		nameButton.addActionListener(this);
		contentPane.add(nameButton);
		
		JLabel conLabel = new JLabel("");
		contentPane.add(conLabel);
		
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

	}
	
	public Node newNode(String nodeName) {
		Node n = new Node(nodeName);
		allNodes.add(n);
		return n;
	}
	
	public Node getNode(String name){
		for (int i = 0; i < allNodes.size(); i++){
			if (allNodes.get(i).getName().equals(name)) {
				return allNodes.get(i);
			}
		}
		return null;
	}
	
	public void nodeConnections(Node node, String connections) {
		String[] nodeConnections = connections.split(" ");
		
		node.addConnections(nodeConnections);
		/*
		for (Node t : allNodes){
			if (t.equals(node)) {
				for (int i = 0; i < nodeConnections.length; i++){
					t.addConnection(nodeConnections[i]);
				}
			}
		}*/
	}
	
	public void runTest(){
		Node A = newNode("A");
		Node B = newNode("B");
		Node C = newNode("C");
		Node D = newNode("D");
		Node E = newNode("E");
		
		// Move to list of nodes, rather than list of node names later
		nodeConnections(A, "B C");
		nodeConnections(B, "A D E");
		nodeConnections(C, "A D");
		nodeConnections(D, "B C");
		nodeConnections(E, "A B");
	}
	
	public void receiveRandomMessage(Node node, String incomingMessage) {
		node.setMessage(incomingMessage);
		
		
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
		
		System.out.println("Changed node: " + node.getName() + " with message: " + incomingMessage);
		System.out.println("Next node: " + nextNode.getName());
		receiveRandomMessage(nextNode, incomingMessage);
	}

	public void displayNodes(){
		for (Node n : allNodes){
			n.displayNode();
		}
	}
	public static void main(String[] args) {
		Master m = new Master();
		m.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		
		if(actionCommand.equals("Create Node")) {
			allNodes.add(new Node(nameTextField.getText()));
		}
		else if(actionCommand.equals("Create connections for node")) {
			Node n =  getNode(conNodeTextField.getText());
			nodeConnections(n, conNameTextField.getText());
		}
		else if(actionCommand.equals("Run Test Method")) {
			runTest();
		}
		else if(actionCommand.equals("Display Nodes and Connections")) {
			displayNodes();
		}
		else if(actionCommand.equals("Random Search")) {
			if (allNodes.size() == 0){
				System.out.println("Need to run tests first");
				return;
			}
			receiveRandomMessage(getNode("A"), "Test");
		}
	}
}
