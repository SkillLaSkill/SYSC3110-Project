import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class Metric {
	private int hops = 0;
	private int transfers = 0;
	private int stepCounter = 0;
	private List<String> stepTransfers;
	private boolean valid = true;
	
	/**
	 * 
	 * @param stepTransfers
	 */
	public void setTransferInfo(List<String> stepTransfers) {
		this.stepTransfers = stepTransfers;
	}
	
	/**
	 * Sets the counter to a value
	 * @param i
	 */
	public void setCounter(int i)
	{
		stepCounter = i;
	}
	
	/**
	 * Adds hops
	 * @param h
	 */
	public void addHops(int h) {
		hops += h;
	}
	public void addCompleteTransfers(int completeTransfers) {
		transfers += completeTransfers;
	}
	
	/**
	 * Adds a hop
	 */
	public void addHop()
	{
		hops++;
	}
	
	/**
	 * Add a transfer
	 */
	public void addTransfers()
	{
		transfers++;
	}
	
	/**
	 * Gets the counter
	 * @return
	 */
	public int getCounter()
	{
		return stepCounter;
	}
	
	/**
	 * Gets hops
	 * @return
	 */
	public int getHops() {
		return hops;
	}
	
	/**
	 * Gets transfers
	 * @return
	 */
	public int getTransfers() {
		return transfers;
	}
	
	/**
	 * Gets steptransfer info
	 * @return
	 */
	public List<String> getStepTransferInfo() {
		return stepTransfers;
	}
	
	/**
	 * Gets the average hops per transfer
	 * @return
	 */
	public double getAverageHopsPerTransfer() {
		if(transfers <= 0)
			return 0;
		return round((double) hops / transfers, 2);
	}
	
	/**
	 * Rounds a double to 2 decimal places
	 * @param value
	 * @param places
	 * @return
	 */
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}

	/**
	 * Returns boolean if metric is valid for History mode
	 * @return
	 */
	public boolean isValid() {
		return valid;
	}

	/**
	 * Sets valid boolean
	 * @param valid
	 */
	public void setValid(boolean valid) {
		this.valid = valid;
	}
}
