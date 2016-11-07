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
		if (name.isEmpty()) return;
		Node node = new Node(name);
		
		// Don't add duplicate
		if (!model.getGraph().contains(node)) {
			model.getGraph().addNode(node);
			view.addNode(node.getName());
		}
		else System.out.println("");
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
	private void makeConnections(String node, String connections) 
	{
		List<String>  conList = new ArrayList(String.split(connections));
		for (Node toCon : conList) {
			makeConnection(node, toCon);
		}		
	}
	
	/**
	 * Removes a connection between 2 nodes
	 */
	private void removeConnection(Node A, Node B)
	{
		
	}
	
	/**
	 * Sets up the simulation
	 */
	/*
	private void setupSim()
	{
		model = new Simulation(new Graph());
	}
	*/
	
	private void DisplayNandC() {}
	
	/**
	 * Starts the simulation
	 * 
	 * @param steps (int)
	 * @param sendRate (int)
	 */
	private void startSim(int steps, int sendRate)
	{
		//get values for steps and sendrate from user
		if(model != null && model.getGraph().size() > 0){
			for(int i = 0; i < steps; i++){
				model.simulateStep(sendRate);
				view.simStepComplete();
			}
		}
		
	}
	
	/**
	 * Takes a single step in simulation
	 *
	 *@param sendRate (int)
	 */
	
	private void stepSim(int sendRate)
	{
		//get values for steps and sendrate from user
		if(model != null && model.getGraph().size() > 0) {
			model.simulateStep(sendRate);
			view.simStepComplete();
		}
	}
	
	/**
	 * Resets the simulation environment
	 */
	private void reset()
	{
		model.reset();
		view.reset();
	}
	
	private void setupTestGraph()
	{
		if(model != null && model.getGraph() != null) model.getGraph().buildTestGraph();
		else System.out.println("Could not setup graph!");
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		String actionCommand = arg0.getActionCommand();
		// Calls the private method to deal with Node creation in both model and view
		if(actionCommand == "Create Node")	createNode(view.getNewNodeName());
		// Calls private method to deal with Connection establishment in both model and view
		else if(actionCommand == "Establish Connections") makeConnections(view.getNewConnectionNodeName(), view.getConnectionList());
		// Calls private method to deal with Node removal in both model and view
		else if(actionCommand == "Delete Connection: ")	removeConnection()
		
		/*
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
				if (model.getGraph().getNode(s) != null) {
					conList.add(model.getGraph().getNode(s));
				}
				else return; // Invalid connection list
			}

			makeConnections(n, conList);
		}
		// <<< Reset the simulation environment >>>
		else if (actionCommand.equals("Reset")) {
			reset();
			//Transfer.resetId();
		}
		else if (actionCommand.equals("Delete Node")) {
			String nodeStr = view.getNodeNameToDelete();
			if (!model.getGraph().contains(new Node(nodeStr))) {
				return;
			}
			model.getGraph().removeNode(nodeStr);
			view.removeNode(nodeStr);
		}
		
		// <<< Delete Connection between two Nodes >>>
		else if (actionCommand.equals("Delete Connection")) {
			String[] connectionToDelete = view.getConnectionToDelete().split(" ");
			if (connectionToDelete.length == 2) {
				Node A = new Node(connectionToDelete[0]);
				Node B = new Node(connectionToDelete[1]);
				if (model.getGraph().contains(A) && model.getGraph().contains(B)){
					model.getGraph().removeConnection(A, B);
					view.removeConnection(A.getName(), B.getName());
				}
			}

			
		}
		//  <<< Select the routing algorithm to use >>>
		else if (actionCommand.equals("algorithm")) {
			String algoritm = view.getSelectedAlgorithm();
		}
		*/
		
		
	}

}
