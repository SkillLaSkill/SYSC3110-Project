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
	 * @param n (String)
	 */
	public Node(String n){
		name = n;
		message = "";
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
	 * Returns the message that the node holds
	 * 
	 * @return String
	 */
	public String getMessage() {
		return message;
	}
	
	/**
	 * Sets the nodes name 
	 * 
	 * @param String
	 */
	public void setName(String n) {
		name = n;
	}
	
	/**
	 * Sets the message to the given incoming message
	 * 
	 * @param m (String)
	 */
	public void setMessage(String m){
		message = m;
	}
	
	//	NEW
	public void addConnection(String n) {
		if(!connections.contains(n) || !(n.equals(null)))	
		{
			connections.add(n);
			return;
		}
		System.out.println("Couldn't add connection!");
	}
	
	public void addConnection(Node n)
	{
		this.addConnection(n.getName());
	}

	
	//	NEW
	public void removeConnection(String n) {
		if(connections.contains(n) || !(n.equals(null)))
		{
			connections.remove(n);
			return;
		}
		System.out.println("Couldn't remove connection!");
	}
	
	//	NEW
	public List<String> getConnections() {
		return connections;
	}
	
	//	NEW
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
