import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
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
	
	public int exportToXmlFile(int stepNumber) {
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter("PreviousSteps.xml"));
			out.write(this.toXML(stepNumber));
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return stepNumber++;
	}
	
	public String toXML(int stepNumber) {
		StringBuilder sb = new StringBuilder();
		
		sb.append("<Step" + stepNumber + ">\n");
		
		for (Node n : nodes) {
			sb.append("\t<Node>\n");
			sb.append("\t\t" + "<Name>" + n.getName() + "</Name>" + "\n");
			for (Node con : n.getConnections()) {
				sb.append("\t\t" + "<Connection>" + con.getName() + "</Connection>" + "\n");
			}
			for(Packet p : n.getPackets()) {
				sb.append("\t\t" + "<Packet>");
				sb.append("\t\t\t" + "<ID>" + p.getId() + "</ID>" + "\n");
				sb.append("\t\t\t" + "<Destination>" + p.getDestination().getName() + "</Destination>" + "\n");
				sb.append("\t\t\t" + "<Message>" + p.getMessage() + "</PacketID>" + "\n");
				sb.append("\t\t\t" + "<Hops>" + p.getHops() + "</PacketID>" + "\n");
				sb.append("\t\t\t" + "<Count>" + p.getCount() + "</PacketID>" + "\n");
				if (p.isTransfered()) sb.append("\t\t\t" + "<Transferred>" + "true" + "</PacketID>" + "\n");
				else sb.append("\t\t\t" + "<Transferred>" + "false" + "</PacketID>" + "\n");
				sb.append("\t\t" + "</Packet>");
			}
			
			for(Packet p1 : n.getSeenPackets()) {
				sb.append("\t\t" + "<SeenPacket>");
				sb.append("\t\t\t" + "<ID>" + p1.getId() + "</ID>" + "\n");
				sb.append("\t\t\t" + "<Destination>" + p1.getDestination().getName() + "</Destination>" + "\n");
				sb.append("\t\t\t" + "<Message>" + p1.getMessage() + "</Message>" + "\n");
				sb.append("\t\t\t" + "<Hops>" + p1.getHops() + "</Hops>" + "\n");
				sb.append("\t\t\t" + "<Count>" + p1.getCount() + "</Count>" + "\n");
				sb.append("\t\t\t" + "<TimeToLive>" + p1.getTTL() + "</TimeToLive>" + "\n");
				if (p1.isTransfered()) sb.append("\t\t\t" + "<Transferred>" + "true" + "</Transferred>" + "\n");
				else sb.append("\t\t\t" + "<Transferred>" + "false" + "</Transferred>" + "\n");
				sb.append("\t\t" + "</Packet>");
			}
			sb.append("\t</Node>\n");
		}
		sb.append("</Step" + stepNumber + ">\n");
		stepNumber++;
		return sb.toString();
	}
}