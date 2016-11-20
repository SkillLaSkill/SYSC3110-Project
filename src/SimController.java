import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFrame;

/**
 * Creates a instance of a simulation controller
 * 
 * @author Team GetterDone
 */
public class SimController implements ActionListener {
	
	private SimGUI view;
	private Simulation model;

	/**
	 * Simulation controller constructor (initializes view and model)
	 * 
	 * @param v (ViewStrategy) - ViewStrategy object wanted used
	 * @param m (Simulation) - Instance of Simuation that will run
	 */
	public SimController(SimGUI v, Simulation m)
	{
		view = v;
		model = m;
	}
	
	/**
	 * Creates nodes on the graph from a string the user gives. This string contains multiple
	 * node names separated by a space
	 */
	private void createNodes()
	{
		String name = view.createPrompt("Enter node name");

		if (name == null || name.isEmpty()) return;
		else if(model.getGraph().contains(name))
		{
			System.out.println("Node was not added!");
			return;
		}

		// Splits the node names into separate strings then creates nodes from them
		String allNames[] = name.split(" ");
		for (String s : allNames) {
			model.getGraph().addNode(new Node(s));
			System.out.println("Node " + s + " has been added!");
			model.notifyView();
		}
	}
	
	
	/**
	 * Removes a node from the graph from a string the user gives. This string is the
	 * name of the node that will be removed from the simulation
	 */
	private void removeNode()
	{
		String name = view.createPrompt("Enter node name");
		if (name == null || name.isEmpty());
		model.getGraph().removeNode(name);
		model.notifyView();
	}
	
	/**
	 * Creates a connection between two node on the graph from two string the user gives.
	 */
	private void makeConnection()
	{
		String A = view.createPrompt("Enter first node name");
		String B = view.createPrompt("Enter second node name");
		if (A == null || B == null || A.isEmpty() || B.isEmpty()) return;

		model.getGraph().getNode(A).addConnection(B);
		model.notifyView();
	}
	
	/**
	 * Create connections from the node to all the nodes in the list from two string the user gives.
	 */
	private void makeConnections() 
	{
		String node = view.createPrompt("Enter node name");
		String[] s = view.createPrompt("Enter nodes you would like to connect to").split(" ");
		for (String con : s) {
			if(model.getGraph().contains(node) && model.getGraph().contains(con))
			{
				model.getGraph().getNode(node).addConnection(con);
			}
			else System.out.println("A node pair was not connected!");
		}	
		model.notifyView();
	}
	
	
	/**
	 * Removes a connection between 2 nodes from two string the user gives.
	 */
	private void removeConnection()
	{
		String A = view.createPrompt("Enter first node name");
		String B = view.createPrompt("Enter second node name");
		if (A == null || B == null || A.isEmpty() || B.isEmpty()) return;

		model.getGraph().getNode(A).removeConnection(B);
		model.notifyView();
		
		
	}
	
	/**
	 * Starts the simulation
	 * 
	 * @param s (String) - Number of steps that will be taken
	 * @param r (String) - The step rate the simulation will use
	 */
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
	
	/**
	 * Checks if the given string can become an integer.
	 * 
	 * @param str (String) - String needed to be checks
	 * @return boolean - True = is an integer; False = isn't an integer
	 */
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
	 * Resets the simulation environment
	 */
	private void reset()
	{
		model.reset();
		model.notifyView();
	}
	
	/**
	 * Closes the simulation
	 */
	private void exit()
	{
		view.close();
	}
	
	/**
	 * Checks that action the user wants to take, then acts accordingly
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		String actionCommand = arg0.getActionCommand();
		// Calls the private method to deal with Node creation in both model and view
		if(actionCommand.equals("Create Nodes")) createNodes();
		// Calls private method to deal with Connection establishment in both model and view
		else if(actionCommand.equals("Add Connection")) makeConnection();
		// Calls Private method to deal with Node removal in both model and view
		else if(actionCommand.equals("Delete Node")) removeNode();
		// Calls private method to deal with Connection removal in both model and view
		else if(actionCommand.equals("Delete Connection"))	removeConnection();
		// Calls private method to deal with reset on both model and view
		else if(actionCommand.equals("Reset"))	reset();
		//Hard refresh
		else if(actionCommand.equals("Refresh")) view.update();
		//Exits the program
		else if(actionCommand.equals("Exit")) exit();
		// Calls private method to start the simulation
		else if(actionCommand.equals("Simulate")) startSim(view.createPrompt("Enter number of steps"), view.createPrompt("Enter send rate"));

		// Calls private method to step once through the simulation
		//else if(actionCommand.equals("Simulate Step"))	startSim(1, view.createPrompt("Enter send rate (ms)"));		

	}
}
