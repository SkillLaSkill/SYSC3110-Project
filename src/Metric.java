import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class Metric {
	private int hops = 0;
	private int transfers = 0;
	private int stepCounter = 0;
	private List<String> stepTransfers;
	
	public void setTransferInfo(List<String> stepTransfers) {
		this.stepTransfers = stepTransfers;
	}
	
	public void setCounter(int i)
	{
		stepCounter = i;
	}
		
	public void addHops(int h) {
		hops += h;
	}
	public void addCompleteTransfers(int completeTransfers) {
		transfers += completeTransfers;
	}
	
	public void addHop()
	{
		hops++;
	}
	
	public void addTransfers()
	{
		transfers++;
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
	
	public List<String> getStepTransferInfo() {
		return stepTransfers;
	}
	
	public double getAverageHopsPerTransfer() {
		if(transfers <= 0)
			return 0;
		return round((double) hops / transfers, 2);
	}
	
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
}
