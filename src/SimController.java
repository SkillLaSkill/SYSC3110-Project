import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
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
		else System.out.println("Didnt add the node bro");
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
	private void makeConnection(String A, String B)
	{
		if(model.getGraph().contains(A) && model.getGraph().contains(A))
		{
		view.addConnection(A, B);
		model.getGraph().addConnection(model.getGraph().getNode(A), model.getGraph().getNode(B));
		}
	}
	
	/**
	 * Create connections from the node to all the nodes in the list
	 * 
	 * @param node	(Node)
	 * @param conList (List<Node>)
	 */
	private void makeConnections(String node, String connections) 
	{
		List<String>  conList = Arrays.asList(connections.split(" "));
		for (String n : conList) {
			if(!model.getGraph().isConnected(node, n))
			{
				makeConnection(node, n);
			}
		}		
	}
	
	
	/**
	 * Removes a connection between 2 nodes
	 */
	private void removeConnection(String connection)
	{
		List<String> nodes = Arrays.asList(connection.split(" "));
		model.getGraph().removeConnection(nodes.get(0), nodes.get(1));
		view.removeConnection(nodes.get(0), nodes.get(1));
		
		
	}
	
	/**
	 * Starts the simulation
	 * 
	 * @param steps (int)
	 * @param sendRate (int)
	 */
	private void startSim(int steps, int sendRate)
	{
		if(steps != 0 && sendRate != 0)
		{
			//get values for steps and sendrate from user
			if(model != null && model.getGraph().size() > 0){
				for(int i = 0; i < steps; i++){
					model.simulateStep(sendRate);
					view.simStepComplete();
					view.setOutput(Integer.toString(model.getTotalHops()));
				}
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
		if(sendRate != 0)
		{
			//get values for steps and sendrate from user
			if(model != null && model.getGraph().size() > 0) {
				model.simulateStep(sendRate);
				view.simStepComplete();
				view.setOutput(Integer.toString(model.getTotalHops()));
			}
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
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		String actionCommand = arg0.getActionCommand();
		// Calls the private method to deal with Node creation in both model and view
		if(actionCommand == "Create Node")	createNode(view.getNewNodeName());
		// Calls private method to deal with Connection establishment in both model and view
		else if(actionCommand == "Establish Connections") makeConnections(view.getNewConnectionNodeName(), view.getConnectionList());
		// Calls Private method to deal with Node removal in both model and view
		else if(actionCommand == "Delete Node") removeNode(view.getNodeNameToDelete());
		// Calls private method to deal with Connection removal in both model and view
		else if(actionCommand == "Delete Connection")	removeConnection(view.getConnectionToDelete());
		// Calls private method to deal with reset on both model and view
		else if(actionCommand == "Reset")	reset();
		// Calls private method to start the simulation
		else if(actionCommand == "Simulate")	startSim(view.getSimSteps(), view.getSendRate());
		// Calls private method to step once through the simulation
		else if(actionCommand == "Simulate Step")	stepSim(view.getSendRate());
		

		
	}

}
