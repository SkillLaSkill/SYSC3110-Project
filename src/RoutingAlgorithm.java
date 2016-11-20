
public abstract class RoutingAlgorithm {
	private Graph graph;
	private boolean simulating;
	public RoutingAlgorithm(Graph g){
		this.graph = g;
	}
	
	public abstract void simulateStep();
	
	public void simulate(int steps) {
		while (steps-- > 0) simulateStep();
	}
	
	public Graph getGraph() {
		return graph;
	}
	public boolean isSimulating() {
		return simulating;
	}
	public void setSimulating(boolean simulating) {
		this.simulating = simulating;
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
