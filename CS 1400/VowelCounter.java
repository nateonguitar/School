/****************************************************
* Nathan Brooks
*
* CS1400
* VowelCounter class that keeps track of vowel counts
*****************************************************/

import java.util.Scanner;

public class VowelCounter
{
   private int[][] vowels = new int[5][2]; // 5 rows 2 collumns
   
   //*****************************************************************
   
      // This method receives a line of text as a parameter and uses it to update the vowel counts appropriately.
   public void processLine(String line)
   {
         // loop through each character of the line
      for (int i=0; i<line.length(); i++)
      {
         switch (line.charAt(i))
         {
            case 'a':
               vowels[0][0]++;
               break;
               
            case 'A':
               vowels[0][1]++;
               break;
               
            case 'e':
               vowels[1][0]++;
               break;
               
            case 'E':
               vowels[1][1]++;
               break;
               
            case 'i':
               vowels[2][0]++;
               break;
               
            case 'I':
               vowels[2][1]++;
               break;
               
            case 'o':
               vowels[3][0]++;
               break;
               
            case 'O':
               vowels[3][1]++;
               break;
               
            case 'u':
               vowels[4][0]++;
               break;
               
            case 'U':
               vowels[4][1]++;
               break;
               
         } // end switch
      } // end for
   }
   
   //************************************************************************
      
      // This method prints a summary of the counts for all vowels entered.
   public void printSummary()
   {
      final char[] VOWELS = { 'A', 'E', 'I', 'O', 'U' };
   
      for (int i=0; i<VOWELS.length; i++)
      {
         System.out.println(VOWELS[i] + ": " +
            vowels[i][0] + " lowercase, " +
            vowels[i][1] + " uppercase, " +
            (vowels[i][0] + vowels[i][1]) + " total");
      } // end for
   } // end printSummary()
} // end class VowelCounter