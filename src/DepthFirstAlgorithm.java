import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Runs the depth first search algorithm which travels as far  as possible. Once it hits a
 * end point, it goes to the previous location and checks if it can continue there.
 * 
 * @author Team GetterDone
 */

public class DepthFirstAlgorithm extends BreadthDepthShared { 

	private List<SearchNode> searchNodes;
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
		searchNodes.add(root);
		stk.push(root);
		
		while (!stk.isEmpty()) {
			SearchNode current = stk.pop();
			
			// If the destination node is found, follow the parents up until before the root and return.
			if (current.getNode().equals(destination)) {
				if (current.getParent() != null) {
					while (current.getParent().getParent() != null) {
						current = current.getParent();
					}
				}
				return current.getNode();
			}
			
			// If not discovered
			if (current.getDistance() == -1) {
				// Set to discovered.
				current.setDistance(0);
				
				// Add the unseen connections to the stack.
				for (Node n : current.getNode().getConnections()) {
					SearchNode bn = new SearchNode(n);
					
					
					if (!searchNodes.contains(bn)) {
						searchNodes.add(bn);
					}
					// Get the search node currently being kept track of.
					else {
						bn = searchNodes.get(searchNodes.indexOf(bn));
					}

					
					// If it hasn't already been visited, 
					if (bn.getDistance() == -1) {
						bn.setParent(current);
						stk.push(bn);
					}
				}
			}			
		}
		
		return null;
	}
}
