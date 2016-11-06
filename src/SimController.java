import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SimController implements ActionListener {
	
	private ViewStrategy view;
	private Simulation model;
	

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
	
	private void setupSim()
	{
		model.getGraph().buildGraph();
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {

		
	}

}
