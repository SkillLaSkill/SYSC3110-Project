import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Creates a instance of a simulation controller
 * 
 * @author Team GetterDone
 *
 */
public class SimController implements ActionListener {
	
	private ViewStrategy view;
	private Simulation model;

	/**
	 * Simulation controller constructor (initializes view and model)
	 * 
	 * @param v (ViewStrategy)
	 * @param m (Simulation)
	 */
	public SimController(ViewStrategy v, Simulation m)
	{
		view = v;
		model = m;
	}
	
	/**
	 * Creates a node on the graph
	 * 
	 * @param name (String)
	 */
	private void createNode(String name)
	{
		Node node = new Node(name);
		
		if (!model.getGraph().contains(node)) {
			model.getGraph().addNode(node);
			view.addNode(node.getName());
		}
	}
	
	/**
	 * Removes a node from the graph
	 * 
	 * @param name (String)
	 */
	private void removeNode(String name)
	{
		view.removeNode(name);
		model.getGraph().removeNode(name);
	}
	
	/*
	private void setupTestGraph()
	{
		if(model != null && model.getGraph() != null) model.getGraph().buildGraph();
		else System.out.println("Could not setup graph!");
	}*/
	
	/**
	 * Creates a connection between two node on the graph
	 * 
	 * @param A (Node)
	 * @param B (Node)
	 */
	private void makeConnection(Node A, Node B)
	{
		view.addConnection(A.getName(), B.getName());
		model.getGraph().addConnection(A, B);
	}
	
	/**
	 * Create connections from the node to all the nodes in the list
	 * 
	 * @param node	(Node)
	 * @param conList (List<Node>)
	 */
	private void makeConnections(Node node, List<Node> conList) {
		for (Node toCon : conList) {
			makeConnection(node, toCon);
		}		
	}
	
	/**
	 * Sets up the simulation
	 */
	private void setupSim()
	{
		model = new Simulation(new Graph());
	}
	
	private void DisplayNandC() {}
	
	/**
	 * Starts the simulation
	 */
	private void startSim()
	{
		//get values for steps and sendrate from user
		if(model != null && model.getGraph() != null) model.simulate(30,3);
	}
	
	/**
	 * Takes a single step in simulation
	 */
	private void stepSim()
	{
		//get values for steps and sendrate from user
		if(model != null && model.getGraph() != null) model.simulate(1,3);
	}
	
	/*Stop method if we decide to use it
	//private void stopSim()
	//{
	//	model.setSimulating(false);
	//}
	*/
	
	/**
	 * Resets the simulation
	 */
	private void reset()
	{
		model = null;
		//set some textfields to ""
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		String actionCommand = arg0.getActionCommand();
		
		if(actionCommand.equals("Create Node")) {
			createNode(view.getNewNodeName());
		}
		else if (actionCommand.equals("Establish Connections")) {
			String nodeStr = view.getNewConnectionNodeName();
			Node n = model.getGraph().getNode(nodeStr);
			String conString = view.getConnectionList();
			
			// Need to verify connection string is valid format, and nodes are valid
			List<Node> conList = new ArrayList<>();
			for (String s : conString.split(" ")) {
				conList.add(model.getGraph().getNode(s));
			}

			makeConnections(n, conList);
		}
		
		
	}

}
