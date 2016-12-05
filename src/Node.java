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
	private List<Packet> packetsSeen = new ArrayList<Packet>();
	
	/**
	 *  Creates a node with a name but no connections or message
	 *  
	 * @param n (String) - The name of the node
	 */
	public Node(String n){
		name = n;
	}
	
	/**
	 * Adds a packet to the list of current packets
	 * 
	 * @param p (Packet) - Packet that will be added
	 */
	public void addPacket(Packet p) {
		packets.add(p);
	}
	
	/**
	 * Removes a packet from the list of current packets
	 * 
	 * @param p (Packet) - Packet that will be removed
	 */
	public void removePacket(Packet p) {
		packets.remove(p);
	}
	
	/**
	 * Gets a list of all the current packets
	 * 
	 * @return List<Packet> - List of all current packets
	 */
	public List<Packet> getPackets() {
		return packets;
	}
	
	/**
	 * Gets a list of all the seen packets
	 * 
	 * @return List<Packet> - List of all seen packets
	 */
	public List<Packet> getSeenPackets() {
		return packetsSeen;
	}
	
	/**
	 * Sets the list of all the current packets
	 * 
	 * @param packets (List<Packet>) - List of all current packets
	 */
	public void setPackets(List<Packet> packets) {
		this.packets = packets;
		
	}
	
	/**
	 * Checks if the current packets contains the given packet
	 * 
	 * @param p (Packet) - Packet you want to check
	 * @return boolean - True if in list; False if not in list
	 */
	public boolean containsPacket(Packet p) {
		return packets.contains(p);
	}
	
	/**
	 * Adds a packet to the list of seen packets
	 * 
	 * @param p (Packet) - Packet that will be added
	 */
	public void addSeenPacket(Packet p) {
		packetsSeen.add(p);
	}
	
	/**
	 * Checks if the packet given has been seen
	 * 
	 * @param p (Packet) - Packet you want to check for
	 * @return boolean - True = packet was seen; False = packet was not seen
	 */
	public boolean hasSeenPacket(Packet p) {
		return packetsSeen.contains(p);
	}
	
	/**
	* Gets a list of all packets the node has seen
	* 
	* @return List<Packet> - List of packets node has seen
	*/
	public List<Packet> getPacketsSeen() {
		return packetsSeen;
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
	 * @param n (String) - Desired name
	 */
	public void setName(String n) {
		name = n;
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
		n.connections.add(this);
	}

	/**
	 * Removes the connection of two nodes.
	 * 
	 * @param n (Node) - Connected node to remove connection with.
	 */
	public void removeConnection(Node n) {
		try{
			if (!connections.contains(n)) {
				System.out.println("Couldn't remove connection!");
				return;
			}
			connections.remove(n);
			n.connections.remove(this);
		}
		catch(NullPointerException nu)
		{}
	}
	
	/**
	 * Gets a list of all names of the nodes connected
	 * 
	 * @return List<Node> - Connections list
	 */
	public List<Node> getConnections() {
		return connections;
	}
	
	/**
	 * Checks if the node is connected to another node given its name
	 * 
	 * @param n (Node) - Node you want to check is connected
	 * @return boolean - True = connected; False = not connected
	 */
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
