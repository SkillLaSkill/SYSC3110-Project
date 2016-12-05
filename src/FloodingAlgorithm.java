import java.util.HashSet;
import java.util.Set;

/**
 * Runs the random search algorithm which keeps going to a random node until
 * it reaches the destination.
 * 
 * @author Team GetterDone
 */
public class FloodingAlgorithm extends RoutingAlgorithm { 
	
	private Set<Packet> packets = new HashSet<Packet>();
	/**
	 * Steps through the simulation using the random method
	 */
	@Override
	public void simulateStep() {
		int packetsSentThisStep = 0;
		int packetsFinishedThisStep = 0;
		
		for (int i = 0; i < getGraph().getNodes().size(); i++){
			Node n = getGraph().getNodes().get(i);
			
			for (int j = 0; j < n.getPackets().size(); j++) {
				Packet p = n.getPackets().get(j);
				
				for( int k = 0; k < n.getConnections().size(); k++) {
					Node con = n.getConnections().get(k);
					
					if(!con.hasSeenPacket(p)){ //checks if connection has seen the packet
						con.addPacket(p); 
						con.addSeenPacket(p); //adds Packet to hasSeen list of Connection
						p.incrementHops();
						packetsSentThisStep++;
					}
					
					// Packet has reached it's destination, so add its hops counter to the totalHops then remove it.
					if (con.equals(p.getDestination())) {
						packetsFinishedThisStep++;
						con.removePacket(p);
						for(Node n1 : getGraph().getNodes()) {
							if(n1.containsPacket(p)) {
								//ADD METRIC OUTPUT SAYING PACKET REACHED DESTINATION
								n1.removePacket(p);
							}
						}
					}
				}
			}
		}
		//return metrics to the metric class
		getMetric().addHops(packetsSentThisStep);
		getMetric().addCompleteTransfers(packetsFinishedThisStep);
		
		//creates a set of all packets in the graph
		for(int i = 0; i < getGraph().getNodes().size(); i++) {
			Node n = getGraph().getNodes().get(i);
			
			for (int j = 0; j < n.getPackets().size(); j++) {
				Packet p = n.getPackets().get(j);
				packets.add(p);
			}
		}
		//decrement the time to live of each packet
		Object[] packetsArray = packets.toArray();
		for(int i = 0; i < packetsArray.length; i++) {
			Packet p = (Packet)packetsArray[i];
			p.decrementTTL();
			
			//if packet has expired, remove all instances of of it from graph
			if(p.getTTL() <= 0) { //if packet has expired, remove all instances of of it from graph
				for(int j = 0; j < getGraph().getNodes().size(); j++) {
					Node n = getGraph().getNodes().get(j);
					if(n.hasSeenPacket(p)) n.removePacket(p);
				}
		
			}
		}
	}
	@Override
	public Node findNextNode(Node currentPosition, Node destination) {
		return null;
	}
}
//add time to live in packet (starting at number, decreasing to 0) - done
//add time to live getter(getTimeToLive) - done
//add packetsSeen array in Node - done
//add hasSeen method in Node - done
//add addHasSeen method in Node- done
