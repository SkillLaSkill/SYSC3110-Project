import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomAlgorithm extends RoutingAlgorithm {
	private Random rand = new Random();

	@Override
	public void simulateStep() {
		int packetsSentThisStep = 0;
		int packetsFinishedThisStep = 0;
		
		
		for (int i = 0; i < getGraph().getNodes().size(); i++){
			Node n = getGraph().getNodes().get(i);
			for (int j = 0; j < n.getPackets().size(); j++) {
				Packet p = n.getPackets().get(j);
				if (!p.isTransfered()) {
					p.setTransfered(true);
					p.incrementHops();
					packetsSentThisStep++;
					// Packet has reached it's destination, so add its hops counter to the totalHops then remove it.
					if (n.equals(p.getDestination())) {
						packetsFinishedThisStep++;
						n.removePacket(p);
						return;
					}
					
					int nextNodeIndex = rand.nextInt((n.getConnections().size()));
					n.removePacket(p);
					n.getConnections().get(nextNodeIndex).addPacket(p);
					
				}
			}
		}
		
		getMetric().addHops(packetsSentThisStep);
		getMetric().addCompleteTransfers(packetsFinishedThisStep);
		resetTransfered();
	}

}
