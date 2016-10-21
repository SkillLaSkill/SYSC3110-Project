import java.util.List;
import java.util.Random;

public class Simulation extends Thread {

	private List<Node> allNodes;
	private Random rand;
	public Simulation(List<Node> allNodes) {
		this.allNodes = allNodes;
		rand = new Random();
	}


	@Override
	public void run() {
		Node src = allNodes.get(rand.nextInt(allNodes.size()));		
		Node dest = allNodes.get(rand.nextInt(allNodes.size()));	
		String msg = "msg";
		
		System.out.println("Starting transfer from " + src.getName() + " to " + dest.getName() + " with message: " + msg + ".");
		int hops = receiveRandomMessage(src, msg, dest);
		System.out.println("Total number of hops for node " + src.getName() + " to get to " + dest.getName() + " was " + hops + ".\n");
	}
	
	private int receiveRandomMessage(Node node, String incomingMessage, Node destination) {
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
		System.out.println("Current Node: " + node.getName() + ", with message: " + incomingMessage);
		
		if(node.equals(destination)) {
			System.out.println("Reached destination.");
			return 0;
		}
		System.out.println("Next node: " + nextNode.getName());
		
		// Sends message to the next node, return the depth to the caller for statistics.
		return 1 + receiveRandomMessage(nextNode, incomingMessage, destination);
	}
}
