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
		if (!isSimulating()) {
			return;
		}
		
		for (int i = 0; i < getGraph().getNodes().size(); i++){
			Node n = getGraph().getNodes().get(i);
			for (int j = 0; j < n.getPackets().size(); j++) {
				Packet p = n.getPackets().get(j);
				if (!p.isTransfered()) {
					p.setTransfered(true);
					
					// Packet has reached it's destination, so add its hops counter to the totalHops then remove it.
					if (n.equals(p.getDestination())) {
						p.addHopsToTotal();
						n.removePacket(p);
						return;
					}
					
					int nextNodeIndex = rand.nextInt((n.getConnections().size()));
					n.removePacket(p);
					n.getConnections().get(nextNodeIndex).addPacket(p);
					
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