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
	 * @param n (String) - The name of the node
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
	 * @return String - Name of the node
	 */
	public String getName() {
		return name;
	}
	/**
	 * Sets the nodes name 
	 * 
	 * @param String - Desired name
	 */
	public void setName(String n) {
		name = n;
	}
	
	public void addConnection(String n) {
		addConnection(new Node(n));		
	}
	
	
	/**
	 * Adds a connection to the node using the node you want it connected to
	 * 
	 * @param n (Node) - Node you want connected
	 */
	public void addConnection(Node n)
	{
		if (connections.contains(n)) {
			System.out.println("Couldn't add connection!");
			return;
		}
		connections.add(n);
		n.addConnection(this);
	}

	
	/**
	 * Removes a connections from the node
	 * 
	 * @param n (String) - Node name that is wanted to be removed
	 */
	public void removeConnection(String n) {
		removeConnection(new Node(n));
		
	}
	
	public void removeConnection(Node n) {
		if (!connections.contains(n)) {
			System.out.println("Couldn't remove connection!");
			return;
		}
		connections.remove(n);
		n.removeConnection(this);
	}
	
	/**
	 * Gets a list of all names of the nodes connected
	 * 
	 * @return List<String> - Connections list
	 */

	//	NEW
	public List<Node> getConnections() {
		return connections;
	}
	
	/**
	 * Checks if the node is connected to another node given its name
	 * 
	 * @param n
	 * @return n (String) - Node name you want to check is connected
	 */
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
	 * Checks if the given node is the exact same as this node
	 * 
	 * @param o (Object) - Object you want compared to this node
	 * @return boolean - True = same node; False = different node
	 */
	@Override
	public boolean equals(Object o) {
		if(o instanceof Node) {
			Node n = (Node) o;
			return this.getName().equals(n.getName());
			
		}
		return false;
	}
	
	/**
	 * Provides the hashcode for the object
	 * 
	 * @return int - The Hashcode
	 */
	@Override
	public int hashCode() {
		return name.hashCode();
	}
}
