import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Graph {
	

	private HashMap<Node, List<Node>> nodeInformation = new HashMap<>();
	
	/**
	 * Adds a node to the HashMap keys
	 * 
	 * @param n (Node)
	 */
	public void addNode(Node n)
	{
		nodeInformation.put(n, null);
	}
	
	/**
	 * Creates connections for the given node to the other given node
	 * 
	 * @param A (Node)
	 * @param B (Node)
	 */
	public void addConnection(Node A, Node B) {
		
		if (A.equals(B)) return; // Don't add self as a connection.
		
		if (nodeInformation.get(A) == null) {
			nodeInformation.put(A, new ArrayList<Node>());
		}
		if (nodeInformation.get(B) == null) {
			nodeInformation.put(B, new ArrayList<Node>());
		}
		
		// Don't add duplicates to connection list to keep graph simple.
		if (!nodeInformation.get(A).contains(B) ) {
			nodeInformation.get(A).add(B);
			nodeInformation.get(B).add(A);
		}
		
		
	}
	
	
	/**
	 * Adds multiple nodeInformation to the given Node 
	 * 
	 * @param A (Node)
	 * @param nodesToAdd (List<Node>)
	 *
	 */
	public void addNodeConnections(Node A, List<Node> nodesToAdd) {
		for (Node n : nodesToAdd) {
			addConnection(A, n);
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
	 * Gets a node given its name
	 * 
	 * @param name (String)
	 * 
	 * @return Node
	 */
	public Node getNode(String name){
		// Goes through all nodes made
		for (Node n : nodeInformation.keySet()) {
			if (n.getName().equals(name)) {
				return n;
			}
		}
		// Returns null and prints if the node name doesn't exist
		System.out.println("Node does not exist!");
		return null;
	}
	
	/**
	 * Gets a list of all the nodes
	 * 
	 * @return List<Node>
	 */
	public List<Node> getNodes()
	{
		return new ArrayList<Node>(nodeInformation.keySet());
	}
	
	/**
	 * Returns all the connections of a node
	 * 
	 * @param n (Node)
	 * 
	 * @return List<Node> 
	 */
	public List<Node> getConnections(Node n) {
		return nodeInformation.get(n);
	}
	
	/**
	 * Removes a given node 
	 * 
	 * @param n (Node)
	 */
	public void removeNode(Node n)
	{
		for(Node node: nodeInformation.get(n))
		{
			getConnections(node).remove(n);
			System.out.println("Node has been removed!");
			return;
		}
		nodeInformation.remove(n);
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
