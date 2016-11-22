import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomAlgorithm extends RoutingAlgorithm {
	private Random rand;
	
	public RandomAlgorithm(Metric metric) {
		super(metric);
		rand = new Random();
	}

	@Override
	public void simulateStep() {
		
		for (int i = 0; i < getGraph().getNodes().size(); i++){
			Node n = getGraph().getNodes().get(i);
			for (int j = 0; j < n.getPackets().size(); j++) {
				Packet p = n.getPackets().get(j);
				if (!p.isTransfered()) {
					p.setTransfered(true);
					p.incrementHops();
					
					// Packet has reached it's destination, so add its hops counter to the totalHops then remove it.
					if (n.equals(p.getDestination())) {
						//need some metric stuff here
						n.removePacket(p);
						return;
					}
					
					int nextNodeIndex = rand.nextInt((n.getConnections().size()));
					n.removePacket(p);
					n.getConnections().get(nextNodeIndex).addPacket(p);
					
				}
			}
		}
		
		resetTransfered();
	}

}
