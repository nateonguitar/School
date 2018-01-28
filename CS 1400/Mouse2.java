/*******************************************
* Nathan Brooks
*
* CS 1400 
*
* 
*******************************************/

import java.util.Scanner;

public class Mouse2
{
   private int age = 0;
   private double weight = 1.0;
   private double percentGrowthRate;
   
   //******************************************
   
   public void setPercentGrowthRate(double percentGrowthRate)
   {
      this.percentGrowthRate = percentGrowthRate;
   }
   
   //*******************************************
   
   public int getAge()
   {
      return this.age;
   }
   
   //********************************************
   
   public double getWeight()
   {
      return this.weight;
   }
   
   //********************************************
   
   public void grow(int days)
   {
      for (int i=0; i<days; i++)
      {
         this.weight += (0.01 * this.percentGrowthRate * this.weight);
      }
      this.age += days;
   } // end grow
} // end Mouse2