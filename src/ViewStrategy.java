public interface ViewStrategy {
	//To GUI
	public void addNode(String name);
	public void removeNode(String name);
	public void addConnection(String A, String B);
	public void removeConnection(String A, String B);
	
	//From GUI
	//public String getNewNodeName();
	//public String getNewConnectionNodeName();
	//public String getConnectionList();
	//public String getNodeNameToDelete();
	//public String getConnectionToDelete();
	//public int getSendRate();
	//public int getSimSteps();
	public void setOutput(String text);
	
	public String getSelectedAlgorithm();
	public void addMessage(String message, String node);
	public void removeMessage(String message, String node);
	public void updateMessage(String message, String currentNode, String newNode);
	public void update(int steps, int sendRate);
	public void reset();
	
	/**
	 * Needs to be called at the end of a simulation step.
	 */
	public void simStepComplete();
}
