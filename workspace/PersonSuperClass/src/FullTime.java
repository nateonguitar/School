
public class FullTime extends Employee
{
	private double salary = 0.0;
	
	//**************************************
	
	public FullTime()
	{ }
	
	public FullTime(String name, int id, double salary)
	{
		super(name, id); // this calls the two parameter Employee constructor
		this.salary = salary;
	}
	
	//**************************************
	
	@Override
	public void display()
	{
		super.display(); // this method overrides the display method defined in the Employee class
		System.out.printf("salary: $%, .0f\n", salary);
	}
}
