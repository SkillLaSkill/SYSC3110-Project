import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Graph {
	
	//private List<Node> Nodes = new ArrayList<Node>();
	private HashMap<Node, List<Node>> nodeInformation = new HashMap<>();
	
	public void addNode(Node n)
	{
		nodeInformation.put(n, null);
	}
	
	/**
	 * Gets a node given its name
	 * 
	 * @param name (String)
	 * @return Node
	 */
	public Node getNode(String name){
		// Goes through all nodes made
		for (Node n : nodeInformation.keySet()) {
			if (n.getName().equals(name)) {
				return n;
			}
		}
		// Returns null if the node name doesn't exist
		return null;
	}
	
	/**
	 * Returns a list of all the nodes 
	 * 
	 * @return List<Node>
	 */
	public List<Node> getNodes()
	{
		return new ArrayList<Node>(nodeInformation.keySet());
	}
	
	/**
	 * Returns all the nodeInformation of a node
	 * 
	 * @param n (Node)
	 * 
	 * @return List<Node> (the nodeInformation)
	 */
	public List<Node> getConnections(Node n) {
		return nodeInformation.get(n);
	}
	
	/**
	 * Creates nodeInformation for the given node using given nodeInformation
	 * 
	 * @param node (Node)
	 * @param nodeInformation (String)
	 */
	public void addConnection(Node A, Node B) {
		if (A.equals(B)) return; // Don't add self as a connection.
		
		if (nodeInformation.get(A) == null) {
			nodeInformation.put(A, new ArrayList<Node>());
		}
		if (nodeInformation.get(B) == null) {
			nodeInformation.put(B, new ArrayList<Node>());
		}
		
		List<Node> conListA = nodeInformation.get(A);
		List<Node> conListB = nodeInformation.get(B);
		
		// Don't add duplicates to connection list to keep graph simple.
		if (!conListA.contains(B) ) {
			conListA.add(B);
			conListB.add(A);
		}
	}
	
	/**
	 * Adds multiple nodeInformation to the given Node 
	 * 
	 * @param A (Node)
	 * @param nodesToAdd (List<Node>)
	 */
	public void addNodeConnections(Node A, List<Node> nodesToAdd) {
		for (Node n : nodesToAdd) {
			addConnection(A, n);
		}
	}
	
	/**
	 * Adds a connection to a node give the nodeInformation name
	 * 
	 * @param node	(Node)
	 * @param nodeInformation	(String)
	 * 
	 * @return boolean
	 */
	public boolean addNodeConnectionsByName(Node node, String nodeInformation) {
		// Separates all nodeInformation into individual strings
		String[] nodeConnections = nodeInformation.split(" ");
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
	public void displayNodes(){
		Master.output.append("\nList of nodes and their nodeInformation:\n");
		if (size() == 0) {
			Master.output.append("No nodes.\n");
		}
		for (Node n : nodeInformation.keySet()){
			n.displayNode();
		} 
	}
	
	/**
	 * Checks if the nodeInformation contains a specific node
	 * 
	 * @param n	(Node)
	 * 
	 * @return boolean
	 */
	public boolean contains(Node n)
	{
		return nodeInformation.containsKey(n);
	}
	
	/**
	 * Gets the number of nodes
	 *  
	 * @return integer
	 */
	public int size()
	{
		return nodeInformation.keySet().size();
	}
	
	/** 
	 * Removes all of the nodes
	 */
	public void clear()
	{
		nodeInformation.clear();
	}
}
