import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Set;

/**
 * Runs the Flooding search algorithm which passes each packet a node contains
 * to all its neighboring nodes that haven't already been visited by the packet
 * 
 * @author Team GetterDone
 */
public class FloodingAlgorithm extends RoutingAlgorithm { 
	
	
	private Set<Packet> packets = new HashSet<Packet>();
	/**
	 * Steps through the simulation using the Flooding method
	 */
	@Override
	public void simulateStep() {
		int packetsSentThisStep = 0;
		int packetsFinishedThisStep = 0;
		List<String> stepTransferInfo = new LinkedList<>();
		Map<Node, ArrayList<Packet>> packetAddMap = new HashMap<Node, ArrayList<Packet>>();
		
		for (int i = 0; i < getGraph().getNodes().size(); i++){
			Node n = getGraph().getNodes().get(i);
			
			for (int j = 0; j < n.getPackets().size(); j++) {
				Packet p = n.getPackets().get(j);
				
				for( int k = 0; k < n.getConnections().size(); k++) {
					Node con = n.getConnections().get(k);
					
					if(!con.hasSeenPacket(p)){ //checks if connection has seen the packet
						con.addSeenPacket(p); //adds Packet to hasSeen list of Connection
						packetAddMap.putIfAbsent(con, new ArrayList<Packet>());
						packetAddMap.get(con).add(p);
						
						p.incrementHops();
						packetsSentThisStep++;
						stepTransferInfo.add("Transfered " + p.getMessage() + " from node " + n.getName() + " to " + con.getName() + ".");
					}
					
					// Packet has reached it's destination, so add its hops counter to the totalHops then remove it.
					if (con.equals(p.getDestination())) {
						packetsFinishedThisStep++;
					}
				}
				//once packet has been sent to eligible connections, remove packet from node
				n.removePacket(p);
			}
		}
		//return metrics to the metric class
		getMetric().addHops(packetsSentThisStep);
		getMetric().addCompleteTransfers(packetsFinishedThisStep);
		
		//add required packets to each node 
		for(Entry<Node, ArrayList<Packet>> mapEntry : packetAddMap.entrySet()) {
			Node key = mapEntry.getKey();
			ArrayList<Packet> packets = mapEntry.getValue();
			for(Packet p : packets) {
				key.addPacket(p);
			}
		}
		
		//creates a set of all packets in the graph
		for(int i = 0; i < getGraph().getNodes().size(); i++) {
			Node n = getGraph().getNodes().get(i);
			
			for (int j = 0; j < n.getPackets().size(); j++) {
				Packet p = n.getPackets().get(j);
				packets.add(p);
			}
		}
	}
	
	@Override
	public Node findNextNode(Node currentPosition, Node destination) {
		return null;
	}
}
//can remove time to live stuff
