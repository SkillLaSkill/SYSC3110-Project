import java.util.List;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.*;
import javax.swing.*;

public class Master extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	public List<Node> allNodes;
	public static final int WIDTH = 400;
	public static final int HEIGHT = 250;
	private JTextField nameTextField, conNameTextField, conNodeTextField;
	
	public Master() {
		allNodes = null;
		setSize(WIDTH,HEIGHT);
		setTitle("Address Book");
		Container contentPane = getContentPane();
		contentPane.setBackground(Color.RED);
		contentPane.setLayout(new GridLayout(8,2));
		
		 
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem newItem = new JMenuItem("Run Test Method", 'R');
        newItem.addActionListener(this);
        fileMenu.add(newItem);
        
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);
		
		JLabel nameLabel = new JLabel("Name: ");
		contentPane.add(nameLabel);
	    nameTextField = new JTextField(25);
		contentPane.add(nameTextField);
		
		JButton nameButton = new JButton("Create Node");
		nameButton.addActionListener(this);
		contentPane.add(nameButton);
		
		
		JLabel conLabel = new JLabel("");
		contentPane.add(conLabel);
		
		JLabel con1Label = new JLabel("		Given node name to: ");
		contentPane.add(con1Label);
		conNodeTextField = new JTextField(25);
		contentPane.add(conNodeTextField);
		
		JLabel con2Label = new JLabel("		Connected to: ");
		contentPane.add(con2Label);
	    conNameTextField = new JTextField(25);
		contentPane.add(conNameTextField);
		
		JButton conButton = new JButton("Create connections for node");
		conButton.addActionListener(this);
		contentPane.add(conButton);

	}
	
	public void newNode(String nodeName) {
		allNodes.add(new Node(nodeName));
	}
	
	public Node getNode(String name){
		for (int i = 0; i < allNodes.size(); i++){
			if (allNodes.get(i).getName() == name) {
				return allNodes.get(i);
			}
		}
		return null;
	}
	
	public void nodeConnections(String node, String connections) {
		Node n = getNode(node);
		String[] nodeConnections = connections.split(" ");
		
		for (int i = 0; i < nodeConnections.length; i++){
			for (Node t : allNodes){
				if (t.getName() == nodeConnections[i]) t.addConnection(n);
			}
		}
	}
	
	public void runTest(){
		newNode("A");
		/*newNode("B");
		newNode("C");
		newNode("D");
		newNode("E");
		
		nodeConnections("A", "B C");
		nodeConnections("B", "B D, E");
		nodeConnections("C", "A D");
		nodeConnections("D", "B C");
		nodeConnections("E", "A B");*/
	}
	
	public static void main(String[] args) {
		Master m = new Master();
		m.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		
		if(actionCommand.equals("Create Node")) {
			newNode(nameTextField.getText());
		}
		else if(actionCommand.equals("Create connections for node")) {
			nodeConnections(conNodeTextField.getText(), conNameTextField.getText());
		}
		else if(actionCommand.equals("Run Test Method")) {
			runTest();
		}
	}
}
