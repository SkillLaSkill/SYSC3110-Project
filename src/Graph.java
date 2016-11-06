import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Graph {
	

	private HashMap<Node, List<Node>> nodeInformation = new HashMap<>();
	
	public void addNode(Node n)
	{
		nodeInformation.put(n, null);
	}
	

	
	/**
	 * Returns all the nodeInformation of a node
	 * 
	 * @param n (Node)
	 * 
	 * @return List<Node> (the nodeInformation)
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
	
	public void removeNodes(Node n)
	{
		for(Node node: connections.get(n))
		{
			getConnections(node).remove(n);
		}
		connections.remove(n);
	}
	
	public void removeNode(String name)
	{
		for(Node node: connections.keySet())
		{
			if(node.getName() == name)
			{
				removeNodes(node);
				System.out.println("Node has been removed!");
			}
		}
		System.out.println("No node with that name was found!");
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
	
	public void buildGraph(){
		// Creates all nodes.
		Node A = new Node("A");
		Node B = new Node("B");
		Node C = new Node("C");
		Node D = new Node("D");
		Node E = new Node("E");
		
		addNode(A);
		addNode(B);
		addNode(C);
		addNode(D);
		addNode(E);
		
		// Adds all connections to the nodes.
		addNodeConnectionsByName(A, "B C");
		addNodeConnectionsByName(B, "A D E");
		addNodeConnectionsByName(C, "A D");
		addNodeConnectionsByName(D, "B C");
		addNodeConnectionsByName(E, "A B");
	}
}
