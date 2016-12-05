import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

/**
 * Runs all the tests for the Graph class such as the constructor, addNodes and Contains
 * 
 * @author Team GetterDone
 */
public class GraphTest extends Graph {
	Node n, n1, n2;
	ArrayList<Node> ln = new ArrayList<Node>();
	Graph g;
	
	/**
	 * Sets up the nodes, a array list of nodes, and graph in order to test the Graph class
	 */
	@Before
	public void setUp() throws Exception {
		n = new Node("A");
		n1 = new Node("B");
		n2 = new Node("C");
		ln.add(n1);
		ln.add(n2);
		g = new Graph();
	}

	/**
	 * Tests to make sure the when the graph is instantiated, it should have zero nodes.
	 */
	@Test
	public void testNoNodes() {
		Assert.assertEquals("Size of Graph Node list should be 0.", 0, g.getNodes().size());
	}

	/**
	 * Tests to make sure that addNode works by adding a node, then making sure the size of the node list in 
	 * graph is 1.
	 */
	@Test
	public void testAddNode() {
		g.addNode(n);
		Assert.assertEquals("Size of Graph Node list should be 1.", 1, g.getNodes().size());
	}
	
	/**
	 * Tests to make sure that removeNOde works by adding a node, removing it, then making sure the size of the 
	 * node list in graph is 0.
	 */
	@Test
	public void testRemoveNode() {
		g.addNode(n);
		g.removeNode(n);
		Assert.assertEquals("Size of Graph Node list should be 0.", 0, g.getNodes().size());
	}
	
	/**
	 * Tests getNode by adding a node to the graph and then comparing it to a new node that is exactly the same.
	 */
	@Test
	public void testGetNode() {
		g.addNode(n);
		Assert.assertEquals("Graph Node list should contain Node 'A'.", true, g.getNode("A").equals(new Node("A")));
	}

	/**
	 * Tests getNodes by adding 2 nodes to the graph and then comparing it to new nodes that is exactly the same.
	 */
	@Test
	public void testGetNodes() {
		g.addNode(n);
		g.addNode(n1);
		Assert.assertEquals("Graph Node list should contain Nodes", true, g.getNodes().contains(new Node("A")) 	&& g.getNodes().contains(new Node("B")));
	}

	/**
	 * Test contains by adding 2 nodes and checking if the graph contains that node, which it should.
	 */
	@Test
	public void testContains() {
		g.addNode(n);
		g.addNode(n1);
		Assert.assertEquals("Graph could contain node 'B'", true, g.getNodes().contains(n1));
	}

	/**
	 * Tests the size method by adding a singular node, and the checking to make sure .size gives 1.
	 */
	@Test
	public void testSize() {
		g.addNode(n);
		Assert.assertEquals("Size of Graph Node list should be 1.", 1, g.getNodes().size());
	}

	/**
	 * Tests the clear method by adding a node and then immediately clearing the graph. After this the size is
	 * checked to make sure that the node lsit size is back to zero.
	 */
	@Test
	public void testClear() {
		g.addNode(n);
		g.clear();
		Assert.assertEquals("Size of Graph Node list should be 0.", 0, g.getNodes().size());
	}
	
	/**
	 * Tests to make sure that isConnected works by adding 2 nodes that are connected, then making sure the it
	 * returns true given the nodes
	 */
	@Test
	public void testIsConnectedNode() {
		n.addConnection(n1);
		g.addNode(n);
		g.addNode(n1);
		Assert.assertEquals("Node A should be connected to node B.", true, g.isConnected(n,n1));
	}
	
	/**
	 * Tests to make sure that isConnected works by adding 2 nodes that are connected, then making sure the it
	 * returns true given the names
	 */
	@Test
	public void testIsConnectedName() {
		n.addConnection(n1);
		g.addNode(n);
		g.addNode(n1);
		Assert.assertEquals("Node A should be connected to node B.", true, g.isConnected("A","B"));
	}
	
	/**
	 * Tests to make sure the packetExists method works
	 */
	public void testPacketExists() {
		Packet p = new Packet("Ello", n);
		n1.addPacket(p);
		g.addNode(n);
		g.addNode(n1);
		Assert.assertEquals("Node B should contain the packet.", true, g.packetsExist());
	}
}
