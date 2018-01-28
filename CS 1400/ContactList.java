/****************************************************
* Dean & Dean
*
*This program reates a cell phone contacts phone number list and prints the created lists
*****************************************************/

import java.util.Scanner;

public class ContactList
{
   public static void main(String[] args)
   {
      Scanner stdIn = new Scanner(System.in);
      long[] phoneList;
      int sizeOfList;
      long phoneNum;
      
      System.out.print("How many contact numbers would you like to enter? ");
      sizeOfList = stdIn.nextInt();
      phoneList = new long[sizeOfList]; // create an array with a user specified size
         
         // fill the array
      for (int i=0; i<sizeOfList; i++)
      {
         System.out.print("Enter phone number; ");
         phoneNum = stdIn.nextLong();              // stdIn = phoneNum
         phoneList[i] = phoneNum;                  // phoneNum is put in phoneList[] at position 'i' 
      }                                            // which is incremented
      
         // print the array
      System.out.println("\nContacts List:");
      for (int i=0; i<sizeOfList; i++)
      {
         System.out.println((i+1) + ". " + phoneList[i]);
      }
   } // end main
} // end class ContactList