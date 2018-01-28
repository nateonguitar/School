/****************************************************
* Dean & Dean
*
*This class illustrates uses and omissions of "this."
*****************************************************/

public class MouseShortcut
{
   private int age;        // in days
   private double weight;  // in grams
   
   //***************************************************
   
   public MouseShortcut(int age, double weight)
   {
      setAge(age);
      setWeight(weight);
   } // end constructor
   
   //***************************************************
   
   public void setAge(int a)
   {
         // OK to omit "this." before instance variable "age"
         // because it's different from parameter "a"
      age = a;
   }
   
   //***************************************************
   
   public void setWeight(double weight)
   {
         // not OK to omit "this." before instance variable weight
         // because it's the same as the paramter, weight
      this.weight = weight;
   }
   
   //***************************************************
   
   public void print()
   {
      System.out.println("age = " + age +
         ", weight = " + weight);
   }
} // end class MouseShortcut11