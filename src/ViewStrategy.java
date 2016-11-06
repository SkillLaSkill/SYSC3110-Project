
public interface ViewStrategy {
	public void addNode(String n);
	public void removeNode(String name);
	public void addConnection(String A, String B);
	public void removeConnection(String A, String B);
	public String getNewNodeName();
	public String getNewConnectionNodeName();
	public String getConnectionList();
	
}
