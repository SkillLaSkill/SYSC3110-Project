import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Graph {
	private List<Node> realNodes = new ArrayList<Node>();
	
	/**
	 * Adds a node to the HashMap keys
	 * 
	 * @param n (Node)
	 */
	public void addNode(Node n) {
		realNodes.add(n);
	}
	
	/**
	 * Creates connections for the given node to the other given node
	 * 
	 * @param A (Node)
	 * @param B (Node)
	 */
	public void addConnection(Node A, Node B) {
		
		if (A.equals(B)) return; // Don't add self as a connection.

		for (int i = 0; i <= realNodes.size(); i++) {	//NEW
			if (realNodes.get(i).getName() == A.getName()) realNodes.get(i).addConnection(B.getName());
			if (realNodes.get(i).getName() == B.getName()) realNodes.get(i).addConnection(A.getName());
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
		
		for (int i = 0; i <= nodeConnections.length; i++) {
			for (int j = 0; j <= realNodes.size(); j++) {
				if (realNodes.get(i).getName() == nodeConnections[i]) {
					addConnection(node, realNodes.get(j));
					return true;
				}
			}
		}
		return false;
	}
	
	
	/**
	 * Gets a node given its name
	 * 
	 * @param name (String)
	 * 
	 * @return Node
	 */
	public Node getNode(String name) {
		for (Node n : realNodes) {
			if (n.getName() == name) return n;
		}
		System.out.println("Node does not exist!");
		return null;
	}
	
	/**
	 * Gets a list of all the nodes
	 * 
	 * @return List<Node>
	 */
	public List<Node> getNodes() {
		return realNodes; 
	}
	
	/**
	 * Returns all the connections of a node
	 * 
	 * @param n (Node)
	 * 
	 * @return List<Node> 
	 */
	public List<Node> getConnections(Node n) {
		List<Node> lit = new ArrayList<Node>();
		List<String> af = n.getConnections();
		for(Node nd : realNodes){
			for(String s : af) {
				if (nd.getName() == s) lit.add(nd);
			}
		}
		return lit;
	}
	
	/**
	 * Removes a given node 
	 * 
	 * @param n (Node)
	 */
	public void removeNode(Node n) {
		removeNode(n.getName());
	}
	
	/**
	 * Removes a node given its name
	 * 
	 * @param name (String)
	 */
	public void removeNode(String name)	{
		int b = 0;
		for (int i = 0; i < realNodes.size(); i++) {
			if (realNodes.get(i).getName() == name) {
				realNodes.remove(i);
				System.out.print("YAS");
				b++;
			}
			if (realNodes.get(i).isConnected(name)) {
				realNodes.get(i).removeConnection(name);
				System.out.print("YAS");
				b++;
			}
		}
		System.out.println(b);
		if (b != 2) System.out.println("No node with that name was found!");
	}

	/**
	 * Removes the given node connection from the given node
	 * @param A
	 * @param B
	 */
	public void removeConnection(Node A, Node B) {
		removeConnection(A.getName(), B.getName());
	}
	
	/**
	 * Removes connection given String names
	 */
	public void removeConnection(String A, String B) {
		if (!isConnected(A, B)) return;
		for (int i = 0; i <= realNodes.size(); i++) {
			if (realNodes.get(i).getName() == A) {
				realNodes.get(i).removeConnection(B);
			}
		}
	}
	
	/*
	 * Check node list for given name of node
	 */
	public boolean contains(String n) {
		for(Node node: realNodes) {
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
	public boolean contains(Node n) {
		return realNodes.contains(n);
	}
	
	/** 
	 * Removes all of the nodes
	 */
	public void clear()	{
		realNodes.clear();
	}
	
	public int size() {
		return realNodes.size();
	}
	
	/*
	 * Checks if two nodes are connected
	 */
	public boolean isConnected(String First, String Second) {
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
		return isConnected(A.getName(), B.getName());
	}
}
