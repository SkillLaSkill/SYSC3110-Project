import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Runs all the tests for the Metric class such as the constructor, addHops, addCompletedTransfers,
 * getAverageHopsPerTransfer, addHops, addTransfers and setCounter
 * 
 * @author Team GetterDone 
 */
public class MetricTest {
	Metric m;
	
	/**
	 * Creates a instance of metric, that adds a hops and 4 transfers
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		m = new Metric();
		m.addHop();
		m.addCompleteTransfers(4);
	}

	/**
	 * Tests addHop method. Should have 1 hops (from set up)
	 */
	@Test
	public void testAddHop() {
		Assert.assertEquals("Hops should only be at 1", 1, m.getHops());
	}
	
	/**
	 * Tests the addCompleteTransfers method. Should have 4 transfers (from set up)
	 */
	@Test
	public void testAddCompleteTransfers() {
		Assert.assertEquals("Hops should be at 4", 4, m.getTransfers());
	}

	/**
	 * Tests the getAverageHopsPerTranfer method. Should return a value of 0.25
	 */
	@Test
	public void testGetAverageHopsPerTransfer() {
		Assert.assertEquals("Hops should be at 0.25", 0.25, m.getAverageHopsPerTransfer(), 0.25);
	}
	
	/**
	 * Tests the addHops method. Should return 6 after adding the 5 (1 from set up)
	 */
	@Test
	public void testAddHops() {
		m.addHops(5);
		Assert.assertEquals("Hops should be at 6", 6, m.getHops());
	}

	/**
	 * Tests the addTransfers method. Should return 5 after adding the 1 (4 from set up)
	 */
	@Test
	public void testAddTransfers() {
		m.addTransfers();
		Assert.assertEquals("Hops should be at 5", 5, m.getTransfers());
	}

	/**
	 * Tests the setCounter method. Should return the 5 it was set to
	 */
	@Test
	public void testSetCounter() {
		m.setCounter(5);
		Assert.assertEquals("Counter should be at 5", 5, m.getCounter());
	}
}
