import java.util.LinkedList;
import java.util.Queue;

/**
 * Runs the shortest path search algorithm which sends a packet to add connected nodes which don't
 * currently have a packet. This keeps going until it has no more nodes without a packet
 * 
 * @author Team GetterDone
 */
public class ShortestPathAlgorithm extends BreadthDepthShared { 


	
	/**
	 * Finds the next node that the breath first method will go to
	 * 
	 * @param currentPosition (Node) - The current node packet is at
	 * @param destination (Node) - The node the packet is trying to reach
	 * @return Node - The next node packet will go to
	 */
	@Override
	public Node findNextNode(Node currentPosition, Node destination) {
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
