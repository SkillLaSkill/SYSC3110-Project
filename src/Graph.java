import java.util.ArrayList;
import java.util.List;

/**
 * The Graph in which the list of nodes is stored and accessed by the simulation
 * 
 * @author Team GetterDone
 */
public class Graph {
	private List<Node> nodes = new ArrayList<Node>();
	
	/**
	 * Adds a node to array of nodes
	 * 
	 * @param n (Node) - Node you want added
	 */
	public void addNode(Node n) {
		if(!(n == null) && !nodes.contains(n))	
		{
			nodes.add(n);
			return;
		}
		System.out.println("Couldn't add node!");
	}
	
	/**
	 * Adds new node by string/name
	 * 
	 * @param s (String) - Name of node being added
	 */
	public void addNode(String s)
	{
		if(!this.contains(s))
		{
			this.addNode(new Node(s));
			return;
		}
	}
	
	/**
	 * Gets a node given its name
	 * 
	 * @param name (String) - Name of node wanted
	 * @return Node - Node with the given name
	 */
	public Node getNode(String name) {
		for (Node n : nodes) {
			if (n.getName().equals(name)) return n;
		}
		System.out.println("Node does not exist!");
		return null;
	}
	
	/**
	 * Gets a list of all the nodes
	 * 
	 * @return List<Node> - List of all nodes in the graph
	 */
	public List<Node> getNodes() {
		return nodes; 
	}
	
	
	/**
	 * Removes a given node given its name
	 * 
	 * @param n (String) - Name of node being removed
	 */
	public void removeNode(String name) {
		if(contains(name))	removeNode(getNode(name));
		else	System.out.println("Node was not removed!");
	}
	
	/**
	 * Removes a given node given the node
	 * 
	 * @param n (Node) - Node being removed
	 */
	public void removeNode(Node n)
	{
		if(contains(n))
		{
			nodes.remove(n);
			for(Node node: nodes)	node.removeConnection(n);
		}
		else	System.out.println("Node was not removed!");
		
	}

	
	/**
	 * Check node list for given name of node
	 * 
	 * @param n (String) - Name of node being checked for
	 * @return boolean - True if it is in graph; False if it isn't
	 */
	public boolean contains(String n) {
		for(Node node: nodes) {
			if (n.equals(node.getName())) return true;
		}
		return false;
	}
	
	/**
	 * Checks if the node list contains the given node
	 * 
	 * @param n (Node) - Node being checked for
	 * @return boolean - True if it is in graph; False if it isn't
	 */
	public boolean contains(Node n) {
		return this.contains(n.getName());
	}
	
	/** 
	 * Removes all of the nodes
	 */
	public void clear()	{
		nodes.clear();
	}
	
	
	/**
	 * Gets the number of nodes in the graph
	 * 
	 * @return int - Number of nodes in graph
	 */
	public int size() {
		return nodes.size();
	}
	
	/**
	 * Checks if two nodes are connected
	 * 
	 * @param First (string) - First node being checked
	 * @param Second (String) - Other side of connection
	 * @return boolean - True if connected; False if not connected
	 */
	public boolean isConnected(String First, String Second) {
		Node A =  getNode(First);
		Node B = getNode(Second);
		return isConnected(A, B);
	}

	/**
	 * Checks if two nodes are connected
	 * 
	 * @param First (Node) - First node being checked
	 * @param Second (Node) - Other side of connection
	 * @return boolean - True if connected; False if not connected
	 */
	public boolean isConnected(Node A, Node B) {
		
		if(contains(A) && contains(B))
		{
			return A.isConnected(B);
		}
		System.out.println("Graph does not contain one or both of those nodes!");
		return false;
	}
	
	/**
	 * Checks if packets exist
	 * 
	 * @return boolean - True if packet exists; False if not
	 */
	public boolean packetsExist() {
		for(Node n : nodes) {
			if(n.getPackets().size() > 0) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Removes all packets from all nodes
	 */
	public void resetPackets() {
		for(Node n : nodes) {
			List<Packet> packets = new ArrayList<Packet>();
			n.setPackets(packets);
		}
	}
}