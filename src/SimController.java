import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
		Node node = new Node(name);
		view.addNode(name);
		model.getGraph().addNode(node);
	}
	
	private void removeNode(String name)
	{
		view.removeNode(name);
		model.getGraph().removeNode(name);
	}
	
	private void setupTestGraph()
	{
		if(model != null && model.getGraph() != null) model.getGraph().buildGraph();
		else System.out.println("Could not setup graph!");
	}
	
	private void makeConnection(Node A, Node B)
	{
		view.addConnection(A, B);
		model.getGraph().addConnection(A, B);
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
		String actionCommand = e.getActionCommand();
		
		if()
		
		
	}

}
