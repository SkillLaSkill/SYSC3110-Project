
import java.util.Random;

import java.util.List;
import java.util.ArrayList;
/**
 * This class creates and manipulates Transfers, which store the current node position, the destination and the message
 * 
 * @author Team GetterDone
 */
public class Packet {
	
	private Node previousPostion;
	private Node destination;
	private String message;
	private int hops = 0;
	private static int count = 0;
	private final int id;
	private boolean transfered = false;
	
	/**
	 * Creates new transfer based on the given graph.
	 * 
	 * @param graph (Graph) - Graph that packet will use
	 */
	public Packet(String message, Node destination)
	{
		this.message = message;
		this.destination = destination;

		id = count++;

	}
	
	/**
	 * Creates a copy of another Packet
	 * 
	 * @param t (Packet) - Packet you want copied
	 */
	public Packet(Packet t) {
		this.destination = t.destination;
		this.message = t.message;
		this.hops = t.hops;
		this.id = t.id;
		this.transfered = t.transfered;
	}

	/**
	 * Gets the Id of the packet.
	 * 
	 * @return id (Integer) - Packet ID
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
	 * Return the destination node of the transfer.
	 * 
	 * @return destination (Node) - Packet destination
	 */
	public Node getDestination()
	{
		return destination;
	}
	
	/**
	 * Return the message the transfer is carrying.
	 * 
	 * @return String - Message that you want sent
	 */
	public String getMessage()
	{
		return message;
	}
	
	/**
	 * Sets the message of the transfer.
	 * 
	 * @param message (String) - Message you want sent
	 */
	public void setMesssage(String message)
	{
		this.message = message;
	}
	
	/**
	 * Returns the number of hops(steps) the transfer has
	 * completed up to this point.
	 *
	 * @return hops (Integer) - Number of hops taken
	 */
	public int getHops()
	{
		return hops;
	}
	
	/**
	 * Resets the Id Counter to 0
	 */
	public void resetId()
	{
		count = 0;
	}
	
	/**
	 * Checks if the given packet is the exact same as this packet
	 * 
	 * @param o (Object) - Object you want compared to this packet
	 * @return boolean - True = same packet; False = different packet
	 */
	@Override
	public boolean equals(Object o) {
		Packet f = (Packet) o;
		return this.id == f.id;
	}
	
	/**
	 * Provides the hashcode for the object
	 * 
	 * @return int - The Hashcode
	 */
	@Override 
	public int hashCode() {
		return id;
	}

	public boolean isTransfered() {
		return transfered;
	}

	public void setTransfered(boolean transfered) {
		this.transfered = transfered;
	}
}
