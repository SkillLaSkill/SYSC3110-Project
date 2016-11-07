import java.awt.event.ActionListener;

public interface ViewStrategy {
	public void addNode(String n);
	public void removeNode(String name);
	public void addConnection(String A, String B);
	public void removeConnection(String A, String B);
	public String getNewNodeName();
	public String getNewConnectionNodeName();
	public String getConnectionList();
	public void addMessage(String message, String node);
	public void removeMessage(String message, String node);
	public void updateMessage(String message, String currentNode, String newNode);
	
	public void setActionListener(ActionListener listener);

	
}
