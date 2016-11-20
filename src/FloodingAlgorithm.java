
public class FloodingAlgorithm extends RoutingAlgorithm {

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
						// Need to remove all copies that exist of this packet elsewhere.
					}
					
					n.removePacket(p);
					for (Node conn : n.getConnections()) {
						// Need to make the node to send to is not the node the packet came from.
						
						conn.addPacket(p);
						
					}
				}
			}
		}
		
		resetTransfered();

	}

}
