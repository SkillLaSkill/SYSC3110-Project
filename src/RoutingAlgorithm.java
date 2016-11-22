
public abstract class RoutingAlgorithm {
	private Graph graph;
	private Metric metric;
	
	public RoutingAlgorithm(Metric metric) {
		this.metric = metric;
	}
	
	public Metric getMetric() {
		return metric;
	}
	
	public void setGraph(Graph g) {
		this.graph = g;
	}
	
	public abstract void simulateStep();
	
	public void simulate(int steps) {
		while (steps-- > 0) simulateStep();
	}
	
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
}
