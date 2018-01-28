/**************************************************
 * Dean and Dean
 * 
 * This is a generic description of an employee
 **************************************************/
public class Employee 
{
	private String name;
	
	//************************************************
	
	public Employee(String name)
	{
		this.name = name;
	}
	
	//************************************************
	
	public void printPay(int date)
	{
		System.out.printf("%2d %10s: %8.2f\n", date, name, getPay());
	}
	
	//************************************************
	
		// this dummy method satisfies the compiler
	public double getPay()
	{
		System.out.println("Error! in dummy");
		return 0.0;
	}
}
