import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Runs all the tests for the Packet class such as the constructor, incrementHops and ResetID
 * 
 * @author Team GetterDone
 */
public class PacketTest {
	Packet t;
	Node pos;
	Node n;
	
	/**
	 * Sets up the nodes and packet in order to test the Packet class
	 */
	@Before
	public void setUp() {
		pos = new Node("A");
		n = new Node("B");
		t = new Packet("IDC", n);
	}
	
	/**
	 * Tests the constructor of the packet class by making sure it is not null
	 */
	@Test
	public void testPacket() {
		Assert.assertNotNull("Packet should exist", t);
	}

	/**
	 * Tests the incrementHops method in packet by comparing the value of hops before and after
	 * calling the incrementHops method. The after hops should be before hops+1.
	 */
	@Test
	public void testIncrementHops() {
		int i = t.getHops();
		t.incrementHops();
		Assert.assertEquals("Hops should have been incremented", i+1, t.getHops());
	}

	/**
	 * Tests the set message method by setting the message to "Test Case" and making sure the message that is put
	 * into the packet is the same.
	 */
	@Test
	public void testSetMesssage() {
		t.setMesssage("Test Case");
		Assert.assertEquals("Transfer should contrain the message 'Test Case'", "Test Case", t.getMessage()); 
	}

	/**
	 * Tests the resetID by calling the method then making sure that the count is back to zero.
	 */
	@Test
	public void testResetId() {
		t.resetId();
		Assert.assertEquals("Count should be back at zero", 0, t.getCount());
	}

	/**
	 * Tests the setTransfered by calling the method then making sure the value is set to true.
	 */
	@Test
	public void testSetTransferred() {
		t.setTransfered(true);
		Assert.assertEquals("Count should be back at zero", true, t.isTransfered());
	}
}
