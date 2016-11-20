import java.util.ArrayList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

public class GraphTest extends Graph {
	Node n, n1, n2;
	ArrayList<Node> ln = new ArrayList<Node>();
	Graph g;
	
	@Before
	public void setUp() throws Exception {
		n = new Node("A");
		n1 = new Node("B");
		n2 = new Node("C");
		ln.add(n1);
		ln.add(n2);
		g = new Graph();
	}

	@After
	public void tearDown() throws Exception {}

	@Test
	public void testNoNodes() {
		Assert.assertEquals("Size of Graph Node list should be 0.", 0, g.getNodes().size());
	}

	@Test
	public void testAddNode() {
		g.addNode(n);
		Assert.assertEquals("Size of Graph Node list should be 1.", 1, g.getNodes().size());
	}
	
	@Test
	public void testGetNode() {
		g.addNode(n);
		Assert.assertEquals("Graph Node list should contain Node 'A'.", true, g.getNode("A").equals(new Node("A")));
	}

	@Test
	public void testGetNodes() {
		g.addNode(n);
		g.addNode(n1);
		Assert.assertEquals("Graph Node list should contain Nodes", true, g.getNodes().contains(new Node("A")) 	&& g.getNodes().contains(new Node("A")));
	}

	@Test
	public void testAddConnection() {
		g.addNode(n);
		g.addNode(n1);
		g.addConnection(n, n1);
		Assert.assertEquals("Node 'A' should be connected to Node 'B'", true, g.getConnections(n).contains(n1));
	}

	@Test
	public void testAddNodeConnections() {
		g.addNode(n);
		g.addNode(n1);
		g.addNode(n2);
		g.addNodeConnections(n, ln);
		Assert.assertEquals("Node 'A' should be connected to Node 'B' and Node 'C'", true, g.isConnected(n,n1) && g.isConnected(n,n2));
	}

	/*@Test
	public void testAddNodeConnectionsByName() {
		g.addNode(n1);
		g.addNode(n2);
		g.addNodeConnectionsByName(n, "B C");
		Assert.assertEquals("Node 'A' should be connected to Node 'B'", true, g.isConnected(n,n1) && g.isConnected(n,n2));
	}*/
	
	@Test
	public void testRemoveConnection() {
		g.addNode(n);
		g.addNode(n1);
		g.addNode(n2);
		g.addNodeConnections(n, ln);
		g.removeConnection(n, n1);
		Assert.assertEquals("Node 'A' should be connected to Node 'B'", false, g.isConnected(n, n1));
	}


	@Test
	public void testContains() {
		g.addNode(n);
		g.addNode(n1);
		g.addConnection(n, n1);
		Assert.assertEquals("Node 'A' should be connected to Node 'B'", true, g.getConnections(n).contains(n1));
	}

	@Test
	public void testSize() {
		g.addNode(n);
		Assert.assertEquals("Size of Graph Node list should be 1.", 1, g.getNodes().size());
	}

	@Test
	public void testClear() {
		g.addNode(n);
		g.clear();
		Assert.assertEquals("Size of Graph Node list should be 0.", 0, g.getNodes().size());
	}
}
