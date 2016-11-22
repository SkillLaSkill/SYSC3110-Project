
public class Metric {
	private int totalHops = 0;
	private int totalCompleteTransfers = 0;
	
	public void addHops(int hops) {
		totalHops += hops;
	}
	public void addCompleteTransfers(int completeTransfers) {
		totalCompleteTransfers += completeTransfers;
	}
	
	public double getAverageHopsPerTransfer() {
		return (double) totalHops / totalCompleteTransfers;
	}
	
}
