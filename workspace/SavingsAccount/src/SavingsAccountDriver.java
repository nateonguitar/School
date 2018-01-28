/******************************************************
 * Nathan Brooks
 * CS 1400
 * 
 * This class is a driver that prints out a year's interest
 ******************************************************/

public class SavingsAccountDriver {

	public static void main(String[] args) 
	{
		SavingsAccount saver1 = new SavingsAccount(10002, 2000); // accountNum, balance
		SavingsAccount saver2 = new SavingsAccount(10003, 3000);
		
		saver1.setAnnualInterestRate(0.05);
		saver2.setAnnualInterestRate(0.05);
		
		System.out.println("Monthly balances for one year with 0.05 annual interest:");
		System.out.println("\nMonth Account #  Balance | Account #  Balance");
		System.out.println("----- ---------  -------   ---------  -------");
		
		
		System.out.printf("%5s|%9s %8.2f | %9s %8.2f\n",
				0, saver1.getAccountNum(), saver1.getBalance(), saver2.getAccountNum(), saver2.getBalance());
		
		for (int i=1; i<13; i++)
		{
			saver1.addMonthlyInterest();
			saver2.addMonthlyInterest();
			System.out.printf("%5s|%9s %8.2f | %9s %8.2f\n", 
					i, saver1.getAccountNum(), saver1.getBalance(), saver2.getAccountNum(), saver2.getBalance());
		}
		
		System.out.printf("\nFinal balance of both accounts combined: %7.2f", (saver1.getBalance() + saver2.getBalance()));
	}
}
