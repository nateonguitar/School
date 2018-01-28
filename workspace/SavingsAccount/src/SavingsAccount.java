/******************************************************
 * Nathan Brooks
 * CS 1400
 * 
 * This class has methods that pertain to a bank account
 ******************************************************/

public class SavingsAccount 
{
	private int ACCOUNT_NUMBER;
	private double balance;
	private double annualInterestRate;
	
	public SavingsAccount(int accountNum, int balance)
	{
		this.ACCOUNT_NUMBER = accountNum;
		this.balance = balance;
	}
	
		//**************************************************
		
		public void setAnnualInterestRate(double annualInterestRate) 
		{
			this.annualInterestRate = annualInterestRate;
		}
		
		//**************************************************
	
		public int getAccountNum() 
		{
			return ACCOUNT_NUMBER;
		}
		
		//**************************************************
		
		public double getBalance() 
		{
			return balance;
		}
		
		//**************************************************
		
		public void addMonthlyInterest()
		{
			balance += (balance * annualInterestRate / 12);
		}
		
		//**************************************************
}
