import java.util.Stack;

/**
 * Runs the depth first search algorithm which travels as far  as possible. Once it hits a
 * end point, it goes to the previous location and checks if it can continue there.
 * 
 * @author Team GetterDone
 */
public class DepthFirstAlgorithm extends BreadthDepthShared { 
	
	/**
	 * Finds the next node that the depth fire method will go to
	 * 
	 * @param currentPosition (Node) - The current node packet is at
	 * @param destination (Node) - The node the packet is trying to reach
	 * @return Node - The next node packet will go to
	 */
	@Override
	public Node findNextNode(Node currentPosition, Node destination) {
		Stack<SearchNode> stk = new Stack<SearchNode>();
		
		// Enqueue the root.
		SearchNode root = new SearchNode(currentPosition);
		root.setDistance(0);
		stk.push(root);
		while (!stk.isEmpty()) {
			SearchNode current = stk.pop();
			// If not discovered
			if (current.getDistance() == -1) {
				// Set to discovered.
				current.setDistance(0);
				
				// If the destination node is found, follow the parents up until before the root and return.
				if (current.getNode().equals(destination)) {
					// Destination was current Position
					SearchNode x = null; 
					while (!stk.isEmpty()) {
						x = stk.pop();
					}
					return x.getNode();
				}
				
				for (Node n : current.getNode().getConnections()) {
					SearchNode bn = new SearchNode(n);
					if (bn.getDistance() == -1) {
						stk.push(bn);
					}	
				}
			}			
		}
		return null;
	}
}
