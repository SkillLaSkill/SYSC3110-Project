import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TransferTest {
	Packet t;
	Node pos;
	Node n;
	
	@Before
	public void setUp() {
		pos = new Node("A");
		n = new Node("B");
		
		Graph g = new Graph();
		g.addNode(pos);
		g.addNode(n);
		t = new Packet(g);
	}
	
	@Test
	public void testTransfer() {
		Assert.assertNotNull("Transfer should exist", t);
	}

	@Test
	public void testIncrementHops() {
		int i = t.getHops();
		t.incrementHops();
		Assert.assertEquals("Hops should have been incremented", i+1, t.getHops());
	}

	@Test
	public void testSetPosition() {
		t.setPosition(pos);
		Assert.assertEquals("Tranfers position should now be at node 'pos'", t.getPosition(), pos);
	}

	@Test
	public void testSetMesssage() {
		t.setMesssage("Test Case");
		Assert.assertEquals("Transfer should contrain the message 'Test Case'", "Test Case", t.getMessage()); 
	}

	@Test
	public void testResetId() {
		t.resetId();
		Assert.assertEquals("Count should be back at zero", 0, t.getId());
	}

}
