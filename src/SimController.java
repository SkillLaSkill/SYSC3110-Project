import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class SimController implements ActionListener {
	
	private ViewStrategy view;
	private Simulation model;
	

	public SimController(ViewStrategy v, Simulation m)
	{
		view = v;
		model = m;
	}
	
	private void createNode(String name)
	{
		if (name.isEmpty()) return;
		Node node = new Node(name);
		
		// Don't add duplicate
		if (!model.getGraph().contains(node)) {
			model.getGraph().addNode(node);
			view.addNode(node.getName());
		}
	}
	
	private void removeNode(String name)
	{
		view.removeNode(name);
		model.getGraph().removeNode(name);
	}
	/*
	private void setupTestGraph()
	{
		if(model != null && model.getGraph() != null) model.getGraph().buildGraph();
		else System.out.println("Could not setup graph!");
	}*/
	
	private void makeConnection(Node A, Node B)
	{
		view.addConnection(A.getName(), B.getName());
		model.getGraph().addConnection(A, B);
	}
	private void makeConnections(Node node, List<Node> conList) {
		for (Node toCon : conList) {
			makeConnection(node, toCon);
		}		
	}
	
	private void setupSim()
	{
		model = new Simulation(new Graph());
	}
	
	private void DisplayNandC()
	{
		
	}
	
	private void startSim()
	{
		//get values for steps and sendrate from user
		if(model != null && model.getGraph() != null) model.simulate(30,3);
	}
	
	private void stepSim()
	{
		//get values for steps and sendrate from user
		if(model != null && model.getGraph() != null) model.simulate(1,3);
	}
	
	/*Stop method if we decide to use it
	//private void stopSim()
	//{
	//	model.setSimulating(false);
	//}
	*/
	
	private void reset()
	{
		model = null;
		//set some textfields to ""
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		String actionCommand = arg0.getActionCommand();
		
		if(actionCommand.equals("Create Node")) {
			createNode(view.getNewNodeName());
		}
		else if (actionCommand.equals("Establish Connections")) {
			String nodeStr = view.getNewConnectionNodeName();
			Node n = model.getGraph().getNode(nodeStr);
			String conString = view.getConnectionList();
			
			// Need to verify connection string is valid format, and nodes are valid
			List<Node> conList = new ArrayList<>();
			for (String s : conString.split(" ")) {
				if (model.getGraph().getNode(s) != null) {
					conList.add(model.getGraph().getNode(s));
				}
				else return; // Invalid connection list
			}

			makeConnections(n, conList);
		}
		
		
	}

}
