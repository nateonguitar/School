import java.util.ArrayList;

public class Dealership 
{
	private String company;
	private Manager manager;
	private ArrayList<SalesPerson> people = new ArrayList<>();
	private ArrayList<Car> cars = new ArrayList<>();
	
	//*************************************************************************************
	
	public Dealership(String company, Manager manager)
	{
		this.company = company;
		this.manager = manager;
	}
	
	//*************************************************************************************
	
	public void addCar(Car car)
	{
		cars.add(car);
	}
	
	public void addPerson(SalesPerson person)
	{
		people.add(person);
	}
	
	//*************************************************************************************
	
	public void printStatus()
	{
		System.out.println(company + "\t manager: " + manager.getName());
		
		System.out.println("\nSalespeople");
		System.out.println("---------");
		for (SalesPerson person : people)
				System.out.println(person.getName());
		
		System.out.println("\nMakes");
		System.out.println("---------");
		
		for (Car car : cars)
				System.out.println(car.getMake());
	}
}
