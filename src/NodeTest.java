import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class NodeTest {
	Node n, n1;

	@Before
	public void setUp() {
		n = new Node("A");
		n1 = n;
	}
	
	@Test
	public void testNewNode() {
		Assert.assertNotNull("Node should not be null", n);
	}
	@Test
	public void testSetMessage() {
		n.setMessage("Test Case");
		Assert.assertEquals("Nodes message should be 'Test Case'", "Test Case", n.getMessage());
	}

	@Test
	public void testEqualsObject() {
		Assert.assertEquals("Equals should say both methods are equal", true, n.equals(n1));
	}
}
