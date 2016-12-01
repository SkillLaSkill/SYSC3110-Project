
public class Metric {
	private int hops = 0;
	private int transfers = 0;
	private int stepCounter = 0;
	private int sendRate = 0;
	
	public void addHops(int hops) {
		hops += hops;
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
	
}
