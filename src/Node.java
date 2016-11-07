/**
 * This is the class that is used to make a node. The class creates a object
 * that has a name, a message and a list of connections
 * 
 * @author Team GetterDone
 *
 */
public class Node {
	//private List<String> connections = new ArrayList<String>();
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
	 * Displays the nodes information
	 */
	public void displayNode(){
		System.out.println("Node: " + name);
		System.out.println("Message: " + message);
	}
	
	/**
	 * Checks if the nodes have the name names
	 * 
	 * @param o (Object)
	 * 
	 * @return boolean
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
