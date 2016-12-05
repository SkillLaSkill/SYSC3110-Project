import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Runs all the tests for the Node class such as the constructor, newNode and equals
 * 
 * @author Team GetterDone
 */
public class NodeTest { 
	Node n, n1;
	Packet p, p1;
	ArrayList<Packet> pl = new ArrayList<Packet>();
	
	/**
	 * Creates 2 identical nodes that will be used to test the node class
	 */
	@Before
	public void setUp() {
		n = new Node("A");
		n1 = n;
		
		p = new Packet("Ello", n1);
		p1 = new Packet("Mate", n1);
		
		pl.add(p);
		pl.add(p1);
		
		n.addPacket(p);
		n.addSeenPacket(p);
		
		n.addConnection(n1);
	}
	
	/**
	 * Tests the constructor by making sure that the node object is not null
	 */
	@Test
	public void testNewNode() {
		Assert.assertNotNull("Node should not be null", n);
	}

	/**
	 * Tests the addPacket method 
	 */
	@Test
	public void testAddPacket() {
		Assert.assertEquals("Node A should contain packet", true, n.countainsPacket(p));
	}
	
	/**
	 * Tests the removePacket method 
	 */
	@Test
	public void testRemovePacket() {
		n.removePacket(p);
		Assert.assertEquals("Node A should not contain packet", false, n.countainsPacket(p));
	}
	
	/**
	 * Tests the setPackets method 
	 */
	@Test
	public void testSetPackets() {
		n.setPackets(pl);
		Assert.assertEquals("Node A should contain both packets", true, n.countainsPacket(p) && n.countainsPacket(p1));
	}
	
	/**
	 * Tests the addSeenPacket method 
	 */
	@Test
	public void testAddSeenPacket() {
		Assert.assertEquals("Node A should contain packet", true, n.hasSeenPacket(p));
	}
	
	/**
	 * Tests the add connection method
	 */
	@Test
	public void testAddConnection(){
		Assert.assertEquals("Node A should be connected to Node B", true, n.isConnected(n1));
	}
	
	/**
	 * Tests the equals method by comparing the two objects with equals, and making sure the 
	 * return is true.
	 */
	@Test
	public void testEqualsObject() {
		Assert.assertEquals("Equals should say both methods are equal", true, n.equals(n1));
	}
}
