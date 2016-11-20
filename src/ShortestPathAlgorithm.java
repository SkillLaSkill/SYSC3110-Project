
public class ShortestPathAlgorithm extends RoutingAlgorithm {

	@Override
	public void simulateStep() {
		if (!isSimulating()) {
			return;
		}
		
		for (Node n : getGraph().getNodes()) {
			for (Packet p : n.getPackets()) {
				if (!p.isTransfered()) {
					p.setTransfered(true);
					// Packet has reached it's destination, so remove it.
					if (n.equals(p.getDestination())) {
						n.removePacket(p);
					}
					
					n.removePacket(p);
					Node next = findNextNodeBFS(n, p.getDestination());
					if (next != null) {
						next.addPacket(p);
					}
					
				}
			}
		}
		
		resetTransfered();
	}
	
	private Node findNextNodeBFS(Node currentPosition, Node destination) {
		return null;
	}

}
