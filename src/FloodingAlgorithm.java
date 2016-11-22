import java.util.Set;
import java.util.HashSet;

/**
 * Runs the flood search algorithm which sends a packet to all the nodes connections. 
 * 
 * @author Team GetterDone
 */
public class FloodingAlgorithm extends RoutingAlgorithm {
	private Set<Packet> packets = new HashSet<Packet>();
	
	/**
	 * Steps through the simulation using the flood method
	 */
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
		Object[] packetsArray = packets.toArray();
		//need a Time To Live counter
		for(int i = 0; i < packetsArray.length; i++) {
			Object p = packetsArray[i];
			
			for(int j = 0; j < getGraph().getNodes().size(); j++) {
				Node n = getGraph().getNodes().get(j);
				if(n.countainsPacket((Packet) p)) {
					
					for(int k = 0; k < n.getConnections().size() ; k++) {
						Node con = n.getConnections().get(k);
						
						if(!con.hasSeenPacket((Packet) p)) {
							packetsSentThisStep++;
							
							//node has reach destination
							if(con.equals(((Packet) p).getDestination())) {
								n.addSeenPacket((Packet) p);
								n.removePacket((Packet) p);	
								packetsFinishedThisStep++;
							}
							else {
							con.addPacket((Packet) p);
							con.addSeenPacket((Packet) p);
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
			Object p = packetsArray[i];
			
			if(((Packet) p).getHops() > 6) {
			
				for(int j = 0; j < getGraph().getNodes().size(); j++) {
					Node n = getGraph().getNodes().get(j);
					if(n.countainsPacket((Packet) p)) {
						n.removePacket((Packet) p);
					}
				}
			}
		}
		
		for(int i = 0; i < packetsArray.length; i++) {
			Object p = packetsArray[i];
			for(int j = 0; j < getGraph().getNodes().size(); j++) {
				Node n = getGraph().getNodes().get(j);
				if(n.countainsPacket((Packet) p)) {
					int total = 0;
					for(int k = 0; k < n.getConnections().size() ; k++) {
						Node con = n.getConnections().get(k);
						if(con.hasSeenPacket((Packet) p)) {
							total++;
						}
					}
				if(total == n.getConnections().size()){
					n.removePacket((Packet) p);
				}
				}
			}
		}
		
	}
}