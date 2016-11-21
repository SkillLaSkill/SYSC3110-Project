import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Creates an Simulation thread that will run the different types of algorithms
 * (Random, Flood, Breadth-first, )
 * 
 * @author Team GetterDone
 *
 */
public class Simulation extends Thread {

	private RoutingAlgorithm alg;
	private Graph graph;
	private List<ViewStrategy> views;
	private Random rand = new Random();
	private boolean simulating = false;
	private int stepCounter = 0;
	private int totalHops = 0;
	
	public Simulation(SimGUI v) {
		this(new Graph());
		views = new ArrayList<ViewStrategy>();
		views.add(v);
	}
	
	/**
	 * Creates a new simulator
	 * 
	 * @param graph (Graph) - Graph the contains all the nodes and information that simulation uses
	 */
	public Simulation(Graph graph) {
		this.graph = graph;
		rand = new Random();
	}
	
	/**
	 * Checks if the simulator is running
	 * 
	 * @return boolean - True = running; False = stopped
	 */
	public boolean isSimulating() {
		return simulating;
	}

	/**
	 * Sets the simulator to running
	 * 
	 * @param simulating (boolean) - Sets the simulation to running or stopped
	 */
	public void setSimulating(boolean simulating) {
		this.simulating = simulating;
	}

	/**
	 * Returns the graph information
	 * 
	 * @return Graph - Graph that the simulation uses
	 */
	public Graph getGraph() {
		return graph;
	}
	
	/**
	 * Gets the number steps taken
	 * 
	 * @return stepCounter (int) - Number of steps simulation has taken
	 */
	public int getSteps(){
		return stepCounter;
	}
	
	/**
	 * Get totalHops (packets) transfered
	 * 
	 * @return totalHops (int) - Number of packet transfers
	 */
	public int getTotalHops() {
		return totalHops;
	}
	/**
	 * Resets the simulation
	 */
	public void reset(){
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
	 * @param sendRate (int) - Rate in which the packets are sent
	 */
	public void simulateStep(int sendRate) {
		simulating = true;
		alg.setGraph(graph);
		
		if (simulating == true) {

			if(!graph.packetsExist() || (stepCounter % sendRate) == 0) {
				List<Node> nodes = graph.getNodes();
				
				Node destination = nodes.get(rand.nextInt((int)	nodes.size()));
				Node source = nodes.get(rand.nextInt((int)	nodes.size()));
				
				Packet p = new Packet("Message", destination);
				while(destination.equals(source)) {
					source = nodes.get(rand.nextInt((int)	nodes.size()));
				}
				source.addPacket(p);
				source.addSeenPacket(p);
			}
			alg.simulateStep();
			stepCounter++;
			notifyView();
		}
		simulating = false;
	}
	
	
	/*
	 * Every time something has been changed notifyView calls view and
	 * re-makes the GUI graph, thus updating the view. 
	 */
	public void notifyView()
	{
		for(ViewStrategy view: views)
			view.update();
	}

	public RoutingAlgorithm getAlgorithm() {
		return alg;
	}

	public void setAlgorithm(RoutingAlgorithm alg) {
		this.alg = alg;
		alg.setGraph(graph);
	}
	
}
