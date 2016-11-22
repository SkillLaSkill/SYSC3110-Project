import java.util.Set;
import java.util.HashSet;


public class FloodingAlgorithm extends RoutingAlgorithm {


	private Set<Packet> packets = new HashSet<Packet>();
	
	@Override
	public void simulateStep() {
		int packetsSentThisStep = 0;
		int packetsFinishedThisStep = 0;
		
		//creates a set of all packets in the graph
		for(int i = 0; i < getGraph().getNodes().size(); i++) {
			Node n = getGraph().getNodes().get(i);
			for (int j = 0; j < n.getPackets().size(); j++) {
				Packet p = n.getPackets().get(j);
				packets.add(p);
			}
		}
		Packet[] packetsArray = (Packet[]) packets.toArray();
		//need a Time To Live counter
		for(int i = 0; i < packetsArray.length; i++) {
			Packet p = packetsArray[i];
			
			for(int j = 0; j < getGraph().getNodes().size(); j++) {
				Node n = getGraph().getNodes().get(j);
				if(n.countainsPacket(p)) {
					
					for(int k = 0; k < n.getConnections().size() ; k++) {
						Node con = n.getConnections().get(k);
						
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
			getMetric().addHops(packetsSentThisStep);
			getMetric().addCompleteTransfers(packetsFinishedThisStep);
		}
		//checks if any of the packets have expired and need to be removed
		for(int i = 0; i < packetsArray.length; i++) {
			Packet p = packetsArray[i];
			
			if(p.getHops() > 6) {
			
				for(int j = 0; j < getGraph().getNodes().size(); j++) {
					Node n = getGraph().getNodes().get(j);
					if(n.countainsPacket(p)) {
						n.removePacket(p);
					}
				}
			}
		}
	}
}