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
	
	public void newNode(String nodeName) {
		allNodes.add(new Node(nodeName));
	}
	
	public Node getNode(String name){
		for (int i = 0; i < allNodes.size(); i++){
			if (allNodes.get(i).getName() == name) {
				return allNodes.get(i);
			}
		}
		return null;
	}
	
	public void nodeConnections(String node, String connections) {
		String[] nodeConnections = connections.split(" ");
		
		for (Node t : allNodes){
			if (t.getName() == node) {
				for (int i = 0; i < nodeConnections.length; i++){
					t.addConnection(nodeConnections[i]);
				}
			}
		}
	}
	
	public void runTest(){
		newNode("A");
		newNode("B");
		newNode("C");
		newNode("D");
		newNode("E");
		
		nodeConnections("A", "B C");
		nodeConnections("B", "A D E");
		nodeConnections("C", "A D");
		nodeConnections("D", "B C");
		nodeConnections("E", "A B");
	}
	
	public void receiveRandomMessage(String node, String incoming) {
		for(Node n : allNodes){
			if (n.getName() == node) {
				n.setMessage(incoming);
				List<String> cons = n.getConnections();
				int nextNode = rand.nextInt(cons.size());
			
				try {
					Thread.sleep(1000);                 
				} catch(InterruptedException ex) {
					Thread.currentThread().interrupt();
				}
		
				String s = cons.get(nextNode);
				System.out.println(s);
				System.out.println("Changed node: " + node + " with message: " + incoming);
				receiveRandomMessage(s,incoming);
			}
		}
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
			nodeConnections(conNodeTextField.getText(), conNameTextField.getText());
		}
		else if(actionCommand.equals("Run Test Method")) {
			runTest();
		}
		else if(actionCommand.equals("Display Nodes and Connections")) {
			displayNodes();
		}
		else if(actionCommand.equals("Random Search")) {
			receiveRandomMessage("A", "Test");
		}
	}
}
