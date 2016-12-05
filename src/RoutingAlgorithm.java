import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

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
			this.exportToXmlFile();
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
	
	public void exportToXmlFile() {
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter("PreviousSteps.xml"));
			out.write(this.toXML());
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String toXML() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("<Step" + stepNumber + ">\n");
		
		for (Node n : graph.getNodes()) {
			sb.append("\t<Node>\n");
			sb.append("\t\t" + "<Name>" + n.getName() + "</Name>" + "\n");
			for (Node con : n.getConnections()) {
				sb.append("\t\t" + "<Connection>" + con.getName() + "</Connection>" + "\n");
			}
			for(Packet p : n.getPackets()) {
				sb.append("\t\t" + "<Packet>");
				sb.append("\t\t\t" + "<ID>" + p.getId() + "</ID>" + "\n");
				sb.append("\t\t\t" + "<Destination>" + p.getDestination().getName() + "</Destination>" + "\n");
				sb.append("\t\t\t" + "<Message>" + p.getMessage() + "</PacketID>" + "\n");
				sb.append("\t\t\t" + "<Hops>" + p.getHops() + "</PacketID>" + "\n");
				sb.append("\t\t\t" + "<Count>" + p.getCount() + "</PacketID>" + "\n");
				if (p.isTransfered()) sb.append("\t\t\t" + "<Transferred>" + "true" + "</PacketID>" + "\n");
				else sb.append("\t\t\t" + "<Transferred>" + "false" + "</PacketID>" + "\n");
				sb.append("\t\t" + "</Packet>");
			}
			
			for(Packet p1 : n.getSeenPackets()) {
				sb.append("\t\t" + "<SeenPacket>");
				sb.append("\t\t\t" + "<ID>" + p1.getId() + "</ID>" + "\n");
				sb.append("\t\t\t" + "<Destination>" + p1.getDestination().getName() + "</Destination>" + "\n");
				sb.append("\t\t\t" + "<Message>" + p1.getMessage() + "</Message>" + "\n");
				sb.append("\t\t\t" + "<Hops>" + p1.getHops() + "</Hops>" + "\n");
				sb.append("\t\t\t" + "<Count>" + p1.getCount() + "</Count>" + "\n");
				sb.append("\t\t\t" + "<TimeToLive>" + p1.getTTL() + "</TimeToLive>" + "\n");
				if (p1.isTransfered()) sb.append("\t\t\t" + "<Transferred>" + "true" + "</Transferred>" + "\n");
				else sb.append("\t\t\t" + "<Transferred>" + "false" + "</Transferred>" + "\n");
				sb.append("\t\t" + "</Packet>");
			}
			sb.append("\t</Node>\n");
		}
		sb.append("</Step" + stepNumber + ">\n");
		stepNumber++;
		return sb.toString();
	}

	public abstract Node findNextNode(Node currentPosition, Node destination);
}
