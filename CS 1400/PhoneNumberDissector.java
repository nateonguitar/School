/*******************************************
* Nathan Brooks
*
* CS 1400 
*
* This program lets you enter a phone number in
* and splits its pieces up into segments.
*
* It also makes sure you don't type a letter first.
*******************************************/

import javax.swing.JOptionPane;
import java.util.Scanner;

public class PhoneNumberDissector
{
   public static void main(String[] args)
   {
      Scanner stdIn = new Scanner(System.in);
            
      String countryCode;
      String areaCode;
      String localPhoneNumber;
      String enteredPhoneNumber;
      char ch;
      int firstDash;
      int lastDash;
      
      System.out.println("Enter a phone number in the form cc-area-local\n "
            + "(cc = country code digits, area = area code digits, and local = local phone digits)");
      enteredPhoneNumber = stdIn.nextLine();
      
         // To check if the first letter is q for quitting
      ch = enteredPhoneNumber.charAt(0);
      
      while (ch != 'q')
      {
            // If someone enters a word or letter
         if (!(Character.isDigit(ch)))
         {
            JOptionPane.showMessageDialog(null, "\"" + enteredPhoneNumber + "\" is not a valid phone number");
            
            System.out.println("Enter a phone number in the form cc-area-local\n "
            + "(cc = country code digits, area = area code digits, and local = local phone digits)");
            enteredPhoneNumber = stdIn.nextLine();
            
            ch = enteredPhoneNumber.charAt(0);
         }
         else
         {
               // Gets the index number of each dash
            firstDash = enteredPhoneNumber.indexOf('-');
            lastDash = enteredPhoneNumber.lastIndexOf('-');
            
               // Assigns string segments to new variables
            countryCode = (enteredPhoneNumber.substring(0, firstDash));
            areaCode = (enteredPhoneNumber.substring(firstDash + 1, lastDash));
            localPhoneNumber = (enteredPhoneNumber.substring(lastDash + 1, enteredPhoneNumber.length()));
         
            JOptionPane.showMessageDialog(null, "Country code: " + countryCode + "\nArea code: "
             + areaCode + "\nLocal phone number: " + localPhoneNumber);
            
            System.out.println("Enter a phone number in the form cc-area-local, "
            + "where cc = country code digits, area = area code digits, and local = local phone digits.");
            enteredPhoneNumber = stdIn.nextLine();
            
               // To check if the first letter is q for quitting
            ch = enteredPhoneNumber.charAt(0);
         } // end of else   
      } // end of while
      
      if (ch == 'q')
      {
         JOptionPane.showMessageDialog(null, "Thank you for using this program");
      }
   } // end main
} // end class PhoneNumberDissector