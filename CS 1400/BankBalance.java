/*******************************************
* Nathan Brooks
*
* CS 1400 
*This program doubles a bank account until it
*reaches 1 million dollars
*******************************************/




import java.util.Scanner;

public class BankBalance
{
   public static void main(String[] args)
   {
      Scanner stdIn = new Scanner(System.in);
      
      double balance;
      int count = 0;
            
      System.out.print("Enter your beginning balance: ");
      balance = stdIn.nextDouble();
      
      while (balance < 100000)
      {
         balance *= 2;
         count++;
         
         if (balance > 100000)
         {
            System.out.println("It takes " + count + " years to reach $100,000.");
         }
      } // end while

      while (balance < 1000000)
      {
         balance *= 2;
         count++;
         
         if (balance > 1000000)
         {
            System.out.println("It takes " + count + " years to reach $1,000,000.");
         }
      } // end while
   } // end main
} // end class BankBalance