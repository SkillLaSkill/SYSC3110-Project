import java.util.ArrayList;

public class Graph {
	
	private ArrayList<Node> Nodes = new ArrayList<Node>();

	/**
	 * Creates a new node and adds it to the list of all nodes and returns the node
	 * 
	 * @param nodeName (String)
	 * @return Node
	 */
	public Node newNodeReturn(String nodeName) {
		Node n = new Node(nodeName);
		Nodes.add(n);
		return n;
	}
	
	public void newNode(String nodeName)
	{
		Nodes.add(new Node(nodeName));
	}
	
	public void addNode(Node n)
	{
		Nodes.add(n);
	}
	/**
	 * Gets a node given its name
	 * 
	 * @param name (String)
	 * @return Node
	 */
	public Node getNode(String name){
		// Goes through all nodes made
		for (int i = 0; i < Nodes.size(); i++){
			// Returns node with the given name
			if (Nodes.get(i).getName().equals(name)) {
				return Nodes.get(i);
			}
		}
		// Returns null if the node name doesn't exist
		return null;
	}
	
	public ArrayList<Node> getNodes()
	{
		return Nodes;
	}
	/**
	 * Creates connections for the given node using given connections
	 * 
	 * @param node (Node)
	 * @param connections (String)
	 */
	
	// Move
	public void nodeConnections(Node node, String connections) {
		// Separates all connections into individual strings
		String[] nodeConnections = connections.split(" ");
		for (String con : nodeConnections) {
			Node n = getNode(con);
			if (n == null) {
				Master.output.append("There were non-existent nodes in the connections list.");
				return;
			}
		}
		
		// Separate loop because we don't want to add connections if the list was faulty.
		for (String con : nodeConnections) {
			// To keep the graph undirected, need to make sure all nodes have connections backwards.
			getNode(con).addConnection(node.getName());
		}
		// Adds the connections to the node
		node.addConnections(nodeConnections);
	}
	
	/**
	 * Displays all nodes information
	 */
	// Move
	public void displayNodes(){
		Master.output.append("\nList of nodes and their connections:\n");
		if (Nodes.size() == 0) {
			Master.output.append("No nodes.\n");
		}
		for (Node n : Nodes){
			n.displayNode();
		} 
	}
	
	public boolean contains(Node n)
	{
		return Nodes.contains(n);
	}
	public int size()
	{
		return Nodes.size();
	}
	public void clear()
	{
		Nodes = new ArrayList<Node>();
	}
}