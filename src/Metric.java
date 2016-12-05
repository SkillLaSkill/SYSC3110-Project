import java.util.List;

public class Metric {
	private int hops = 0;
	private int transfers = 0;
	private int stepCounter = 0;
	private int sendRate = 0;
	private List<String> stepTransfers;
	
	public void setTransferInfo(List<String> stepTransfers) {
		this.stepTransfers = stepTransfers;
	}
	
	public List<String> getStepTransferInfo() {
		return stepTransfers;
	}
	
	public void addHops(int h) {
		hops += h;
	}
	public void addCompleteTransfers(int completeTransfers) {
		transfers += completeTransfers;
	}
	
	public double getAverageHopsPerTransfer() {
		return (double) hops / transfers;
	}
	
	public void addHop()
	{
		hops++;
	}
	
	public void addTransfers()
	{
		transfers++;
	}
	
	public void setCounter(int i)
	{
		stepCounter = i;
	}
	
	public void setSendRate(int i)
	{
		sendRate = i;
	}
	
	public int getSendRate()
	{
		return sendRate;
	}
	
	public int getCounter()
	{
		return stepCounter;
	}
	
	public int getHops() {
		return hops;
	}
	
	public int getTransfers() {
		return transfers;
	}
}
