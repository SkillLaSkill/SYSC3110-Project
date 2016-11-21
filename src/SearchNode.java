
class SearchNode{
	private Node node;
	private int distance = -1;
	private SearchNode parent = null;
	public SearchNode(Node n) {
		node = n;
	}
	public int getDistance() {
		return distance;
	}
	public void setDistance(int distance) {
		this.distance = distance;
	}
	public SearchNode getParent() {
		return parent;
	}
	public void setParent(SearchNode parent) {
		this.parent = parent;
	}
	Node getNode() {
		return node;
	}
	
}