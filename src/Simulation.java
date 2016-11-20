import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Creates an Simulation thread that will run the different types of algorithms
 * (Only random transfer algorithm right now)
 * 
 * @author Team GetterDone
 *
 */
public class Simulation extends Thread {

	private Graph graph;
	private List<ViewStrategy> views;
	private Random rand = new Random();
	private ArrayList<Packet> packetList;
	private boolean simulating = false;
	private int stepCounter = 0;
	private int totalHops = 0;
	//private int completedHops = 0;
	//private int completedTransferCount = 0;
	
	
	public Simulation(SimGUI v) {
		this(new Graph());
		views = new ArrayList<ViewStrategy>();
		views.add(v);
	}
	
	/**
	 * Creates a new simulator
	 * 
	 * @param graph (Graph)
	 */
	public Simulation(Graph graph) {
		this.graph = graph;
		rand = new Random();
		packetList = new ArrayList<Packet>();
	}
	
	/**
	 * Checks if the simulator is running
	 * 
	 * @return boolean
	 */
	public boolean isSimulating() {
		return simulating;
	}

	/**
	 * Sets the simulator to running
	 * 
	 * @param simulating (boolean)
	 */
	public void setSimulating(boolean simulating) {
		this.simulating = simulating;
	}

	/**
	 * Returns the graph information
	 * 
	 * @return Graph
	 */
	public Graph getGraph() {
		return graph;
	}
	
	/**
	 * Gets the number steps taken
	 * 
	 * @return stepCounter (int)
	 */
	public int getSteps(){
		return stepCounter;
	}
	
	/**
	 * Get totalHops (packets) transfered
	 * 
	 * @return totalHops (int)
	 */
	public int getTotalHops() {
		return totalHops;
	}
	
	public List<Packet> getTransfers() {
		List<Packet> tList = new ArrayList<>();
		for (Packet t : packetList) {
			tList.add(new Packet(t));
		}
		return tList;
	}
	
	/**
	 * Resets the simulation
	 */
	public void reset(){
		packetList = new ArrayList<Packet>();
		simulating = false;
		stepCounter = 0;
		totalHops = 0;
		graph = new Graph();
	}
	
	@Override
	public void run() {}
	
	/**
	 * Runs one step into the simulation.
	 *
	 * @param sendRate (int)
	 */
	public void simulateStep(int sendRate) {
		simulating = true;
		/** Continue simulating until another object tells us to stop.
		*   Creates a new transfer every 3rd step, or at beginning
		*/
		
		for (Node n : graph.getNodes()) {
			System.out.println(n.getName());
			
			for (Node x : graph.getConnections(n))
				System.out.println("-" + x.getName());
		}
		
		if (simulating == true) {
			if(packetList.isEmpty() || (stepCounter % sendRate) == 0)
				packetList.add(new Packet(graph));
			
			randomTransferAlgorithm();
			stepCounter++;
		}
		simulating = false;
	}
	
	/*
	 * Every time something has been changed notifyView calls view and
	 * remakes the GUI graph, thus updating the view. 
	 */
	public void notifyView()
	{
		for(ViewStrategy view: views)
			view.update();
	}
	
	/**
	 * Begins the random transfer algorithm.
	 * Performs one step into the simulation for each transfer.
	 */
	private void randomTransferAlgorithm() {
		ArrayList<Packet> completedTransferList = new ArrayList<Packet>();
		
		if (!simulating) {
			return;
		}
		for(Packet trans : packetList)
		{
			//System.out.println();
			//Set a new random position node for the transfer	
			List<Node> cons = graph.getConnections(trans.getPosition());
		
			int nextNodeIndex = rand.nextInt(cons.size());
			trans.setPosition(cons.get(nextNodeIndex));
			trans.incrementHops();
			totalHops++;
			
			// Prints the change name as well as the next node that the message will be sent to as long as it didn't reach the destinations
			if(trans.getPosition().equals(trans.getDestination())) {
				completedTransferList.add(trans);
			}
			
		}
		//Removes all completed transfers from the list of all transfers
		if(completedTransferList.isEmpty() == false) {
			packetList.removeAll(completedTransferList);
				//for(Transfer trans : completedTransferList) {
				//	completedHops += trans.getHops();
				//	completedTransferCount++;
				//}
		}
	}
}
