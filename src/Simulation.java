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
	private Metric metric;
	private List<ViewStrategy> views;
	private Random rand = new Random();
	
	private List<Graph> history = new ArrayList<>();
	private int historyPosition = 0;
	
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
		metric = new Metric();
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
	 * Returns the metric information
	 * 
	 * @return Metric - Metrics that the simulation uses
	 */
	public Metric getMetric() {
		return metric;
	}
	
	/**
	 * Gets the metrics of the simulation
	 * 
	 * @return stepCounter (int) - Number of steps simulation has taken
	 */
	public Metric getSteps(){
		return metric;
	}

	/**
	 * Resets the simulation
	 */
	public void reset(){
		graph = new Graph();
		metric = new Metric();
	}
	
	/**
	 * Sets the send rate of the simulation
	 * 
	 * @param sendRate (int) - Desired send rate
	 *
	public void setSendRate(int sendRate) {
		this.sendRate = sendRate;
	}*/
	
	/**
	 * Checks if the simulation has a send rate
	 * 
	 * @return boolean - True = has a send rate; False = doesn't have a send rate
	 *
	public boolean hasSendRate()
	{
		if(sendRate <= 0) return false;
		else return true;
	}*/
	
	@Override
	public void run() {}
	
	/**
	 * Runs one step into the simulation.
	 *
	 * @param sendRate (int) - Rate in which the packets are sent
	 */
	public void simulateStep() {
		// If back in history, just go forward without simulating again.
		if (historyPosition != 0) {
			graph = history.get(history.size() - 1 - historyPosition);
			historyPosition--;
		}
		
		// Will only simulate if not in history.
		else {
			alg.setGraph(graph);
			alg.setMetric(metric);
			
				
			alg.simulateStep();
			//stepCounter++;
			
			// Packet reaches destination, new packet is made
			if(!graph.packetsExist()) {
				List<Node> nodes = graph.getNodes();
				
				Node destination = nodes.get(rand.nextInt((int)	nodes.size()));
				Node source = nodes.get(rand.nextInt((int)	nodes.size()));
				
				Packet p = new Packet("Message", destination);
				while(destination.equals(source)) {
					source = nodes.get(rand.nextInt((int)	nodes.size()));
				}
				source.addPacket(p);
				source.addSeenPacket(p);
				this.printPacketTransfer(source, destination, p);
			}
			
			// Need to export and import to create a new graph of that state.
			Graph g = (Graph.importFromXMLObj(graph.exportToXmlObj()));
			
			history.add(g);
		}
		notifyView();
	}
	
	/**
	 * Runs one step into the simulation.
	 *
	 * @param sendRate (int) - Rate in which the packets are sent
	 */
	public void simulateBackStep() {
		if (historyPosition != (history.size() - 1)) {
			historyPosition++;
			graph = history.get(history.size() - 1 - historyPosition);
			notifyView();
		}
	}
	
	
	/**
	 * Every time something has been changed notifyView calls view and
	 * re-makes the GUI graph, thus updating the view. 
	 */
	public void notifyView()
	{
		for(ViewStrategy view: views)
			view.update();
		printSimulationMetrics();
	}

	/**
	 * Gets the algorithm being used
	 * 
	 * @return RoutingAlgorithm - Algorithm being used
	 */
	public RoutingAlgorithm getAlgorithm() {
		return alg;
	}

	/**
	 * Sets the algorithm being used
	 * 
	 * @param alg (RoutingAlgorithm) - Algorithm wanted to be used
	 */
	public void setAlgorithm(RoutingAlgorithm alg) {
		this.alg = alg;
		alg.setGraph(graph);
	}
	
	/**
	 * Prints the packet being transfered information.
	 * 
	 * @param current (Node) - Current node packet is at
	 * @param travelling (Node) - Next node
	 * @param packet (Node) - Packet itself
	 */
	public void printPacketTransfer(Node current, Node travelling, Packet packet)
	{
		for(ViewStrategy view: views)
			view.addText("Message: " + packet.getMessage() +" Leaving node: " + current.getName() + " Travelling to node: " + travelling.getName() + ". Current hops: " + packet.getHops());
	}
	
	/**
	 * Prints the simulation metrics such as average hops
	 */
	public void printSimulationMetrics()
	{
		for(ViewStrategy view: views)
			view.addText("Total hops: " + metric.getHops() + "\n" + "Average hops per transfer: " + metric.getAverageHopsPerTransfer());
	}
	
	/**
	 * Sets the graph to the given graph
	 * 
	 * @param g (Graph) - Graph you want used
	 */
	public void setGraph(Graph g) {
		graph = g;
	}
}
