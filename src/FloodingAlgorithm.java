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
							p.incrementHops();
							//node has reach destination
							if(con.equals(p.getDestination())) {
								n.addSeenPacket(p);
								n.removePacket(p);	
								//need some metric stuff here
							}
							else {
							con.addPacket(p);
							con.addSeenPacket(p);
							}
						}
					}
				}
				
			}
		}
		
	
	
	}
}