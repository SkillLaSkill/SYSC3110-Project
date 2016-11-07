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
	private Random rand;
	private ArrayList<Transfer> transferList;
	private boolean simulating = false;
	private int stepCounter = 0;
	private int totalHops = 0;
	//private int completedHops = 0;
	//private int completedTransferCount = 0;
	
	
	public Simulation() {
		this(new Graph());
	}
	
	/**
	 * Creates a new simulator
	 * 
	 * @param graph (Graph)
	 */
	public Simulation(Graph graph) {
		this.graph = graph;
		rand = new Random();
		transferList = new ArrayList<Transfer>();
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
	
	public void reset(){
		transferList = new ArrayList<Transfer>();
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
		if (simulating == true) {
			if(transferList.isEmpty() || (stepCounter % sendRate) == 0){
				Transfer transfer1 = new Transfer(graph);
				transferList.add(transfer1);
			}
			randomTransferAlgorithm();
			stepCounter++;
		}
		simulating = false;
	}
	
	/**
	 * Begins the random transfer algorithm.
	 * Performs one step into the simulation for each transfer.
	 */
	private void randomTransferAlgorithm() {
		ArrayList<Transfer> completedTransferList = new ArrayList<Transfer>();
		
		if (!simulating) {
			return;
		}
		for(Transfer trans : transferList)
		{
					
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
			transferList.removeAll(completedTransferList);
				//for(Transfer trans : completedTransferList) {
				//	completedHops += trans.getHops();
				//	completedTransferCount++;
				//}
		}
	}
}
