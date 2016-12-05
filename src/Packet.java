/**
 * This class creates and manipulates Packets, which store the current node position, the destination and the message
 * 
 * @author Team GetterDone
 */
public class Packet {
	private int id;
	//private Node previousPostion;
	private Node destination;
	private String message;
	private int timeToLive = 8; 
	private int hops = 0;
	private static int count = 0;
	private boolean transferred = false;
	
	/**
	 * Creates new Packet based on the given graph.
	 * 
	 * @param graph (Graph) - Graph that packet will use
	 */
	public Packet(String message, Node destination)
	{
		this.destination = destination;

		id = count++;
		this.message = message + " ID:" + id + " Dst:" + destination.getName();

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
		this.transferred = t.transferred;
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
	 * Return the destination node of the Packet.
	 * 
	 * @return destination (Node) - Packet destination
	 */
	public Node getDestination()
	{
		return destination;
	}
	
	/**
	 * Return the message the Packet is carrying.
	 * 
	 * @return String - Message that you want sent
	 */
	public String getMessage()
	{
		return message;
	}
	
	/**
	 * Sets the message of the Packet.
	 * 
	 * @param message (String) - Message you want sent
	 */
	public void setMesssage(String message)
	{
		this.message = message;
	}
	
	/**
	 * Returns the number of hops(steps) the Packet has
	 * completed up to this point.
	 *
	 * @return hops (int) - Number of hops taken
	 */
	public int getHops()
	{
		return hops;
	}
	
	/**
	 * Return the count the Packet is at.
	 * 
	 * @return int - Count that you are at
	 */
	public int getCount()
	{
		return count;
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
	
	/**
	 * Checks to see if that packet has already been transfered during a simulation step
	 * 
	 * @return transfered (Boolean) - Status of whether packet has been transfered True = has been transfered; False = has not been transfered
	 */
	public boolean isTransfered() {
		return transferred;
	}
	
	/**
	 * Sets the status of packet
	 * 
	 * @param Transfered (Boolean) - Sets status of whether packet has been transfered
	 */
	public void setTransfered(boolean Transfered) {
		this.transferred = Transfered;
	}
	
	public static Packet importFromXMLObj(org.w3c.dom.NodeList nl) {
		int count = Packet.count;
		Packet p = new Packet("", new Node(""));
		// Get all the fields for Packet
		for (int i = 0; i < nl.getLength(); i++) {
			org.w3c.dom.Node xmlNode = (org.w3c.dom.Node) nl.item(i);

			String name = xmlNode.getNodeName();
			String value = xmlNode.getTextContent();
			
			switch (name) {
			case "ID":
				// Not sure about this.
				p.id = Integer.parseInt(value);
				break;
			case "Destination":
				p.destination = new Node(value);
				break;
			case "Message":
				p.message = value;
				break;
			case "Hops":
				p.hops = Integer.parseInt(value);
				break;
			case "Count":
				Packet.count = Integer.parseInt(value);
			case "Transferred":
				p.transferred = Boolean.parseBoolean(xmlNode.getTextContent());
				break;
			default: break;
			}

		}
		Packet.count = count;
		return p;
	}
}
