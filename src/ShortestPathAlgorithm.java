import java.util.LinkedList;
import java.util.Queue;

public class ShortestPathAlgorithm extends RoutingAlgorithm {

	@Override
	public void simulateStep() {
		
		if (!isSimulating()) {
			return;
		}
		
		for (int i = 0; i < getGraph().getNodes().size(); i++){
			Node n = getGraph().getNodes().get(i);
			for (int j = 0; j < n.getPackets().size(); j++) {
				Packet p = n.getPackets().get(j);
				if (!p.isTransfered()) {
					p.setTransfered(true);
					// Packet has reached it's destination, so remove it.
					if (n.equals(p.getDestination())) {
						n.removePacket(p);
					}
					
					n.removePacket(p);
					
					Node next = findNextNodeBFS(n, p.getDestination());
					
					if (next != null) {
						next.addPacket(p);
					}
					else {
						// Found its destination
					}
				}
			}
		}
		
		resetTransfered();
	}
	
	private Node findNextNodeBFS(Node currentPosition, Node destination) {
		Queue<BFSNode> q = new LinkedList<>();
		
		// Enqueue the root.
		BFSNode root = new BFSNode(currentPosition);
		root.setDistance(0);
		q.add(root);
		
		while (!q.isEmpty()) {
			BFSNode current = q.poll();
			//System.out.println("Current: " + current.getNode().getName() + " Dist: " + current.getDistance());
			
			// If the destination node is found, follow the parents up until before the root and return.
			if (current.getNode().equals(destination)) {
				// Destination was current Position
				if (current.getParent() == null) break;
				while (current.getParent().getParent() != null) {
					current = current.getParent();
				}
				return current.getNode();
			}
			
			for (Node n : current.getNode().getConnections()) {
				BFSNode bn = new BFSNode(n);
				if (bn.getDistance() == -1) {
					bn.setDistance(current.getDistance() + 1);
					bn.setParent(current);
					
					q.add(bn);
				}	
			}
		}
		return null;
	}

	public static void main(String[] args) {
		ShortestPathAlgorithm al = new ShortestPathAlgorithm();
		Graph g = new Graph();
		Node A = new Node("A");
		Node B = new Node("B");
		Node C = new Node("C");
		Node D = new Node("D");
		Node E = new Node("E");
		
		A.addConnection(B);
		A.addConnection(C);
		A.addConnection(E);
		B.addConnection(D);
		B.addConnection(E);
		C.addConnection(D);
		
		g.addNode(A);
		g.addNode(B);
		g.addNode(C);
		g.addNode(D);
		g.addNode(E);
		
		Packet p = new Packet("Hello", D);
		E.addPacket(p);
		
		
		al.setGraph(g);
		al.setSimulating(true);
		
		al.simulate(3);
	}
	
	private class BFSNode{
		private Node node;
		private int distance = -1;
		private BFSNode parent = null;
		public BFSNode(Node n) {
			node = n;
		}
		public int getDistance() {
			return distance;
		}
		public void setDistance(int distance) {
			this.distance = distance;
		}
		public BFSNode getParent() {
			return parent;
		}
		public void setParent(BFSNode parent) {
			this.parent = parent;
		}
		private Node getNode() {
			return node;
		}
		
	}
}
