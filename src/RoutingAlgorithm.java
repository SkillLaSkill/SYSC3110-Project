/**
 * Interface the runs all of the different algorithms
 * 
 * @author Team GetterDone
 */
public abstract class RoutingAlgorithm { 
	private Graph graph;
	private Metric metric;
	private int stepNumber = 0;
	
	/**
	 * Sets the metric to the given metric
	 * 
	 * @param metric (Metric)- Desired metric to use
	 */
	public void setMetric(Metric metric) {
		this.metric = metric;
	}
	
	/**
	 * Gets the metrics of the algorithm
	 * 
	 * @return Matric - Metrics of the algorithm
	 */
	public Metric getMetric() {
		return metric;
	}
	
	/**
	 * Sets the graph of the algorithm
	 * 
	 * @param g (Graph) - Graph of the algorithm
	 */
	public void setGraph(Graph g) {
		this.graph = g;
	}
	
	/**
	 * Abstract that allows for individual algorithms to run
	 */
	public abstract void simulateStep();
	
	/**
	 * Keeps simulating steps of all the algorithms
	 * 
	 * @param steps (int) - Number of steps you wish to do
	 */
	public void simulate(int steps) {
		while (steps-- > 0) {
			stepNumber = graph.exportToXmlFile(stepNumber);
			simulateStep();
		}
	}
	
	/**
	 * Gets the graph of the algorithm
	 * 
	 * @param g (Graph) - Graph of the algorithm
	 */
	public Graph getGraph() {
		return graph;
	}
	
	/**
	 * Sets the transfered boolean value in all packets to false to indicate they are ready to transfer again.
	 */
	public void resetTransfered() {
		for (Node n : getGraph().getNodes()) {
			for (Packet p : n.getPackets()) {
				p.setTransfered(false);
			}
		}
	}

	public abstract Node findNextNode(Node currentPosition, Node destination);
}
