import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFrame;

/**
 * Creates a instance of a simulation controller
 * 
 * @author Team GetterDone
 *
 */
public class SimController implements ActionListener {
	
	private SimGUI view;
	private Simulation model;

	/**
	 * Simulation controller constructor (initializes view and model)
	 * 
	 * @param v (ViewStrategy)
	 * @param m (Simulation)
	 */
	public SimController(SimGUI v, Simulation m)
	{
		view = v;
		model = m;
	}
	
	/**
	 * Creates a node on the graph
	 * 
	 * @param name (String)
	 */
	private void createNode()
	{
		String name = view.createPrompt("Enter node name");
		if (name == null || name.isEmpty()) return;
		else if(model.getGraph().contains(name))
		{
			System.out.println("Node was not added!");
			return;
		}
		model.getGraph().addNode(new Node(name));
		System.out.println("Node " + name + " has been added!");
		model.notifyView();
	}
	
	
	/**
	 * Removes a node from the graph
	 * 
	 * @param name (String)
	 */
	private void removeNode()
	{
		String name = view.createPrompt("Enter node name");
		if (name == null || name.isEmpty());
		model.getGraph().removeNode(name);
		model.notifyView();
	}
	
	/**
	 * Creates a connection between two node on the graph
	 * 
	 * @param A (Node)
	 * @param B (Node)
	 */
	private void makeConnection()
	{
		String A = view.createPrompt("Enter first node name");
		String B = view.createPrompt("Enter second node name");
		if (A == null || B == null || A.isEmpty() || B.isEmpty()) return;
		model.getGraph().addConnection(model.getGraph().getNode(A), model.getGraph().getNode(B));
		model.notifyView();
	}
	
	/**
	 * Create connections from the node to all the nodes in the list
	 * 
	 * @param node	(Node)
	 * @param conList (List<Node>)
	 */
	private void makeConnections() 
	{
		String node = view.createPrompt("Enter node name");
		String[] s = view.createPrompt("Enter nodes you would like to connect to").split(" ");
		List<String>  conList = Arrays.asList(s);
		for (String con : conList) {
			if(model.getGraph().contains(node) && model.getGraph().contains(con))
			{
				model.getGraph().addConnection(model.getGraph().getNode(node), model.getGraph().getNode(con));
			}
			else System.out.println("A node pair was not connected!");
		}	
		model.notifyView();
	}
	
	
	/**
	 * Removes a connection between 2 nodes
	 */
	private void removeConnection()
	{
		String A = view.createPrompt("Enter first node name");
		String B = view.createPrompt("Enter second node name");
		if (A == null || B == null || A.isEmpty() || B.isEmpty()) return;
		model.getGraph().removeConnection(model.getGraph().getNode(A), model.getGraph().getNode(B));
		model.notifyView();
		
		
	}
	
	private void startSim(String s, String r)
	{
		if(	!(this.isNumeric(s) && this.isNumeric(r)) )	return;
		int steps = Integer.parseInt(s);
		int sendRate = Integer.parseInt(r);
		
		if(	!(sendRate > 0 )	|| !(steps > 0)	)
		{
			System.out.println("Sendrate and steps must be above zero!");
			return;
		}
		for(int i = 0; i < steps; i++)
			model.simulateStep(sendRate);
	}
	
	private boolean isNumeric(String str)  
	{  
	  try  
	  {  
	    Integer.parseInt(str);  
	  }  
	  catch(NumberFormatException nfe)  
	  {  
	    return false;  
	  }  
	  return true;  
	}
	
	/**
	 * Starts the simulation
	 * 
	 * @param steps (int)
	 * @param sendRate (int)
	 */
	/*private void startSim(String s, String r)
	{
		int steps = Integer.parseInt(s);
		int sendRate = Integer.parseInt(r);
		while(steps-- != 0 && sendRate != 0)
		{
			//get values for steps and sendrate from user
			if(model != null && model.getGraph().size() > 0) {
				List<Transfer> before = model.getTransfers();
				model.simulateStep(sendRate);
				view.simStepComplete();
				List<Transfer> after = model.getTransfers();
				
				for (Transfer t2 : before) {
					// If the transfer no longer exists in the model
					if (!after.contains(t2)) {
						view.removeMessage(t2.getMessage()  + ": " + t2.getDestination().getName(), t2.getPosition().getName());
					}
				}
				for (Transfer t : after) {
					// If the transfer didn't exist before the simulation step
					if (!before.contains(t)) {
						view.addMessage(t.getMessage() + ": " + t.getDestination().getName(), t.getPosition().getName());
					}
					else {
						int idx = before.indexOf(t);
						String beforePos = before.get(idx).getPosition().getName();
						String afterPos = t.getPosition().getName();
						
						view.updateMessage(t.getMessage() + ": " + t.getDestination().getName(), beforePos, afterPos);
					}
				}
				view.setOutput(Integer.toString(model.getTotalHops()));
			}
		}
		view.setOutput("Total packets transmitted: " + model.getTotalHops());
	}*/
	
	/**
	 * Resets the simulation environment
	 */
	private void reset()
	{
		model.reset();
		model.notifyView();
	}
	
	private void exit()
	{
		view.close();
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		String actionCommand = arg0.getActionCommand();
		// Calls the private method to deal with Node creation in both model and view
		if(actionCommand.equals("Create Node"))	createNode();
		// Calls private method to deal with Connection establishment in both model and view
		else if(actionCommand.equals("Add Connection")) makeConnection();
		// Calls Private method to deal with Node removal in both model and view
		else if(actionCommand.equals("Delete Node")) removeNode();
		// Calls private method to deal with Connection removal in both model and view
		else if(actionCommand.equals("Delete Connection"))	removeConnection();
		// Calls private method to deal with reset on both model and view
		else if(actionCommand.equals("Reset"))	reset();
		//Hard refresh
		else if(actionCommand.equals("Refresh"))	view.update();
		//Exits the program
		else if(actionCommand.equals("Exit"))	exit();
		// Calls private method to start the simulation
		else if(actionCommand.equals("Simulate"))	startSim(view.createPrompt("Enter number of steps"), view.createPrompt("Enter send rate (ms)"));
		// Calls private method to step once through the simulation
		//else if(actionCommand.equals("Simulate Step"))	startSim(1, view.createPrompt("Enter send rate (ms)"));		
		System.out.println("Nothing Happened");
	}

}
