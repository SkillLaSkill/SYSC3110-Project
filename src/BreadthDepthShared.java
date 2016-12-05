/**
 * Interface the runs breadth and depth algorithms
 * 
 * @author Team GetterDone
 */
public abstract class BreadthDepthShared extends RoutingAlgorithm {
		
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
					else {
						n.removePacket(p);
						
						Node next = findNextNode(n, p.getDestination());
						
						if (next != null) {
							next.addPacket(p);
						}
						else {
							// Something's wrong with graph?
						}	
					}
					
				}
			}
		}
		getMetric().addHops(packetsSentThisStep);
		getMetric().addCompleteTransfers(packetsFinishedThisStep);
		resetTransfered();
	}
	
	public abstract Node findNextNode(Node currentPosition, Node destination);
}
