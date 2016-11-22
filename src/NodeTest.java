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

	/**
	 * Creates 2 identical nodes that will be used to test the node class
	 */
	@Before
	public void setUp() {
		n = new Node("A");
		n1 = n;
	}
	
	/**
	 * Tests the constructor by making sure that the node object is not null
	 */
	@Test
	public void testNewNode() {
		Assert.assertNotNull("Node should not be null", n);
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
