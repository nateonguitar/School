/*******************************************
* Nathan Brooks
*
* CS 1400 
*
* This program uses another file called BankAccount.java
* it pulls user entered user information and runs them
* through methods on the other file.
*******************************************/

import java.util.Scanner;

public class BankAccountDriver
{
   public static void main (String[] args)
   {
      Scanner stdIn = new Scanner(System.in);
      
      String Customer;
      int AccountNum;
            
         // instantiate bankAccount
      BankAccount account = new BankAccount();
      
         // Sets customer for THIS instance
      System.out.print("What is your name? ");
      account.setCustomer(stdIn.nextLine());
      
         // Sets account number for THIS instance
      System.out.print("What is your account number: ");
      account.setAccountNum(stdIn.nextInt());
      
         // this.customer + "'s account number is " + this.accountNum + "."
      account.printAccountInfo();
   } // end main
} // end of class BankAccountDriver