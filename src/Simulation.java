import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Creates an Simulation thread that will run the different types of algorithms
 * (Only random transfer algorithm right now)
 * 
 * @author Team Getterdone
 *
 */
public class Simulation extends Thread {

	private Graph graph;
	private Random rand;
	private ArrayList<Transfer> transferList;
	private int stepCounter;
	private boolean simulating = false;
	
	/**
	 * Creates a new simulator
	 * 
	 * @param graph (Graph)
	 */
	public Simulation(Graph graph) {
		this.graph = graph;
		rand = new Random();
		stepCounter = 0;
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

	@Override
	public void run() {
		
			
	}
	/**
	 * Runs the simulation based on given number of steps and given send rate.
	 * 
	 * @param steps (Integer)
	 * @param sendRate (Integer)
	 */
	public void simulate(int steps, int sendRate) {
		simulating = true;
		
		/** Continue simulating until another object tells us to stop.
		*   Creates a new transfer every 3rd step, or at beginning
		*/
		while (simulating == true && steps-- > 0) {
			simulateStep();
		}
		simulating = false;
	}
	
	public void simulateStep() {
		if(stepCounter == 0 || (stepCounter % 3) == 0){
			Transfer transfer1 = new Transfer(graph);
			transferList.add(transfer1);
			Master.output.append("Transfer" + Integer.toString(transfer1.getId())+ " starting from " + transfer1.getPosition().getName() + " to " + transfer1.getDestination().getName() + " with message: " + transfer1.getMessage() + ".\n");
		}
		randomTransferAlgorithm();
		stepCounter++;
	}
		
	/**
	 * Begins the random transfer algorithm.
	 * Performs one step into the simulation for each transfer.
	 * 
	 * 
	 */
	private void randomTransferAlgorithm() {
		ArrayList<Transfer> completedTransfers = new ArrayList<Transfer>();
		
		if (!simulating) {
			Master.output.append("Simulation ended early, will not count towards statistics.\n");
			return;
		}
		for(Transfer trans : transferList)
		{
			// Delay to see things as they're happening.
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
					
			//Set a new random position node for the transfer	
			List<Node> cons = graph.getConnections(trans.getPosition());
			
			int nextNodeIndex = rand.nextInt(cons.size());
			trans.setPosition(cons.get(nextNodeIndex));
			trans.incrementHops();
			Master.output.append("Transfer" + Integer.toString(trans.getId()) + " sent to: " + trans.getPosition().getName() + ", with message: " + trans.getMessage() + "\n");
			
			
			// Prints the change name as well as the next node that the message will be sent to as long as it didn't reach the destinations
			if(trans.getPosition().equals(trans.getDestination())) {
				Master.output.append("Transfer" + Integer.toString(trans.getId()) + " has reached its destination. Number of hops taken: " + trans.getHops() + "\n");
				completedTransfers.add(trans);
			}
			
		}
		if(completedTransfers.isEmpty() == false) {
			transferList.removeAll(completedTransfers);
		}
	
	}
}
