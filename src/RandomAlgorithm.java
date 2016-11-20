import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomAlgorithm extends RoutingAlgorithm {
	private Random rand;
	public RandomAlgorithm() {
		rand = new Random();
	}

	@Override
	public void simulateStep() {
		ArrayList<Packet> completedTransferList = new ArrayList<Packet>();
		
		if (!isSimulating()) {
			return;
		}
		
		for (Node n : getGraph().getNodes()) {
			for (Packet p : n.getPackets()) {
				if (!p.isTransfered()) {
					
					// Packet has reached it's destination, so remove it.
					if (n.equals(p.getDestination())) {
						n.removePacket(p);
					}
					
					int nextNodeIndex = rand.nextInt((n.getConnections().size()));
					n.removePacket(p);
					n.getConnections().get(nextNodeIndex).addPacket(p);
					p.setTransfered(true);
				}
			}
		}
		
		resetTransfered();
		/*
		for(Packet packet : packetList)
		{
			//System.out.println();
			//Set a new random position node for the transfer	
			List<Node> connectedNodes = getGraph().getConnections(packet.getPosition());
		
			int nextNodeIndex = rand.nextInt(connectedNodes.size());
			packet.setPosition(connectedNodes.get(nextNodeIndex));
			packet.incrementHops();
			totalHops++;
			
			// Prints the change name as well as the next node that the message will be sent to as long as it didn't reach the destinations
			if(packet.getPosition().equals(packet.getDestination())) {
				completedTransferList.add(packet);
			}
			
		}
		//Removes all completed transfers from the list of all transfers
		if(completedTransferList.isEmpty() == false) {
			packetList.removeAll(completedTransferList);
				//for(Transfer trans : completedTransferList) {
				//	completedHops += trans.getHops();
				//	completedTransferCount++;
				//}
		}
		
		*/
	}

}
