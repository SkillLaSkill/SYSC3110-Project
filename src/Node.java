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
	private String message;
	private String name;
	private List<String> connections = new ArrayList<String>();
	
	/**
	 *  Creates a node with a name but no connections or message
	 *  
	 * @param n (String) - The name of the node
	 */
	public Node(String n){
		name = n;
		message = "";
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
	 * Returns the message that the node holds
	 * 
	 * @return String - Message of the node
	 */
	public String getMessage() {
		return message;
	}
	
	/**
	 * Sets the nodes name 
	 * 
	 * @param String - Desired name
	 */
	public void setName(String n) {
		name = n;
	}
	
	/**
	 * Sets the message to the given incoming message
	 * 
	 * @param m (String) - Desired message
	 */
	public void setMessage(String m){
		message = m;
	}
	
	/**
	 * Adds a connection to the node using a string
	 * 
	 * @param n (String) - Name of the connected node
	 */
	public void addConnection(String n) {
		if(!connections.contains(n) && !(n.equals(null)))	
		{
			connections.add(n);
			return;
		}
		System.out.println("Couldn't add connection!");
	}
	
	/**
	 * Adds a connection to the node using the node you want it connected to
	 * 
	 * @param n (Node) - Node you want connected
	 */
	public void addConnection(Node n)
	{
		this.addConnection(n.getName());
	}

	
	/**
	 * Removes a connections from the node
	 * 
	 * @param n (String) - Node name that is wanted to be removed
	 */
	public void removeConnection(String n) {
		if(connections.contains(n) && !(n.equals(null)))
		{
			connections.remove(n);
			return;
		}
		System.out.println("Couldn't remove connection!");
	}
	
	/**
	 * Gets a list of all names of the nodes connected
	 * 
	 * @return List<String> - Connections list
	 */
	public List<String> getConnections() {
		return connections;
	}
	
	/**
	 * Checks if the node is connected to another node given its name
	 * 
	 * @param n
	 * @return n (String) - Node name you want to check is connected
	 */
	public boolean isConnected(String n) {
		if(n.equals(null))	System.out.println("Null string!");
		for (String node : connections)
			if (node.equals(n)) return true;
		return false;
	}
	
	/**
	 * Displays the nodes information
	 */
	public void displayNode(){
		System.out.println("Node: " + name);
		System.out.println("Message: " + message);
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
