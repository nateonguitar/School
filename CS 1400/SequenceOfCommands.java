/*******************************
* SequenceOfCommands.java
* Nathan Brooks
*
* This program does some simple math.
*******************************/

public class SequenceOfCommands
   {
   public static void main(String[] args)
      {
      int a = 5, b = 2;
      double c = 3.0;
      
      a += b;
      b++;
      c--;
      c *= a;
      System.out.println("a + b + c = " + (a + b + c));
      } // End main
   } // End class SequenceOfCommands