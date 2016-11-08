
import java.util.Random;
import java.util.List;
/**
 * This class creates and manipulates Transfers, which store the current node position, the destination and the message
 * 
 * @author Team GetterDone
 */
public class Transfer {
	
	private Node position;
	private Node destination;
	private String message;
	private Random rand = new Random();
	private int hops = 0;
	private static int count = 0;
	private final int id;
	
	/**
	 * Creates new transfer based on the given graph.
	 * @param graph (Graph)
	 */
	public Transfer(Graph graph)
	{
		int x = rand.nextInt(graph.size());
		List<Node> nodes = graph.getNodes();
		position = nodes.get(x);
		nodes.remove(x);
		destination = nodes.get(rand.nextInt(nodes.size()));
		id = count++;
		message = "Hi " + id;
	}
	
	public Transfer(Transfer t) {
		this.position = t.position;
		this.destination = t.destination;
		this.message = t.message;
		this.hops = t.hops;
		this.id = t.id;
	}

	/**
	 * Get Id of a transfer.
	 * 
	 * @return id (Integer)
	 */
	public int getId(){
		return id;
	}
	
	/**
	 * Increment the hops counter.
	 */
	public void incrementHops()
	{
		hops++;
	}
	
	/**
	 * Return the node where the transfer currently is.
	 * 
	 * @return position (Node)
	 */
	public Node getPosition()
	{
		return position;
	}
	
	/**
	 * Set the current position of the node
	 */
	public void setPosition(Node position)
	{
		this.position = position;
	}

	/**
	 * Return the destination node of the transfer.
	 * 
	 * @return destination (Node)
	 */
	public Node getDestination()
	{
		return destination;
	}
	
	/**
	 * Return the message the transfer is carrying.
	 * 
	 * @return message (String)
	 */
	public String getMessage()
	{
		return message;
	}
	
	/**
	 * Sets the message of the transfer.
	 * 
	 */
	public void setMesssage(String message)
	{
		this.message = message;
	}
	
	/**
	 * Returns the number of hops(steps) the transfer has
	 * completed up to this point.
	 *
	 * @return hops (Integer)
	 */
	public int getHops()
	{
		return hops;
	}
	
	/**
	 * Resets the Id Counter to 0
	 * 
	 */
	public void resetId()
	{
		count = 0;
	}
	@Override
	public boolean equals(Object o) {
		Transfer f = (Transfer) o;
		return this.id == f.id;
	}
	@Override 
	public int hashCode() {
		return id;
	}
}
