import java.util.ArrayList;
import java.util.List;

/**
 * This is the class that is used to make a node. The class creates a object
 * that has a name, a message and a list of connections
 * 
 * @author Team GetterDone
 *
 */
public class Node {
	private String name;
	private List<Node> connections = new ArrayList<Node>();
	private List<Packet> packets = new ArrayList<Packet>();
	
	/**
	 *  Creates a node with a name but no connections or message
	 *  
	 * @param n (String)
	 */
	public Node(String n){
		name = n;
	}
	
	public void addPacket(Packet p) {
		packets.add(p);
	}
	public void removePacket(Packet p) {
		packets.remove(p);
	}
	
	public List<Packet> getPackets() {
		return packets;
	}
	
	/**
	 * Return the nodes name 
	 * 
	 * @return String
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the nodes name 
	 * 
	 * @param String
	 */
	public void setName(String n) {
		name = n;
	}
	

	//	NEW
	public void addConnection(String n) {
		addConnection(new Node(n));		
	}
	
	
	public void addConnection(Node n)
	{
		if (connections.contains(n)) {
			System.out.println("Couldn't add connection!");
			return;
		}
		connections.add(n);
	}

	
	//	NEW
	public void removeConnection(String n) {
		removeConnection(new Node(n));
		
	}
	
	public void removeConnection(Node n) {
		if (!connections.contains(n)) {
			System.out.println("Couldn't remove connection!");
			return;
		}
		connections.remove(n);
	}
	
	//	NEW
	public List<Node> getConnections() {
		return connections;
	}
	
	//	NEW
	public boolean isConnected(Node n) {
		if(n == null)	System.out.println("Null string!");
		for (Node node : connections)
			if (node.equals(n)) return true;
		return false;
	}
	
	/**
	 * Displays the nodes information
	 */
	public void displayNode(){
		System.out.println("Node: " + name);
		//System.out.println("Message: " + message);
	}
	
	/**
	 * Checks if the nodes have the same names
	 * 
	 * @param o (Object)
	 * 
	 * @return boolean
	 */
	@Override
	public boolean equals(Object o) {
		if(o instanceof Node) {
			Node n = (Node) o;
			return this.getName().equals(n.getName());
			
		}
		return false;
		
	}
	
	@Override
	public int hashCode() {
		return name.hashCode();
	}
}
