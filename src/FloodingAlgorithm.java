
public class FloodingAlgorithm extends RoutingAlgorithm {

	@Override
	public void simulateStep() {
		if (!isSimulating()) {
			return;
		}
		
		for (int i = 0; i < getGraph().getNodes().size(); i++){
			Node n = getGraph().getNodes().get(i);
			for (int j = 0; j < n.getPackets().size(); j++) {
				Packet p = n.getPackets().get(j);
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
