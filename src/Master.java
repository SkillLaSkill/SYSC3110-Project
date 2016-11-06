import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.*;
import javax.swing.*;

/**
 * Thiis class creates the GUI that the user uses to create nodes,
 * create connections, and run the random routing simulator
 * 
 * @author Team GetterDone
 *
 */
public class Master extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	
	//Graph Containing Nodes
	private Graph graph = new Graph();
	
	// Variables used to make GUI
	public static final int WIDTH = 400;
	public static final int HEIGHT = 250;
	private JTextField nameTextField, conNameTextField, conNodeTextField;
	private Simulation sim;
	public static JTextArea output;
	
	/**
	 * Generates the GUI
	 */
	public Master() {
		// Creates basic JFrame with container
		setSize(WIDTH,HEIGHT); 
		setTitle("Network Simulator");
		Container outPane = getContentPane();
		Container contentPane = new Container();
		outPane.setBackground(Color.PINK);
		outPane.setLayout(new GridLayout(2, 1));
		contentPane.setBackground(Color.PINK);
		contentPane.setLayout(new GridLayout(6, 2));
		outPane.add(contentPane);
		
		// Creates JMenu bar
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        
        // Adds Setup Simulation Method to JMenuBar
        JMenuItem setupItem = new JMenuItem("Setup Simulation", 'S');
        setupItem.addActionListener(this);
        fileMenu.add(setupItem);
        
        // Adds Step Simulation Method to JMenuBar
        JMenuItem stepItem = new JMenuItem("Step into Simulation", 'I');
        stepItem.addActionListener(this);
        fileMenu.add(stepItem);
        
        // Adds Display Method to JMenuBar
        JMenuItem displayItem = new JMenuItem("Display Nodes and Connections", 'D');
        displayItem.addActionListener(this);
        fileMenu.add(displayItem);
        
        // Adds Setup Test Nodes to JMenuBar
        JMenuItem newItem = new JMenuItem("Setup Test Nodes");
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
		JButton simStopButton = new JButton("Stop Simulation");
		simStopButton.addActionListener(this);
		
		contentPane.add(simStartButton);
		contentPane.add(simStopButton);
		
		output = new JTextArea();
		output.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(output);
		outPane.add(scrollPane);
		
		// Makes it so the program closes when you close the GUI
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	
	/**
	 * Creates the nodes with connections as shown in the projects specifications (for test)
	 */
	
	// Move
	
	


	
	/**
	 * Creates new master program, making the GUI
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Master m = new Master();
		m.setVisible(true);
	}

	/**
	 * Determines the type of action the user wishes and acts accordingly
	 */
	@Override
	// Move to action class
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		
		// User created new node in container(no connections yet)
		if(actionCommand.equals("Create Node")) {
			if (nameTextField.getText().isEmpty() || nameTextField.getText().contains(" ")) {
				Master.output.append("Invalid node name.\n");
				return;
			}
			Node n = new Node(nameTextField.getText());
			
			if (graph.contains(n)) {
				Master.output.append("Cannot add duplicate nodes.\n");
				return;
			}
			graph.addNode(n);
			nameTextField.setText("");
		}
		
		//User initializes the simulation based on the current graph
		else if(actionCommand.equals("Setup Simulation")) {
			sim = new Simulation(graph);
		}
		
		// User added connections to the node
		else if(actionCommand.equals("Establish Connections")) {
			String s = conNodeTextField.getText();
			if (s.isEmpty()) return;
			
			Node n =  graph.getNode(s);
			if (n == null) {
				Master.output.append("Node \"" + s + "\" does not exist\n.");
				return;
			}
			
			String connList = conNameTextField.getText();
			
			graph.addNodeConnectionsByName(n, connList);
			
			conNodeTextField.setText("");
			conNameTextField.setText("");
		}
		
		// User runs test method that creates text nodes with connections
		else if(actionCommand.equals("Setup Test Nodes")) {
			//runTest();
		}
		
		// User wants to display all the nodes with their connections
		else if(actionCommand.equals("Display Nodes and Connections")) {
			graph.displayNodes();
		}
		
		// User wants to start the simulation. Can specify # of steps and the Send Rate.
		else if(actionCommand.equals("Start Simulation")) {
			if (graph.size() == 0) {
				Master.output.append("Need to set up nodes and connections.\n");
				return;
			}
			
			if(sim == null) {
				Master.output.append("Need to setup simulation\n");
				return;
			}
			
			//sim.run();
			sim.simulate(50, 3);
		}
		
		//User wants to take 1 step into the simulation. Can specify # of steps and the Send Rate.
		else if(actionCommand.equals("Step into Simulation")) {
			if (graph.size() == 0) {
				Master.output.append("Need to set up nodes and connections.\n");
				return;
			}
			
			if(sim == null) {
				Master.output.append("Need to setup simulation.\n");
				return;
			}
			sim.simulate(1, 3);
		}
		
		// User wants to stop the simulation
		else if (actionCommand.equals("Stop Simulation")){
			sim.setSimulating(false);
		}
		// User wants to reset the GUI and all nodes
		else if (actionCommand.equals("Reset")) {
			if (sim.isSimulating()) {
				Master.output.append("Need to stop simulation before resetting.\n");
				return;
			}
			graph.clear();
			nameTextField.setText("");
			conNameTextField.setText(""); 
			conNodeTextField.setText("");
			sim = null;
		}
	}
}