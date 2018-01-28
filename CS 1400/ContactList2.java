/****************************************************
* Dean & Dean
*
* this program creates a contacts phone number list and 
* prints the created list.  It uses a partially filled array.
*****************************************************/
   
import java.util.Scanner;

public class ContactList2
{
   public static void main(String[] args)
   {
      Scanner stdIn = new Scanner(System.in);
      String[] phoneList = new String[100];
      int filledElements = 0;
      String phoneNum;
      
      System.out.print("Enter phone number(or 'q' to quit): ");
      phoneNum = stdIn.nextLine();
      while (!phoneNum.equalsIgnoreCase("q") && filledElements < phoneList.length)
      {
         if (phoneNum.length() < 1 || phoneNum.length() > 16)
         {
            System.out.println("Invalid entry. Must enter between 1 and 16 characters");
         }
         else
         {
            phoneList[filledElements] = phoneNum;
            filledElements++;       // update the number of filled elements
         }
         System.out.print("Enter phone number (or 'q' to quit): ");
         phoneNum = stdIn.nextLine();
      }  // end while
      
      System.out.println("\nContacctList:");
      for (int i=0; i<filledElements; i++)
      {
         System.out.println((i + 1) + ". " + phoneList[i]);
      }
   } // end main
} // end class ConatctList2