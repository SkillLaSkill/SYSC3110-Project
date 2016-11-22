
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Runs all the tests for the Packet class such as the constructor, incrementHops and ResetID
 * 
 * @author Team GetterDone
 */
public class SimulationTest {
	Simulation s;
	Node pos;
	Node n;
	
	/**
	 * Sets up the nodes, graph, and simulator in order to test the Simulation class
	 */
	@Before
	public void setUp() {
		pos = new Node("A");
		n = new Node("B");
		
		Graph g = new Graph();
		g.addNode(pos);
		g.addNode(n);
		s = new Simulation(g);
	}
	
	/**
	 * Tests the constructor by making sure its not null
	 */
	@Test
	public void testSimulation() {
		Assert.assertNotNull("Simulation should exist", s);
	}
	

	/**
	 * Tests the reset method by resetting the simulation and making sure the numbers of step
	 */
	@Test
	public void testReset() {
		s.reset();
		Assert.assertEquals("Simulator should be back at zero steps", 0, s.getSteps());
	}
}

