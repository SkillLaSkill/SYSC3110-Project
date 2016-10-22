import java.util.*;

/**
 * This is the class that is used to make a node. The class creates a object
 * that has a name, a message and a list of connections
 * 
 * @author Team GetterDone
 *
 */
public class Node {
	private List<String> connections = new ArrayList<String>();
	private String message;
	private String name;
	
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
	 * Returns a list of all the connections
	 * 
	 * @return List<String>
	 */
	public List<String> getConnections(){
		return connections;
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
	 * Sets the message to the given incoming message
	 * 
	 * @param m (String)
	 */
	public void setMessage(String m){
		message = m;
	}
	
	/**
	 * Displays the nodes information (name, message, and all connections)
	 */
	public void displayNode() {
		Master.output.append("Node " + name + ":\n");
		Master.output.append("Message: " + message + "\n");
		Master.output.append("Connections: ");
		
		// Goes through all connections and prints their names
		for (String n : connections) {
			Master.output.append(n + ", ");
		}
		Master.output.append("\n");
	}
	
	/**
	 * Adds a singular connection reference to the node
	 * 
	 * @param con (String)
	 */
	public void addConnection(String con){
		// Don't allow duplicates or self as a possible connection, i.e. keep graph simple.
		if (!connections.contains(con) && !con.equals(this.getName())){
			connections.add(con);	
		}	
	}
	
	/**
	 * Adds multiple connection references to the node
	 * 
	 * @param cons (String[])
	 */
	public void addConnections(String[] cons) {
		for (String con : cons) {
			addConnection(con);
		}
	}
	
	/**
	 * Checks if the nodes have the name names
	 */
	@Override
	public boolean equals(Object o) {
		// Parameter is empty
		if (o == null) return false;
		// Parameter isn't a node
		if (!(o instanceof Node)) {
			return false;
		}
		// Makes sure that object is a node, then check that their names are the same
		Node n = (Node)o;
		return this.name.equals(n.name);
	}
}
