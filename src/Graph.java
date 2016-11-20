import java.util.ArrayList;
import java.util.List;

public class Graph {
	private List<Node> nodes = new ArrayList<Node>();
	
	/**
	 * Adds a node to array of nodes
	 * 
	 * @param n (Node)
	 */
	public void addNode(Node n) {
		System.out.println(nodes.size());
		if(!(n == null) && !(nodes.contains(n)) || nodes.size() == 0)	
		{
			nodes.add(n);
		}
		else System.out.println("Couldn't add node!");
	}
	
	/*
	 * Adds new node by string/name
	 */
	public void addNode(String s)
	{
		if(!this.contains(s))
		{
			this.addNode(new Node(s));
		}
		else System.out.println("Couldn't add node name!");
	}
	
	/**
	 * Creates connections for the given node to the other given node
	 * 
	 * @param A (Node)
	 * @param B (Node)
	 */
	public void addConnection(Node A, Node B) {
		
		if(A == null || B == null)
		{
			System.out.println("Was given null Node! UH OH!");
			return;
		}
		else if (A.equals(B)) 
		{
			System.out.println("Can't add duplicate connections!");
			return;
		}
		
		else if(!contains(A) && contains(B))
		{
			System.out.println("One or both nodes don't exist!");
			return;
		}

		for (Node node: nodes) {	//NEW
			if (node.equals(A)) node.addConnection(B);
			if (node.equals(B)) node.addConnection(A);
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
	
	public void addNodeConnections(Node A, String nodesToAdd)
	{
		String[] nodes = nodesToAdd.split(" ");
		ArrayList<Node> nodesToAdd1 = new ArrayList<Node>();
		for(int i = 0; i < nodes.length; i++)
		{
			try
			{
				nodesToAdd1.add(this.getNode(nodes[i]));
			}
			catch(NullPointerException N)
			{
				System.out.println("Can't add a node that doesn't exist!");
			}
		}
		this.addNodeConnections(A, nodesToAdd1);
	}
	
	
	/**
	 * Gets a node given its name
	 * 
	 * @param name (String)
	 * 
	 * @return Node
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
	 * @return List<Node>
	 */
	public List<Node> getNodes() {
		return nodes; 
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
		for(Node nd : nodes){
			for(String s : af) {
				if (nd.getName().equals(s)) lit.add(nd);
			}
		}
		return lit;
	}
	
	/**
	 * Removes a given node 
	 * 
	 * @param n (Node)
	 */
	public void removeNode(String name) {
		if(contains(name))	removeNode(getNode(name));
		else	System.out.println("Node was not removed!");
	}
	
	public void removeNode(Node n)
	{
		if(contains(n))
		{
			nodes.remove(n);
			for(Node node: nodes)	node.removeConnection(n.getName());
		}
		else	System.out.println("Node was not removed!");
		
	}

	/**
	 * Removes the given node connection from the given node
	 * @param A
	 * @param B
	 */
	public void removeConnection(Node A, Node B) {
		if(A == null || B == null)	return;
		else if(	!(this.contains(A) || this.contains(B))	) return;
		A.removeConnection(B.getName());
		B.removeConnection(A.getName());
	}
	
	/**
	 * Removes connection given String names
	 */
	public void removeConnection(String A, String B) {
		if(A == null || B == null)	return;
		else if(	!(this.contains(A) || this.contains(B))	)	return;
		Node a = this.getNode(A);
		Node b = this.getNode(B);
		this.removeConnection(a, b);
		
	}
	
	/*
	 * Check node list for given name of node
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
	 * @param n (Node)
	 * 
	 * @return boolean
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
	
	public int size() {
		return nodes.size();
	}
	
	/*
	 * Checks if two nodes are connected
	 */
	public boolean isConnected(String First, String Second) {
		Node A =  getNode(First);
		Node B = getNode(Second);
		return isConnected(A, B);
	}

	
	public boolean isConnected(Node A, Node B) {
		
		if(contains(A) && contains(B))
		{
			return A.isConnected(B.getName());
		}
		System.out.println("Graph does not contain one or both of those nodes!");
		return false;
	}
}
