/***************************************
* BankAccount.java
* Dean & Dean
*
* This program stores and prints information
* for a bank account.
***************************************/

public class BankAccount
{
   private String customer;  // customer's name
   private int accountNum;   // bank account number

   //**************************************************

   public void setCustomer(String customer)
   {
      this.customer = customer;
   } // end setCustomer

   public void setAccountNum(int accountNum)
   {
      this.accountNum = accountNum;
   } // end setAccountNum

   //**************************************************

   // This method prints a bank account's information.

   public void printAccountInfo()
   {
      System.out.println(
         this.customer + "'s account number is " + this.accountNum + ".");
   } // end printAccountInfo
} // end class BankAccount