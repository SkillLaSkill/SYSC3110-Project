import java.util.LinkedList;
import java.util.Queue;

/**
 * Runs the shortest path search algorithm which sends a packet to add connected nodes which don't
 * currently have a packet. This keeps going until it has no more nodes without a packet
 * 
 * @author Team GetterDone
 */
public class ShortestPathAlgorithm extends RoutingAlgorithm { 

	/**
	 * Steps through the simulation using the shortest path method
	 */
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
					packetsSentThisStep++;
					// Packet has reached it's destination, so remove it.
					if (n.equals(p.getDestination())) {
						n.removePacket(p);
						packetsFinishedThisStep++;
					}
					
					n.removePacket(p);
					
					Node next = findNextNodeBFS(n, p.getDestination());
					
					if (next != null) {
						next.addPacket(p);
					}
					else {
						// Something's wrong with graph?
					}
				}
			}
		}
		getMetric().addHops(packetsSentThisStep);
		getMetric().addCompleteTransfers(packetsFinishedThisStep);
		resetTransfered();
	}
	
	/**
	 * Finds the next node that the breath first method will go to
	 * 
	 * @param currentPosition (Node) - The current node packet is at
	 * @param destination (Node) - The node the packet is trying to reach
	 * @return Node - The next node packet will go to
	 */
	private Node findNextNodeBFS(Node currentPosition, Node destination) {
		Queue<SearchNode> q = new LinkedList<>();
		
		// Enqueue the root.
		SearchNode root = new SearchNode(currentPosition);
		root.setDistance(0);
		q.add(root);
		
		while (!q.isEmpty()) {
			SearchNode current = q.poll();
			//System.out.println("Current: " + current.getNode().getName() + " Dist: " + current.getDistance());
			
			// If the destination node is found, follow the parents up until before the root and return.
			if (current.getNode().equals(destination)) {
				// Destination was current Position
				if (current.getParent() == null) break;
				while (current.getParent().getParent() != null) {
					current = current.getParent();
				}
				return current.getNode();
			}
			
			for (Node n : current.getNode().getConnections()) {
				SearchNode bn = new SearchNode(n);
				if (bn.getDistance() == -1) {
					bn.setDistance(current.getDistance() + 1);
					bn.setParent(current);
					
					q.add(bn);
				}	
			}
		}
		return null;
	}
}
