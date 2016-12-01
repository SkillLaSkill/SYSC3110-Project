/**
 * Object that searches for the node
 * 
 * @author Team GetterDone
 */
class SearchNode{
	private Node node;
	private int distance = -1;
	private SearchNode parent = null;
	 
	/**
	 * Constructor
	 * 
	 * @param n (Node) - Node that is searching
	 */
	public SearchNode(Node n) {
		node = n;
	}
	
	/**
	 * Gets the distance of the node
	 * 
	 * @return int - The distance
	 */
	public int getDistance() {
		return distance;
	}
	
	/**
	 * Sets the distance of the node
	 * 
	 * @param int - The distance
	 */
	public void setDistance(int distance) {
		this.distance = distance;
	}
	
	/**
	 * Gets the parent of the node
	 * 
	 * @return SearchNode - The Parent of the node
	 */
	public SearchNode getParent() {
		return parent;
	}
	
	/**
	 * Sets the parent of the node
	 * 
	 * @param SearchNode - The Parent of the node
	 */
	public void setParent(SearchNode parent) {
		this.parent = parent;
	}
	
	/**
	 * Gets the node searching
	 * 
	 * @return Node - searching node
	 */
	Node getNode() {
		return node;
	}
	
}