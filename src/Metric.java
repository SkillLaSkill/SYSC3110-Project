
public class Metric {
	private int totalHops = 0;
	private int totalCompleteTransfers = 0;
	
	public void addHops(int hops) {
		totalHops += hops;
	}
	public void addCompleteTransfers(int completeTransfers) {
		totalCompleteTransfers += completeTransfers;
	}
	

	@Override 
	public String toString() {
		int x;
		if (totalCompleteTransfers == 0) x = 1;
		else x = totalCompleteTransfers;
		return "Total hops: " + totalHops + ".\nAverage hops/Transfer: " + (float)totalHops/x + ".";
		
	}
}
