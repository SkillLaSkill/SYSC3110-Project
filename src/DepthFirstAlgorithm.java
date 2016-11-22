
import java.util.Stack;

public class DepthFirstAlgorithm extends RoutingAlgorithm {

	public DepthFirstAlgorithm(Metric metric) {
		super(metric);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void simulateStep() {
		
		for (int i = 0; i < getGraph().getNodes().size(); i++){
			Node n = getGraph().getNodes().get(i);
			for (int j = 0; j < n.getPackets().size(); j++) {
				Packet p = n.getPackets().get(j);
				if (!p.isTransfered()) {
					p.setTransfered(true);
					// Packet has reached it's destination, so remove it.
					if (n.equals(p.getDestination())) {
						n.removePacket(p);
					}
					
					n.removePacket(p);
					
					Node next = findNextNodeDFS(n, p.getDestination());
					
					if (next != null) {
						next.addPacket(p);
					}
					else {
						// Found its destination
					}
				}
			}
		}
		
		resetTransfered();
		
	}
	
	private Node findNextNodeDFS(Node currentPosition, Node destination) {
		Stack<SearchNode> stk = new Stack<SearchNode>();
		
		// Enqueue the root.
		SearchNode root = new SearchNode(currentPosition);
		root.setDistance(0);
		stk.push(root);
		while (!stk.isEmpty()) {
			SearchNode current = stk.pop();
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
					
					stk.push(bn);
				}	
			}
		}
		return null;
	}


}
