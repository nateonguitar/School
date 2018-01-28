/****************************************************
* Dean & Dean
*
*This class stores and retrieves data for a car
*****************************************************/

public class Car4
{
   private String make;
   private int year;
   private String color;
   
   //*****************************************************
   
   public Car4(String m, int y, String c)
   {
   this.make = m;
   this.year = y;
   this.color = c;
   } // end constructor
   
   //*****************************************************
   
   public String getMake()
   {
      return this.make;
   }
} // end class Car4