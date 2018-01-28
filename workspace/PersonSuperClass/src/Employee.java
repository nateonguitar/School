
public class Employee extends Person // the Employee class is derived from the Person superclass
{
	private int id = 0;
	
	//*********************************************
	
	public Employee()
	{  }
	
	/*       // the same as:
	 * 
	 *  public Employee()
	 *  { 
	 *  	super();
	 *  }
	 *  
	 */
	
	public Employee(String name, int id)
	{
		super(name);		// this calls the one parameter Person constructor
		this.id = id;
	}
	
	//*********************************************
	
	public void display()
	{
		System.out.println("name: " + getName()); // because "name" is in a different class and is private, 
												  // we must use an accessor to get it.  Because getName is inherited,
												  // we don't need a referencing prefix for it.
		System.out.println("id: " + id);
	}
}
