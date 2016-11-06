import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Graph {
	
	//private List<Node> Nodes = new ArrayList<Node>();
	private HashMap<Node, List<Node>> connections = new HashMap<>();
	
	public void addNode(Node n)
	{
		connections.put(n, null);
	}
	/**
	 * Gets a node given its name
	 * 
	 * @param name (String)
	 * @return Node
	 */
	public Node getNode(String name){
		// Goes through all nodes made
		for (Node n : connections.keySet()) {
			if (n.getName().equals(name)) {
				return n;
			}
		}
		// Returns null if the node name doesn't exist
		return null;
	}
	
	
	public List<Node> getNodes()
	{
		return new ArrayList<Node>(connections.keySet());
	}
	
	public List<Node> getConnections(Node n) {
		return connections.get(n);
	}
	/**
	 * Creates connections for the given node using given connections
	 * 
	 * @param node (Node)
	 * @param connections (String)
	 */
	
	public void addConnection(Node A, Node B) {
		if (A.equals(B)) return; // Don't add self as a connection.
		
		if (connections.get(A) == null) {
			connections.put(A, new ArrayList<Node>());
		}
		if (connections.get(B) == null) {
			connections.put(B, new ArrayList<Node>());
		}
		
		List<Node> conListA = connections.get(A);
		List<Node> conListB = connections.get(B);
		
		// Don't add duplicates to connection list to keep graph simple.
		if (!conListA.contains(B) ) {
			conListA.add(B);
			conListB.add(A);
		}
	}
	
	public void addNodeConnections(Node A, List<Node> nodesToAdd) {
		for (Node n : nodesToAdd) {
			addConnection(A, n);
		}
	}
	
	// Move
	public boolean addNodeConnectionsByName(Node node, String connections) {
		// Separates all connections into individual strings
		String[] nodeConnections = connections.split(" ");
		List<Node> nodesToAdd = new ArrayList<Node>();
		for (String con : nodeConnections) {
			Node n = getNode(con);
			if (n == null) {
				return false;
			} 
			
			nodesToAdd.add(n);
		}
		
		addNodeConnections(node, nodesToAdd);
		return true;
	}
	
	/**
	 * Displays all nodes information
	 */
	// Move
	public void displayNodes(){
		Master.output.append("\nList of nodes and their connections:\n");
		if (size() == 0) {
			Master.output.append("No nodes.\n");
		}
		for (Node n : connections.keySet()){
			//n.displayNode();
		} 
	}
	
	public boolean contains(Node n)
	{
		return connections.containsKey(n);
	}
	public int size()
	{
		return connections.keySet().size();
	}
	public void clear()
	{
		connections.clear();
	}
}
