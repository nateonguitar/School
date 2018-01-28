/*************************************************
 *  Dean and Dean
 *  
 *  This class hires and pays employees
 *************************************************/
public class Payroll 
{
	public static void main(String[] args)
	{
		Employee[] employees = new Employee[100];
		Hourly hourly;
		employees[0] = new Hourly("Anna", 25.0);
		employees[1] = new Salaried("Simon", 48000);
		employees[2] = new Hourly("Donovan", 20.0);
		
			// this arbitrarily assumes that the payroll's month
			// starts on a tuesday (day = 2), and it contains 30 days
		for (int date=1, day=2; date<=30; date++, day++, day%=7)
		{
			for (int i=0; i<employees.length && employees[i] != null; i++)
			{
				if (day > 0 && day < 6 && employees[i] instanceof Hourly) // this selects appropriate elements
				{
					hourly = (Hourly) employees[i]; // this casts elements into their native class
					hourly.addHours(8);
				}
					// this selects the appropriate time to print each different type
				if ((day == 5 && employees[i] instanceof Hourly) || (date%15 == 0 && employees[i] instanceof Salaried))
				{
					employees[i].printPay(date);
				}
			} // end for i
		} // end for date
	} // end main
} // end class Payroll
