import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit.framework.Assert;

public class GraphTest extends Graph {
	Node n;
	Node n1;
	Simulation m;
	@Before
	public void setUp() throws Exception {
		n = new Node("A");
		n1 = new Node("B");
		m = new Simulation (new Graph());
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testAddNode() {
		Assert.assertEquals("Size of Master Node list should be 0.", 0, m.allNodes.size());
	}

	@Test
	public void testGetNode() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetNodes() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetConnections() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddConnection() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddNodeConnections() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddNodeConnectionsByName() {
		fail("Not yet implemented");
	}

	@Test
	public void testDisplayNodes() {
		fail("Not yet implemented");
	}

	@Test
	public void testContains() {
		fail("Not yet implemented");
	}

	@Test
	public void testSize() {
		fail("Not yet implemented");
	}

	@Test
	public void testClear() {
		fail("Not yet implemented");
	}

}
