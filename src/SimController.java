import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/** 
 * Creates a instance of a simulation controller
 * 
 * @author Team GetterDone
 */
public class SimController implements ActionListener {
	
	private static final String IMPORT_STATE = "Import State";
	private static final String SAVE_STATE = "Save State";
	private static final String STEP_BACK = "Step Back";
	private static final String STEP_SIMULATION = "Step Simulation";
	private static final String START_SIMULATION = "Start Simulation";
	private static final String SELECT_ALGORITHM = "Select Algorithm";
	private static final String CONSTRUCT_GRAPH = "Construct Graph";
	private static final String CLEAR_TEXT = "Clear Text";
	private static final String EXIT = "Exit";
	private static final String REFRESH = "Refresh";
	private static final String RESET = "Reset";
	private static final String DELETE_CONNECTION = "Delete Connection";
	private static final String DELETE_NODE = "Delete Node";
	private static final String ADD_CONNECTION = "Add Connection";
	private static final String CREATE_NODES = "Create Nodes";
	private SimGUI view;
	private Simulation model;
	String fileName = "ImportExport";

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
		//Gets node names from the user
		String name = view.createPrompt("Enter node names seperated by a comma");
		if (name == null || name.isEmpty()) return;
		else if(model.getGraph().contains(name))
		{
			System.out.println("Node was not added!");
			return;
		}
		// Splits the node names into separate strings then creates nodes from them
		String allNames[] = name.split(",");
		for (String s : allNames) {
			model.getGraph().addNode(new Node(s));
			System.out.println("Node " + s + " has been added!");
					
		}
		model.notifyView();
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

		Node nodeA = model.getGraph().getNode(A);
		Node nodeB = model.getGraph().getNode(B);
		if (nodeA == null || nodeB == null) return;
		nodeA.addConnection(nodeB);
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

		Node nodeA = model.getGraph().getNode(A);
		Node nodeB = model.getGraph().getNode(B);
		if (nodeA == null || nodeB == null) return;
		nodeA.removeConnection(nodeB);
		model.notifyView();
		
		
	}
	
	/**
	 * Selects the algorithm to use in the simulation
	 */
	private void selectAlg()
	{
		String[] choices = {"Random", "Flood", "Breadth-first", "Depth-first" };
		String choice = view.comboPrompt(choices);
		if(choice == null || choice.isEmpty()) 
			return;
		if(choice.equals("Random")) {
			model.setAlgorithm(new RandomAlgorithm());
		}
		else if (choice.equals("Breadth-first")){
			model.setAlgorithm(new ShortestPathAlgorithm());
		}

		else if (choice.equals("Flood"))
			model.setAlgorithm(new FloodingAlgorithm());
		else if (choice.equals("Depth-first")) {
			model.setAlgorithm(new DepthFirstAlgorithm());
		}
		//Add other algorithm choices here
		else System.out.println("Algorithm not implemented!");
	}
	
	/**
	 * Starts the simulation
	 * 
	 * @param s (String) - Number of steps that will be taken
	 * @param r (String) - The step rate the simulation will use
	 */ 
	private void startSim()
	{
		if(model.getGraph().isEmpty())
			return;
		if(model.getAlgorithm() == null)
			this.selectAlg();
		if(model.getAlgorithm() == null)
			return;
		String s1 = view.createPrompt("Enter number of steps");
		if(s1 == null || s1.isEmpty() )
			return;
		if(	!(this.isNumeric(s1)) )	
			return;
		int steps = Integer.parseInt(s1);
		
		if(!(steps > 0))
		{
			System.out.println("Steps must be above zero!");
			return;
		}
		for(int i = 0; i < steps; i++) 
			model.simulateStep();
	}
	
	/**
	 * Takes a step in the simulation
	 */
	private void stepSim()
	{
		if(model.getGraph().isEmpty())
			return;
		if(!model.hasSendRate())
		{
			String s2 = view.createPrompt("Enter send rate");
			if(s2 == null || s2.isEmpty() )
				return;
			if(	!(this.isNumeric(s2)) )	
				return;
			int rate = Integer.parseInt(s2);
			if(!(rate > 0))
			{
				System.out.println("Send rate must be above zero!");
				return;
			}
			model.setSendRate(rate);
		}
			
		if(model.getAlgorithm() == null) this.selectAlg();
		if(!(model.getAlgorithm() == null))
			model.simulateStep();
	}
	
	/**
	 * Takes a step in teh simulation
	 */
	private void stepBack()
	{
		if(model.getGraph().isEmpty())
			return;
		if(model.getAlgorithm() == null) this.selectAlg();
		if(!(model.getAlgorithm() == null))
			model.simulateBackStep();
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
	
	private void clear()
	{
		view.clearText();
	}
	
	private void construct()
	{
		Node A = new Node("1");
		Node B = new Node("2");
		Node C = new Node("3");
		Node D = new Node("4");
		A.addConnection(B);
		A.addConnection(C);
		A.addConnection(D);
		C.addConnection(D);
		model.getGraph().addNode(A);
		model.getGraph().addNode(B);
		model.getGraph().addNode(C);
		model.getGraph().addNode(D);
		model.notifyView();
	}
	
	/**
	 * Checks that action the user wants to take, then acts accordingly
	 */
	@SuppressWarnings("static-access")
	@Override
	public void actionPerformed(ActionEvent arg0) {
		String actionCommand = arg0.getActionCommand();
		// Calls the private method to deal with Node creation in both model and view
		if(actionCommand.equals(CREATE_NODES)) createNodes();
		// Calls private method to deal with Connection establishment in both model and view
		else if(actionCommand.equals(ADD_CONNECTION)) makeConnection();
		// Calls Private method to deal with Node removal in both model and view
		else if(actionCommand.equals(DELETE_NODE)) removeNode();
		// Calls private method to deal with Connection removal in both model and view
		else if(actionCommand.equals(DELETE_CONNECTION))	removeConnection();
		// Calls private method to deal with reset on both model and view
		else if(actionCommand.equals(RESET))	reset();
		//Hard refresh
		else if(actionCommand.equals(REFRESH)) view.update();
		//Exits the program
		else if(actionCommand.equals(EXIT)) exit();
		//Clears the text field
		else if(actionCommand.equals(CLEAR_TEXT)) clear();
		//Constructs a graph
		else if(actionCommand.equals(CONSTRUCT_GRAPH)) construct();
		// Calls private method to start the simulation
		else if(actionCommand.equals(SELECT_ALGORITHM)) selectAlg();
		// Starts the simulation
		else if(actionCommand.equals(START_SIMULATION)) startSim();
		// Steps once into the simulation
		else if(actionCommand.equals(STEP_SIMULATION)) stepSim();
		// Steps back once in the simulation
		else if(actionCommand.equals(STEP_BACK)) stepBack();
		// Exports the topology view information into a XML
		else if(actionCommand.equals(SAVE_STATE)) model.getGraph().exportToXmlFile(fileName);
		// Imports topology view from the XML file
		else if(actionCommand.equals(IMPORT_STATE)) {
			model.setGraph(model.getGraph().importFromXMLFile(new File(fileName+".xml")));
			view.update(); 
		}
	}
}
