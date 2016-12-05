import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MetricTest {
	Metric m;
	
	@Before
	public void setUp() throws Exception {
		m = new Metric();
		m.addHop();
		m.addCompleteTransfers(4);
	}

	@Test
	public void testAddHop() {
		Assert.assertEquals("Hops should only be at 1", 1, m.getHops());
	}
	
	@Test
	public void testAddCompleteTransfers() {
		Assert.assertEquals("Hops should be at 4", 4, m.getTransfers());
	}

	@Test
	public void testGetAverageHopsPerTransfer() {
		Assert.assertEquals("Hops should be at 0.25", 0.25, m.getAverageHopsPerTransfer(), 0.25);
	}
	
	@Test
	public void testAddHops() {
		m.addHops(5);
		Assert.assertEquals("Hops should be at 6", 6, m.getHops());
	}

	@Test
	public void testAddTransfers() {
		m.addTransfers();
		Assert.assertEquals("Hops should be at 5", 5, m.getTransfers());
	}

	@Test
	public void testSetCounter() {
		m.setCounter(5);
		Assert.assertEquals("Counter should be at 5", 5, m.getTransfers());
	}

	@Test
	public void testSetSendRate() {
		m.setSendRate(5);
		Assert.assertEquals("Send rate should be at 5", 5, m.getSendRate());
	}

}
