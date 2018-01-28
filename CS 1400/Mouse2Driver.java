/*******************************************
* Nathan Brooks
*
* CS 1400 
*
* 
*******************************************/

import java.util.Scanner;

public class Mouse2Driver
{
   public static void main(String[] args)
   {
      Scanner stdIn = new Scanner(System.in);
      Mouse2 mickey =  new Mouse2();
      int days;
      
      mickey.setPercentGrowthRate(10);
      System.out.print("Enter number of days to grow: ");
      days = stdIn.nextInt();
      mickey.grow(days);
      System.out.printf("age = %d, weight = %.3f\n", mickey.getAge(), mickey.getWeight());
   } // end main
} // end Mouse2Driver