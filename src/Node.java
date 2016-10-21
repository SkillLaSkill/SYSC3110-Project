import java.util.*;

public class Node {
	private List<String> connections = new ArrayList<String>();
	private String message;
	private String name;
	
	public Node(String n){
		name = n;
		message = "";
	}
	
	public String getName() {
		return name;
	}
	
	public List<String> getConnections(){
		return connections;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String m){
		message = m;
	}
	
	public void displayNode() {
		System.out.println("Node " + name + ":");
		System.out.println("Message: " + message);
		System.out.print("Connections: ");
		for (String n : connections) {
			System.out.print(n + ", ");
		}
		System.out.println("");
	}
	
	public void addConnection(String con){
		connections.add(con);
	}
	public void addConnections(String[] cons) {
		for (String con : cons) {
			addConnection(con);
		}
	}
	@Override
	public boolean equals(Object o) {
		if (o == null) return false;
		if (!(o instanceof Node)) {
			return false;
		}
		Node n = (Node)o;
		return this.name.equals(n.name);
	}
}
