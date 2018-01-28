/***************************************************
 * Dean & Dean
 * 
 * this class implements an hourly employee.
 ***************************************************/
public class Hourly extends Employee
{
	private double hourlyRate;
	private double hours = 0.0;
	
	//***************************************************
	
	public Hourly(String name, double rate)
	{
		super(name);
		hourlyRate = rate;
	}
	
	//***************************************************
	
		// Postcontition: This resets hours to zero.
	@Override
	public double getPay()
	{
		double pay = hourlyRate * hours;
		hours = 0.0;
		return pay;
	} // end getPay
	
	//***************************************************
	
	public void addHours(double hours)
	{
		this.hours+= hours;
	} // end addHours()
} // end class Hourly
