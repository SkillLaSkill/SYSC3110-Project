import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Runs the random search algorithm which keeps going to a random node until
 * it reaches the destination.
 * 
 * @author Team GetterDone
 */
public class RandomAlgorithm extends RoutingAlgorithm { 
	private Random rand = new Random();

	/**
	 * Steps through the simulation using the random method
	 */
	@Override
	public void simulateStep() {
		int packetsSentThisStep = 0;
		int packetsFinishedThisStep = 0;
		List<String> stepTransferInfo = new LinkedList<>();
		
		for (int i = 0; i < getGraph().getNodes().size(); i++){
			Node n = getGraph().getNodes().get(i);
			for (int j = 0; j < n.getPackets().size(); j++) {
				Packet p = n.getPackets().get(j);
				if (!p.isTransfered()) {
					p.setTransfered(true);
					packetsSentThisStep++;
					// Packet has reached it's destination, so add its hops counter to the totalHops then remove it.
					if (n.equals(p.getDestination())) {
						packetsFinishedThisStep++;
						n.removePacket(p);
						return;
					}
					// If not destination, send packet to 
					else {
						int nextNodeIndex = rand.nextInt((n.getConnections().size()));
						n.removePacket(p);
						Node next = n.getConnections().get(nextNodeIndex);
						next.addPacket(p);
						stepTransferInfo.add("Transfered " + p.getMessage() + " from node " + n.getName() + " to " + next.getName() + ".");
					}
				}
			}
		}
		
		getMetric().addHops(packetsSentThisStep);
		getMetric().addCompleteTransfers(packetsFinishedThisStep);
		getMetric().setTransferInfo(stepTransferInfo);
		resetTransfered();
	}

	@Override
	public Node findNextNode(Node currentPosition, Node destination) {
		return null;
	}

}
