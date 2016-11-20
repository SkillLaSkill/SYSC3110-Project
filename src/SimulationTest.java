
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SimulationTest {

	Simulation s;
	Node pos;
	Node n;
	
	@Before
	public void setUp() {
		pos = new Node("A");
		n = new Node("B");
		
		Graph g = new Graph();
		g.addNode(pos);
		g.addNode(n);
		s = new Simulation(g);
	}
	
	@Test
	public void testSimulation() {
		Assert.assertNotNull("Transfer should exist", s);
	}
	
	@Test
	public void testSetSimulating() {
		s.setSimulating(true);
		Assert.assertEquals("Simulator should be simulating", true, s.isSimulating());
	}

	@Test
	public void testReset() {
		s.reset();
		Assert.assertEquals("Simulator should be back at zero steps", 0, s.getSteps());
	}
}

