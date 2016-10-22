import java.util.List;
import java.util.Random;

public class Simulation extends Thread {

	private List<Node> allNodes;
	private Random rand;
	private int transfers = 0;
	private double averageHops = 0;
	
	private boolean simulating = false;
	
	public boolean isSimulating() {
		return simulating;
	}


	public void setSimulating(boolean simulating) {
		this.simulating = simulating;
	}


	public Simulation(List<Node> allNodes) {
		this.allNodes = allNodes;
		rand = new Random();
	}


	@Override
	public void run() {
		simulating = true;
		
		// Continue simulating until another object tells us to stop.
		while (simulating) {
			Node src = allNodes.get(rand.nextInt(allNodes.size()));		
			Node dest = allNodes.get(rand.nextInt(allNodes.size()));	
			String msg = "msg";
			
			Master.output.append("Starting transfer from " + src.getName() + " to " + dest.getName() + " with message: " + msg + ".\n");
			int hops = randomTransferAlgorithm(src, msg, dest);
			if (hops != -1) {
				averageHops += hops;
				transfers++;
				Master.output.append("Total number of hops for node " + src.getName() + " to get to " + dest.getName() + " was " + hops + ".\n\n");
			}
			
		}
		if (transfers == 0) transfers = 1;
		averageHops /= transfers;
		
		Master.output.append("Average number of hops was " + averageHops + ".\n");
	}
	
	private int randomTransferAlgorithm(Node node, String incomingMessage, Node destination) {
		if (!simulating) {
			Master.output.append("Simulation ended early, will not count towards statistics.\n");
			return -1;
		}
		
		// Sets the message and increments the number of packets sent
		node.setMessage(incomingMessage);
		
		// Delay to see things as they're happening.
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		// Get the next (random) node from node's list of connections
		List<String> cons = node.getConnections();
		int nextNodeIndex = rand.nextInt(cons.size());
		Node nextNode = getNode(cons.get(nextNodeIndex));
		
		// Prints the change name as well as the next node that the message will be sent to as long as it didn't reach the destinations
		Master.output.append("Current Node: " + node.getName() + ", with message: " + incomingMessage + "\n");
		
		if(node.equals(destination)) {
			Master.output.append("Reached destination.\n");
			return 0;
		}
		Master.output.append("Next node: " + nextNode.getName() + "\n");
		
		// Sends message to the next node, return the depth to the caller for statistics.
		int x = randomTransferAlgorithm(nextNode, incomingMessage, destination);
		if (x == -1) return -1;
		else return x + 1;
	}
	
	private Node getNode(String name){
		// Goes through all nodes made
		for (int i = 0; i < allNodes.size(); i++){
			// Returns node with the given name
			if (allNodes.get(i).getName().equals(name)) {
				return allNodes.get(i);
			}
		}
		// Returns null if the node name doesn't exist
		return null;
	}
}
