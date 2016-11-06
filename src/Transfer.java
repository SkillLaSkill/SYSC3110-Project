
import java.util.Random;
import java.util.List;
/**
 * This class creates and manipulates Transfers, which store the current node position, the destination and the message
 * 
 * 
 */
public class Transfer {
	
	private Node position;
	private Node destination;
	private String message;
	private Random rand;
	private int hops = 0;
	private static int count = 0;
	private int id = 0;
	
	/**
	 * Creates new transfer based on the given graph
	 * 
	 */
	
	public Transfer(Graph graph)
	{
		rand = new Random();
		int x = rand.nextInt(graph.size());
		List<Node> nodes = graph.getNodes();
		position = nodes.get(x);
		nodes.remove(x);
		destination = nodes.get(rand.nextInt(nodes.size()));
		message = "Hi";
		id = count++;
		
	}
	
	public int getId(){
		return id;
	}
	
	public void incrementHops()
	{
		hops++;
	}
	
	public Node getPosition()
	{
		return position;
	}
	
	public void setPosition(Node position)
	{
		this.position = position;
	}

	public Node getDestination()
	{
		return destination;
	}
	
	public String getMessage()
	{
		return message;
	}
	
	public void setMesssage(String message)
	{
		this.message = message;
	}
	
	public int getHops()
	{
		return hops;
	}

}
