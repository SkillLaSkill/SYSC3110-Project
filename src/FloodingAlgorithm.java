import java.util.Set;
import java.util.HashSet;


public class FloodingAlgorithm extends RoutingAlgorithm {

	private Set<Packet> packets = new HashSet<Packet>();
	
	@Override
	public void simulateStep() {
		
		if (!isSimulating()) {
			return;
		}
		//creates a set of all packets in the graph
		for(Node n : getGraph().getNodes()) {
			for(Packet p : n.getPackets()) {
				packets.add(p);
			}
		}
		//need a Time To Live counter
		for(Packet p : packets) {
			for(Node n : getGraph().getNodes()) {
				if(n.countainsPacket(p)) {
					for(Node con : n.getConnections()) {
						if(!con.hasSeenPacket(p)) {
							con.addPacket(p);
							con.addSeenPacket(p);
							p.incrementHops();
						}
					}
				}
				
			}
		}
		
	}
}