import java.util.*;

public class Node {
	private List<Node> connections;
	private String message;
	private String name;
	private Random rand = new Random();
	
	public Node(String n){
		connections = null;
		name = n;
		message = "";
	}
	public String getName() {
		return name;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void receiveRandomMessage(String incoming) {
		message = incoming;
		connections.get(rand.nextInt(connections.size())).receiveRandomMessage(incoming);
	}
	
	public void addConnection(Node con){
		connections.add(con);
	}
}
