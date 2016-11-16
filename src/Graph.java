import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Graph {
	

	//private HashMap<Node, List<Node>> nodeInformation = new HashMap<>();
	private List<Node> nodes;
	
	public Graph()
	{
		nodes = new ArrayList<Node>();
	}
	
	/**
	 * Adds a node to the HashMap keys
	 * 
	 * @param n (Node)
	 */
	public void addNode(Node n)
	{
		nodes.add(n);
	}
	
	/**
	 * Creates connections for the given node to the other given node
	 * 
	 * @param A (Node)
	 * @param B (Node)
	 */
	public void addConnection(Node A, Node B) {
		// Don't add self as a connection.
		if (A.equals(B)) return; 
		// Don't add duplicates to connection list to keep graph simple.
		if(!A.hasConnection(B) && B.hasConnection(A)) return;
		// Add Connections
		A.addConnection(B);
		B.addConnection(A);
	}
	
	
	/**
	 * Adds multiple nodeInformation to the given Node 
	 * 
	 * @param A (Node)
	 * @param nodesToAdd (List<Node>)
	 *
	 */
	public void addMultipleConnections(Node A, List<Node> nodesToAdd) {
		for (Node n : nodesToAdd) {
			A.addConnection(n);
			n.addConnection(A);
		}
	}
	
	/**
	 * Adds multiple connects to a node give their names in a string
	 * 
	 * @param node (Node)
	 * @param connections (String)
	 * 
	 * @return boolean
	 */
	public boolean addMultipleConnectionsByName(Node node, String connections) {
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
		addMultipleConnections(node, nodesToAdd);
		return true;
	}
	
	
	/**
	 * Gets a node given its name by going through all nodes made. Returns null if no match is found
	 * 
	 * @param name (String)
	 * 
	 * @return Node
	 */
	public Node getNode(String name){
		for (Node n : nodes)	
			if (n.getName().equals(name))	return n;
		return null;
	}
	
	/**
	 * Returns all the connections of a node in string form
	 * 
	 * @param n (Node)
	 * 
	 * @return List<Node> 
	 */
	public String getConnections(Node n) {
		return n.toStringConnections();
	}
	
	/**
	 * Removes a given node 
	 * 
	 * @param n (Node)
	 */
	public void removeNode(Node n)
	{
		List<Node> conns = nodeInformation.remove(n);
		if (conns == null) return;
		for (int i = 0; i < conns.size(); i++) {
			removeConnection(n, conns.get(i));
		}
		System.out.println("No node with that name was found!");
	}
	
	/**
	 * Removes a node given its name
	 * 
	 * @param name (String)
	 */
	public void removeNode(String name)
	{
		if(contains(name))	removeNode(getNode(name));
	}

	
	/**
	 * Removes the given node connection from the given node
	 * @param A
	 * @param B
	 */
	public void removeConnection(Node A, Node B) {
		if (!isConnected(A, B)) return;
		nodeInformation.get(A).remove(B);
		nodeInformation.get(B).remove(A);
	}
	
	/**
	 * Removes connection given String names
	 */
	public void removeConnection(String A, String B)
	{
		removeConnection(getNode(A), getNode(B));
	}
	
	
	/*
	 * Check node list for given name of node
	 */
	
	public boolean contains(String n)
	{
		for(Node node: nodeInformation.keySet())
		{
			if (n.equals(node.getName())) return true;
		}
		return false;
	}

	
	/**
	 * Checks if the node list contains the given node
	 * 
	 * @param n (Node)
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
	/*
	 * Checks if two nodes are connected
	 */
	
	public boolean isConnected(String First, String Second)
	{
		Node A =  getNode(First);
		Node B = getNode(Second);
		if(contains(A) && contains(B))
		{
			return isConnected(A, B);
		}
		System.out.println("Does not contain one or both of those nodes!");
		return false;
	}

	
	public boolean isConnected(Node A, Node B) {
		if (contains(A) && contains(B)) {
			if (nodeInformation.get(A) == null) return false;
			
			for (Node n : nodeInformation.get(A)) {
				if (n.equals(B)) return true;
			}
		}
		return false;
	}
}
